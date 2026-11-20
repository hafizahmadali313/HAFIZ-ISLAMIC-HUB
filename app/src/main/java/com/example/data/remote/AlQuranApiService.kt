package com.example.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

@JsonClass(generateAdapter = true)
data class AlQuranMultiEditionResponse(
    @Json(name = "code") val code: Int,
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: List<AlQuranSurahEditionDto>
)

@JsonClass(generateAdapter = true)
data class AlQuranSurahEditionDto(
    @Json(name = "number") val number: Int,
    @Json(name = "name") val name: String,
    @Json(name = "englishName") val englishName: String,
    @Json(name = "ayahs") val ayahs: List<AlQuranAyahDto>
)

@JsonClass(generateAdapter = true)
data class AlQuranAyahDto(
    @Json(name = "number") val number: Int,
    @Json(name = "text") val text: String,
    @Json(name = "numberInSurah") val numberInSurah: Int,
    @Json(name = "juz") val juz: Int,
    @Json(name = "page") val page: Int
)

interface AlQuranApiService {
    @GET("surah/{surahNumber}/editions/quran-uthmani,ur.jalandhry")
    suspend fun getSurahWithUrduTranslation(
        @Path("surahNumber") surahNumber: Int
    ): AlQuranMultiEditionResponse

    companion object {
        private const val BASE_URL = "https://api.alquran.cloud/v1/"

        fun create(): AlQuranApiService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
                .create(AlQuranApiService::class.java)
        }
    }
}
