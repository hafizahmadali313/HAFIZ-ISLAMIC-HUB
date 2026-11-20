package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.HafizIslamicHubApp
import com.example.data.catalog.QuranCatalogData
import com.example.data.local.BookmarkEntity
import com.example.data.local.SurahAyahEntity
import com.example.data.model.AyahItem
import com.example.data.model.JuzInfo
import com.example.data.model.SurahInfo
import com.example.data.repository.IslamicHubRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface SurahLoadState {
    data object Loading : SurahLoadState
    data class Success(val surah: SurahInfo, val ayahs: List<AyahItem>) : SurahLoadState
    data class Error(val surah: SurahInfo, val message: String) : SurahLoadState
}

class QuranViewModel(private val repository: IslamicHubRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _cachedAyahSearchResults = MutableStateFlow<List<SurahAyahEntity>>(emptyList())
    val cachedAyahSearchResults: StateFlow<List<SurahAyahEntity>> = _cachedAyahSearchResults

    val filteredSurahs: StateFlow<List<SurahInfo>> = _searchQuery
        .combine(MutableStateFlow(QuranCatalogData.allSurahs)) { query, all ->
            if (query.isBlank()) all
            else {
                val q = query.trim().lowercase()
                all.filter {
                    it.englishName.lowercase().contains(q) ||
                        it.englishMeaning.lowercase().contains(q) ||
                        it.arabicName.contains(query.trim()) ||
                        it.urduName.contains(query.trim()) ||
                        it.number.toString() == q
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), QuranCatalogData.allSurahs)

    val allJuz: List<JuzInfo> = QuranCatalogData.allJuz

    val bookmarks: StateFlow<List<BookmarkEntity>> = repository.bookmarksFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _activeSurahNumber = MutableStateFlow(1)
    private val _loadError = MutableStateFlow<String?>(null)
    private val _isLoading = MutableStateFlow(false)

    private var surahObserverJob: Job? = null
    private val _currentAyahs = MutableStateFlow<List<AyahItem>>(emptyList())

    val surahLoadState: StateFlow<SurahLoadState> = combine(
        _activeSurahNumber,
        _isLoading,
        _loadError,
        _currentAyahs
    ) { surahNum, loading, error, ayahs ->
        val surahInfo = QuranCatalogData.allSurahs.find { it.number == surahNum }
            ?: QuranCatalogData.allSurahs.first()
        when {
            ayahs.isNotEmpty() -> SurahLoadState.Success(surahInfo, ayahs)
            loading -> SurahLoadState.Loading
            error != null -> SurahLoadState.Error(surahInfo, error)
            else -> SurahLoadState.Loading
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SurahLoadState.Loading)

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            _cachedAyahSearchResults.value = repository.searchCachedAyahs(query)
        }
    }

    fun openSurah(surahNumber: Int) {
        val target = surahNumber.coerceIn(1, 114)
        _activeSurahNumber.value = target
        _loadError.value = null
        _currentAyahs.value = emptyList()

        surahObserverJob?.cancel()
        surahObserverJob = viewModelScope.launch {
            repository.observeSurahAyahs(target).collect { list ->
                _currentAyahs.value = list
            }
        }

        fetchSurahIfNeeded(target)
    }

    fun retrySurahLoad() {
        fetchSurahIfNeeded(_activeSurahNumber.value)
    }

    private fun fetchSurahIfNeeded(surahNumber: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _loadError.value = null
            val result = repository.ensureSurahLoaded(surahNumber)
            _isLoading.value = false
            if (result.isFailure) {
                _loadError.value = result.exceptionOrNull()?.message
                    ?: "Unable to load Surah. Please check your internet connection."
            }
        }
    }

    fun toggleBookmark(ayah: AyahItem) {
        val surahName = QuranCatalogData.allSurahs.find { it.number == ayah.surahNumber }?.englishName ?: "Surah"
        viewModelScope.launch {
            repository.toggleBookmark(ayah, surahName)
        }
    }

    fun removeBookmark(id: String) {
        viewModelScope.launch { repository.removeBookmark(id) }
    }

    fun saveLastRead(surahNumber: Int, ayahNumber: Int) {
        val surahName = QuranCatalogData.allSurahs.find { it.number == surahNumber }?.englishName ?: "Al-Fatihah"
        viewModelScope.launch {
            repository.saveLastRead(surahNumber, surahName, ayahNumber)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HafizIslamicHubApp)
                QuranViewModel(app.repository)
            }
        }
    }
}
