package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.catalog.QuranCatalogData
import com.example.data.model.AppPreferences
import com.example.data.model.AyahItem
import com.example.data.model.JuzInfo
import com.example.data.model.SurahInfo
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.IslamicGeometricBackdrop
import com.example.ui.theme.IslamicOrnamentDivider
import com.example.ui.theme.PlayfairFontFamily
import com.example.ui.theme.RubElHizbBadge
import com.example.ui.viewmodel.QuranViewModel
import com.example.ui.viewmodel.SurahLoadState

@Composable
fun QuranHomeScreen(
    quranViewModel: QuranViewModel,
    preferences: AppPreferences,
    onOpenSurah: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val searchQuery by quranViewModel.searchQuery.collectAsStateWithLifecycle()
    val surahs by quranViewModel.filteredSurahs.collectAsStateWithLifecycle()
    val bookmarks by quranViewModel.bookmarks.collectAsStateWithLifecycle()
    val cachedAyahHits by quranViewModel.cachedAyahSearchResults.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Surahs (114)", "Para / Juz (30)", "Bookmarks (${bookmarks.size})")

    Box(modifier = modifier.fillMaxSize()) {
        IslamicGeometricBackdrop()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
                .align(Alignment.TopCenter)
        ) {
            // Header
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = "Holy Quran (Quran Pak)",
                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = PlayfairFontFamily),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Arabic Uthmani Script & Authentic Urdu Translation",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { quranViewModel.updateSearchQuery(it) },
                    placeholder = { Text("Search Surah name, number, or cached verses...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Quran") },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { quranViewModel.updateSearchQuery("") }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear search")
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("quran_search_input")
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Last Read Pill
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable {
                            onOpenSurah(preferences.lastRead.surahNumber, preferences.lastRead.ayahNumber)
                        }
                        .testTag("quran_last_read_banner"),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.65f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Last Read: ${preferences.lastRead.surahName} • Ayah ${preferences.lastRead.ayahNumber}",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                        Text(
                            text = "Open",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.labelLarge
                            )
                        },
                        modifier = Modifier.testTag("quran_tab_$index")
                    )
                }
            }

            when (selectedTab) {
                0 -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (searchQuery.isNotBlank() && cachedAyahHits.isNotEmpty()) {
                            item {
                                Text(
                                    text = "Matching Ayahs (${cachedAyahHits.size})",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                            items(cachedAyahHits, key = { "hit_${it.id}" }) { hit ->
                                val sName = QuranCatalogData.allSurahs.find { it.number == hit.surahNumber }?.englishName ?: ""
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onOpenSurah(hit.surahNumber, hit.ayahNumberInSurah) },
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            text = "Surah $sName (${hit.surahNumber}:${hit.ayahNumberInSurah})",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = hit.arabicText,
                                            style = MaterialTheme.typography.bodyLarge.copy(fontFamily = AmiriFontFamily),
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = hit.urduTranslation,
                                            style = MaterialTheme.typography.bodySmall,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            item { HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp)) }
                        }

                        items(surahs, key = { it.number }) { surah ->
                            SurahListItemCard(
                                surah = surah,
                                onClick = { onOpenSurah(surah.number, 1) }
                            )
                        }
                    }
                }
                1 -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(quranViewModel.allJuz, key = { it.juzNumber }) { juz ->
                            JuzListItemCard(
                                juz = juz,
                                onClick = { onOpenSurah(juz.startSurah, juz.startAyah) }
                            )
                        }
                    }
                }
                2 -> {
                    if (bookmarks.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.BookmarkBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                                )
                                Text(
                                    text = "No Bookmarks Yet",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Tap the bookmark icon on any Ayah while reading to save it here for quick access.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(bookmarks, key = { it.id }) { bm ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { onOpenSurah(bm.surahNumber, bm.ayahNumberInSurah) },
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = "Surah ${bm.surahName} • Ayah ${bm.ayahNumberInSurah}",
                                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = bm.arabicSnippet,
                                                style = MaterialTheme.typography.bodyLarge.copy(fontFamily = AmiriFontFamily),
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = bm.urduSnippet,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        IconButton(
                                            onClick = { quranViewModel.removeBookmark(bm.id) },
                                            modifier = Modifier.minimumInteractiveComponentSize()
                                        ) {
                                            Icon(
                                                Icons.Default.DeleteOutline,
                                                contentDescription = "Remove bookmark",
                                                tint = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SurahListItemCard(surah: SurahInfo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("surah_item_${surah.number}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RubElHizbBadge(label = surah.number.toString())
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = surah.englishName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${surah.englishMeaning} • ${surah.revelationType} • ${surah.ayahCount} Ayahs",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = surah.arabicName,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = AmiriFontFamily,
                        fontSize = 22.sp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Juz ${surah.startingJuz}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
private fun JuzListItemCard(juz: JuzInfo, onClick: () -> Unit) {
    val startSurah = QuranCatalogData.allSurahs.find { it.number == juz.startSurah }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .testTag("juz_item_${juz.juzNumber}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RubElHizbBadge(label = juz.juzNumber.toString())
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Para / Juz ${juz.juzNumber} • ${juz.englishName}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Starts at Surah ${startSurah?.englishName ?: juz.startSurah}, Ayah ${juz.startAyah}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = juz.arabicName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = AmiriFontFamily,
                    fontSize = 20.sp
                ),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahReaderScreen(
    surahNumber: Int,
    initialAyah: Int,
    quranViewModel: QuranViewModel,
    preferences: AppPreferences,
    onUpdateFontSizes: (Float, Float) -> Unit,
    onBack: () -> Unit
) {
    val loadState by quranViewModel.surahLoadState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    var showTextSizeSheet by remember { mutableStateOf(false) }

    LaunchedEffect(surahNumber) {
        quranViewModel.openSurah(surahNumber)
        quranViewModel.saveLastRead(surahNumber, initialAyah.coerceAtLeast(1))
    }

    LaunchedEffect(loadState, initialAyah) {
        if (loadState is SurahLoadState.Success && initialAyah > 1) {
            val ayahs = (loadState as SurahLoadState.Success).ayahs
            val idx = ayahs.indexOfFirst { it.ayahNumberInSurah == initialAyah }
            if (idx >= 0) {
                listState.scrollToItem(idx + 1) // +1 for header card
            }
        }
    }

    val surahInfo = remember(surahNumber) {
        QuranCatalogData.allSurahs.find { it.number == surahNumber } ?: QuranCatalogData.allSurahs.first()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "${surahInfo.number}. ${surahInfo.englishName}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${surahInfo.urduName} • ${surahInfo.ayahCount} Ayahs",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("surah_back_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showTextSizeSheet = true },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("surah_text_size_button")
                    ) {
                        Icon(Icons.Default.FormatSize, contentDescription = "Adjust Arabic and Urdu text sizes")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            IslamicGeometricBackdrop()

            when (val state = loadState) {
                is SurahLoadState.Loading -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Text(
                            text = "Loading Surah ${surahInfo.englishName} with Urdu translation...",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                is SurahLoadState.Error -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.CloudOff,
                            contentDescription = null,
                            modifier = Modifier.size(52.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = "Surah Not Cached Offline",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { quranViewModel.retrySurahLoad() },
                            modifier = Modifier.testTag("surah_retry_button")
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Retry Download")
                        }
                    }
                }

                is SurahLoadState.Success -> {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .widthIn(max = 760.dp)
                            .align(Alignment.TopCenter),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            SurahBannerHeader(surah = state.surah)
                        }

                        items(state.ayahs, key = { it.ayahNumberInSurah }) { ayah ->
                            val isLastRead = preferences.lastRead.surahNumber == ayah.surahNumber &&
                                preferences.lastRead.ayahNumber == ayah.ayahNumberInSurah
                            AyahCard(
                                ayah = ayah,
                                arabicSizeSp = preferences.arabicFontSizeSp,
                                urduSizeSp = preferences.urduFontSizeSp,
                                isLastRead = isLastRead,
                                onToggleBookmark = { quranViewModel.toggleBookmark(ayah) },
                                onMarkLastRead = {
                                    quranViewModel.saveLastRead(ayah.surahNumber, ayah.ayahNumberInSurah)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showTextSizeSheet) {
        ModalBottomSheet(onDismissRequest = { showTextSizeSheet = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Reading Text Size",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Arabic Text Size: ${preferences.arabicFontSizeSp.toInt()} sp",
                    style = MaterialTheme.typography.labelLarge
                )
                Slider(
                    value = preferences.arabicFontSizeSp,
                    onValueChange = { onUpdateFontSizes(it, preferences.urduFontSizeSp) },
                    valueRange = 18f..42f
                )
                Text(
                    text = "Urdu Translation Size: ${preferences.urduFontSizeSp.toInt()} sp",
                    style = MaterialTheme.typography.labelLarge
                )
                Slider(
                    value = preferences.urduFontSizeSp,
                    onValueChange = { onUpdateFontSizes(preferences.arabicFontSizeSp, it) },
                    valueRange = 13f..26f
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SurahBannerHeader(surah: SurahInfo) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = surah.arabicName,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = AmiriFontFamily,
                    fontSize = 32.sp
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "${surah.englishName} (${surah.englishMeaning})",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            Text(
                text = "${surah.revelationType} • ${surah.ayahCount} Verses • Juz ${surah.startingJuz}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
            )
            if (surah.number != 9 && surah.number != 1) {
                Spacer(modifier = Modifier.height(6.dp))
                IslamicOrnamentDivider(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(14.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = AmiriFontFamily,
                        fontSize = 24.sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun AyahCard(
    ayah: AyahItem,
    arabicSizeSp: Float,
    urduSizeSp: Float,
    isLastRead: Boolean,
    onToggleBookmark: () -> Unit,
    onMarkLastRead: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isLastRead) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = if (isLastRead) 1.5.dp else 1.dp,
            color = if (isLastRead) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${ayah.ayahNumberInSurah}",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Text(
                        text = "Juz ${ayah.juz} • Page ${ayah.page}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextButton(
                        onClick = onMarkLastRead,
                        modifier = Modifier.minimumInteractiveComponentSize()
                    ) {
                        if (isLastRead) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Last read position",
                                modifier = Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Last Read", style = MaterialTheme.typography.labelSmall)
                        } else {
                            Text("Mark Read", style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    IconButton(
                        onClick = onToggleBookmark,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("bookmark_ayah_${ayah.ayahNumberInSurah}")
                    ) {
                        Icon(
                            imageVector = if (ayah.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = if (ayah.isBookmarked) "Remove bookmark" else "Bookmark Ayah",
                            tint = if (ayah.isBookmarked) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Arabic Quranic text
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Text(
                    text = ayah.arabicText,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = AmiriFontFamily,
                        fontSize = arabicSizeSp.sp,
                        lineHeight = (arabicSizeSp * 1.65f).sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f))

            // Urdu Translation
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Text(
                    text = ayah.urduTranslation,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = AmiriFontFamily,
                        fontSize = urduSizeSp.sp,
                        lineHeight = (urduSizeSp * 1.65f).sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
