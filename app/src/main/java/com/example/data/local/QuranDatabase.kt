package com.example.data.local

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "surah_ayahs")
data class SurahAyahEntity(
    @PrimaryKey val id: String, // e.g., "1:1"
    val surahNumber: Int,
    val ayahNumberInSurah: Int,
    val globalAyahNumber: Int,
    val arabicText: String,
    val urduTranslation: String,
    val juz: Int,
    val page: Int
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String, // e.g., "1:1"
    val surahNumber: Int,
    val surahName: String,
    val ayahNumberInSurah: Int,
    val arabicSnippet: String,
    val urduSnippet: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "quiz_history")
data class QuizHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val categoryName: String,
    val score: Int,
    val totalQuestions: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface QuranDao {
    @Query("SELECT * FROM surah_ayahs WHERE surahNumber = :surahNumber ORDER BY ayahNumberInSurah ASC")
    fun observeAyahsBySurah(surahNumber: Int): Flow<List<SurahAyahEntity>>

    @Query("SELECT * FROM surah_ayahs WHERE surahNumber = :surahNumber ORDER BY ayahNumberInSurah ASC")
    suspend fun getAyahsBySurah(surahNumber: Int): List<SurahAyahEntity>

    @Query("SELECT COUNT(*) FROM surah_ayahs WHERE surahNumber = :surahNumber")
    suspend fun countAyahsForSurah(surahNumber: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAyahs(ayahs: List<SurahAyahEntity>)

    @Query(
        "SELECT * FROM surah_ayahs WHERE arabicText LIKE '%' || :query || '%' OR urduTranslation LIKE '%' || :query || '%' ORDER BY surahNumber ASC, ayahNumberInSurah ASC LIMIT 60"
    )
    suspend fun searchCachedAyahs(query: String): List<SurahAyahEntity>

    @Query("SELECT * FROM bookmarks ORDER BY createdAt DESC")
    fun observeBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT id FROM bookmarks")
    fun observeBookmarkIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE id = :id")
    suspend fun deleteBookmarkById(id: String)

    @Query("SELECT * FROM quiz_history ORDER BY timestamp DESC LIMIT 20")
    fun observeQuizHistory(): Flow<List<QuizHistoryEntity>>

    @Insert
    suspend fun insertQuizResult(result: QuizHistoryEntity)
}

@Database(
    entities = [SurahAyahEntity::class, BookmarkEntity::class, QuizHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao

    companion object {
        @Volatile
        private var INSTANCE: QuranDatabase? = null

        fun getInstance(context: Context): QuranDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuranDatabase::class.java,
                    "hafiz_islamic_hub_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
