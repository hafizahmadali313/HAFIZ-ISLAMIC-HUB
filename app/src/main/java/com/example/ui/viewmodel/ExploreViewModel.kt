package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.HafizIslamicHubApp
import com.example.data.catalog.AsmaUlHusnaCatalog
import com.example.data.catalog.DuasCatalog
import com.example.data.catalog.QuizCatalog
import com.example.data.local.QuizHistoryEntity
import com.example.data.model.AllahName
import com.example.data.model.DuaCategory
import com.example.data.model.DuaItem
import com.example.data.model.QuizCategory
import com.example.data.model.QuizQuestion
import com.example.data.repository.IslamicHubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class QuizSessionState(
    val category: QuizCategory = QuizCategory.ALL,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val score: Int = 0,
    val isFinished: Boolean = false
)

class ExploreViewModel(private val repository: IslamicHubRepository) : ViewModel() {

    // Duas
    private val _selectedDuaCategory = MutableStateFlow<DuaCategory?>(null)
    val selectedDuaCategory: StateFlow<DuaCategory?> = _selectedDuaCategory

    private val _duaSearch = MutableStateFlow("")
    val duaSearch: StateFlow<String> = _duaSearch

    val filteredDuas: StateFlow<List<DuaItem>> = combine(_selectedDuaCategory, _duaSearch) { cat, query ->
        DuasCatalog.allDuas.filter { dua ->
            val matchesCat = cat == null || dua.category == cat
            val q = query.trim().lowercase()
            val matchesQuery = q.isEmpty() ||
                dua.title.lowercase().contains(q) ||
                dua.english.lowercase().contains(q) ||
                dua.urdu.contains(query.trim()) ||
                dua.reference.lowercase().contains(q)
            matchesCat && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), DuasCatalog.allDuas)

    fun selectDuaCategory(category: DuaCategory?) {
        _selectedDuaCategory.value = category
    }

    fun updateDuaSearch(query: String) {
        _duaSearch.value = query
    }

    // 99 Names of Allah
    private val _namesSearch = MutableStateFlow("")
    val namesSearch: StateFlow<String> = _namesSearch

    val filteredNames: StateFlow<List<AllahName>> = _namesSearch
        .combine(MutableStateFlow(AsmaUlHusnaCatalog.allNames)) { query, all ->
            val q = query.trim().lowercase()
            if (q.isEmpty()) all
            else all.filter {
                it.transliteration.lowercase().contains(q) ||
                    it.englishMeaning.lowercase().contains(q) ||
                    it.urduMeaning.contains(query.trim()) ||
                    it.arabic.contains(query.trim()) ||
                    it.number.toString() == q
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AsmaUlHusnaCatalog.allNames)

    fun updateNamesSearch(query: String) {
        _namesSearch.value = query
    }

    // Islamic Quiz
    private val _quizState = MutableStateFlow(QuizSessionState())
    val quizState: StateFlow<QuizSessionState> = _quizState

    val quizHistory: StateFlow<List<QuizHistoryEntity>> = repository.quizHistoryFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun startQuiz(category: QuizCategory) {
        val pool = if (category == QuizCategory.ALL) {
            QuizCatalog.allQuestions.shuffled().take(10)
        } else {
            QuizCatalog.allQuestions.filter { it.category == category }.shuffled()
        }
        _quizState.value = QuizSessionState(
            category = category,
            questions = pool,
            currentIndex = 0,
            selectedOptionIndex = null,
            score = 0,
            isFinished = false
        )
    }

    fun selectQuizOption(optionIndex: Int) {
        val state = _quizState.value
        if (state.selectedOptionIndex != null || state.isFinished) return
        val currentQ = state.questions.getOrNull(state.currentIndex) ?: return
        val newScore = if (optionIndex == currentQ.correctIndex) state.score + 1 else state.score
        _quizState.value = state.copy(
            selectedOptionIndex = optionIndex,
            score = newScore
        )
    }

    fun nextQuizQuestion() {
        val state = _quizState.value
        if (state.currentIndex + 1 >= state.questions.size) {
            _quizState.value = state.copy(isFinished = true)
            viewModelScope.launch {
                repository.saveQuizResult(
                    categoryName = state.category.title,
                    score = state.score,
                    totalQuestions = state.questions.size
                )
            }
        } else {
            _quizState.value = state.copy(
                currentIndex = state.currentIndex + 1,
                selectedOptionIndex = null
            )
        }
    }

    fun resetQuizLobby() {
        _quizState.value = QuizSessionState()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HafizIslamicHubApp)
                ExploreViewModel(app.repository)
            }
        }
    }
}
