package com.example

import com.example.data.catalog.AsmaUlHusnaCatalog
import com.example.data.catalog.DuasCatalog
import com.example.data.catalog.QuranCatalogData
import com.example.data.catalog.QuizCatalog
import com.example.data.model.AsrMadhhab
import com.example.data.model.CityLocation
import com.example.data.model.DuaCategory
import com.example.data.model.PrayerCalcMethod
import com.example.domain.PrayerTimeCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun quranCatalog_hasAll114SurahsAnd30Juz() {
        assertEquals(114, QuranCatalogData.allSurahs.size)
        assertEquals(30, QuranCatalogData.allJuz.size)
        assertEquals(7, QuranCatalogData.getBundledSurahAyahs(1).size)
        assertEquals(4, QuranCatalogData.getBundledSurahAyahs(112).size)
    }

    @Test
    fun asmaUlHusna_hasAll99Names() {
        assertEquals(99, AsmaUlHusnaCatalog.allNames.size)
        assertEquals(1, AsmaUlHusnaCatalog.allNames.first().number)
        assertEquals(99, AsmaUlHusnaCatalog.allNames.last().number)
    }

    @Test
    fun duasCatalog_coversAllCategories() {
        val categories = DuasCatalog.allDuas.map { it.category }.toSet()
        assertEquals(DuaCategory.entries.toSet(), categories)
    }

    @Test
    fun quizCatalog_hasValidOptions() {
        assertTrue(QuizCatalog.allQuestions.size >= 30)
        QuizCatalog.allQuestions.forEach { q ->
            assertTrue(q.correctIndex in q.options.indices)
        }
    }

    @Test
    fun qiblaBearing_fromLahoreIsWestward() {
        val bearing = PrayerTimeCalculator.calculateQiblaBearing(31.5204, 74.3587)
        assertTrue("Expected Lahore Qibla bearing around 260 deg, got $bearing", bearing in 250.0..270.0)
    }

    @Test
    fun prayerSchedule_calculatesAllSixTimingsInOrder() {
        val lahore = CityLocation("Lahore", "Pakistan", 31.5204, 74.3587, "Asia/Karachi")
        val schedule = PrayerTimeCalculator.calculatePrayerSchedule(
            city = lahore,
            method = PrayerCalcMethod.KARACHI,
            madhhab = AsrMadhhab.HANAFI
        )
        assertEquals(6, schedule.prayers.size)
        val minutes = schedule.prayers.map { it.hour24 * 60 + it.minute }
        for (i in 0 until minutes.lastIndex) {
            assertTrue("Prayer times should be strictly increasing: $minutes", minutes[i] < minutes[i + 1])
        }
    }
}
