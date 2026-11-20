package com.example.data.catalog

import com.example.data.model.CityLocation
import com.example.data.model.QuizCategory
import com.example.data.model.QuizQuestion
import com.example.data.model.TasbeehDhikr

object QuizCatalog {
    val allQuestions: List<QuizQuestion> = listOf(
        // QURAN PAK
        QuizQuestion(
            id = 1,
            category = QuizCategory.QURAN,
            question = "How many Surahs (chapters) are in the Holy Quran?",
            options = listOf("110", "112", "114", "120"),
            correctIndex = 2,
            explanation = "The Holy Quran consists of 114 Surahs divided into 30 Juz (Paras).",
            reference = "Quran Structure"
        ),
        QuizQuestion(
            id = 2,
            category = QuizCategory.QURAN,
            question = "Which Surah is known as the 'Heart of the Quran'?",
            options = listOf("Surah Al-Fatihah", "Surah Ya-Sin", "Surah Ar-Rahman", "Surah Al-Mulk"),
            correctIndex = 1,
            explanation = "Surah Ya-Sin (Surah 36) is famously referred to as the Heart of the Quran.",
            reference = "Jami' at-Tirmidhi 2887"
        ),
        QuizQuestion(
            id = 3,
            category = QuizCategory.QURAN,
            question = "Which is the longest Surah in the Holy Quran?",
            options = listOf("Surah Aal-i-Imran", "Surah An-Nisa", "Surah Al-Baqarah", "Surah Al-A'raf"),
            correctIndex = 2,
            explanation = "Surah Al-Baqarah (Chapter 2) is the longest Surah with 286 verses.",
            reference = "Surah Al-Baqarah 2:1-286"
        ),
        QuizQuestion(
            id = 4,
            category = QuizCategory.QURAN,
            question = "Which Surah does NOT begin with 'Bismillah ir-Rahman ir-Rahim'?",
            options = listOf("Surah Al-Anfal", "Surah At-Tawbah", "Surah Al-Kahf", "Surah An-Naml"),
            correctIndex = 1,
            explanation = "Surah At-Tawbah (Chapter 9) does not open with Bismillah, while Surah An-Naml mentions it twice.",
            reference = "Surah At-Tawbah"
        ),
        QuizQuestion(
            id = 5,
            category = QuizCategory.QURAN,
            question = "Which verse of the Quran is known as the greatest verse (Ayat-ul-Kursi)?",
            options = listOf("Surah Al-Baqarah 2:255", "Surah Al-Baqarah 2:286", "Surah Al-Ikhlas 112:1", "Surah An-Nur 24:35"),
            correctIndex = 0,
            explanation = "Ayat-ul-Kursi is verse 255 of Surah Al-Baqarah and describes Allah's absolute sovereignty.",
            reference = "Sahih Muslim 810"
        ),
        QuizQuestion(
            id = 6,
            category = QuizCategory.QURAN,
            question = "In which month was the Holy Quran first revealed?",
            options = listOf("Muharram", "Rajab", "Ramadan", "Dhul-Hijjah"),
            correctIndex = 2,
            explanation = "The Quran was sent down during the blessed month of Ramadan on Laylat-ul-Qadr.",
            reference = "Surah Al-Baqarah 2:185"
        ),
        QuizQuestion(
            id = 7,
            category = QuizCategory.QURAN,
            question = "Which Surah is equal to one-third of the Quran in reward when recited?",
            options = listOf("Surah Al-Kawthar", "Surah Al-Ikhlas", "Surah Al-Falaq", "Surah Al-Asr"),
            correctIndex = 1,
            explanation = "The Prophet ﷺ stated that Surah Al-Ikhlas is equivalent in reward to one-third of the Quran.",
            reference = "Sahih al-Bukhari 5013"
        ),

        // PROPHETS (AS)
        QuizQuestion(
            id = 8,
            category = QuizCategory.PROPHETS,
            question = "Which Prophet is mentioned most frequently by name in the Holy Quran?",
            options = listOf("Prophet Ibrahim (AS)", "Prophet Isa (AS)", "Prophet Musa (AS)", "Prophet Nuh (AS)"),
            correctIndex = 2,
            explanation = "Prophet Musa (Moses, AS) is mentioned by name over 130 times across the Quran.",
            reference = "Holy Quran"
        ),
        QuizQuestion(
            id = 9,
            category = QuizCategory.PROPHETS,
            question = "Which Prophet was titled 'Khalilullah' (Friend of Allah)?",
            options = listOf("Prophet Yusuf (AS)", "Prophet Ibrahim (AS)", "Prophet Dawud (AS)", "Prophet Sulayman (AS)"),
            correctIndex = 1,
            explanation = "Allah took Prophet Ibrahim (AS) as Khalil (an intimate friend).",
            reference = "Surah An-Nisa 4:125"
        ),
        QuizQuestion(
            id = 10,
            category = QuizCategory.PROPHETS,
            question = "Which Prophet built the Holy Kaaba together with his son Ismail (AS)?",
            options = listOf("Prophet Adam (AS)", "Prophet Nuh (AS)", "Prophet Ibrahim (AS)", "Prophet Yaqub (AS)"),
            correctIndex = 2,
            explanation = "Prophet Ibrahim (AS) and Prophet Ismail (AS) raised the foundations of the Kaaba in Makkah.",
            reference = "Surah Al-Baqarah 2:127"
        ),
        QuizQuestion(
            id = 11,
            category = QuizCategory.PROPHETS,
            question = "Which Prophet was swallowed by a great fish and prayed from within the depths?",
            options = listOf("Prophet Yunus (AS)", "Prophet Ayyub (AS)", "Prophet Zakariyya (AS)", "Prophet Hud (AS)"),
            correctIndex = 0,
            explanation = "Prophet Yunus (AS) called out 'La ilaha illa Anta Subhanaka inni kuntu minaz-zalimin'.",
            reference = "Surah Al-Anbiya 21:87"
        ),
        QuizQuestion(
            id = 12,
            category = QuizCategory.PROPHETS,
            question = "Which Scripture was revealed to Prophet Dawud (AS)?",
            options = listOf("Tawrat", "Zabur", "Injil", "Suhuf"),
            correctIndex = 1,
            explanation = "Allah granted the Zabur (Psalms) to Prophet Dawud (AS).",
            reference = "Surah An-Nisa 4:163"
        ),
        QuizQuestion(
            id = 13,
            category = QuizCategory.PROPHETS,
            question = "Which Prophet could understand the speech of birds and commanded the winds?",
            options = listOf("Prophet Yusuf (AS)", "Prophet Sulayman (AS)", "Prophet Idris (AS)", "Prophet Ilyas (AS)"),
            correctIndex = 1,
            explanation = "Prophet Sulayman (AS) was taught the language of birds and granted a unique kingdom.",
            reference = "Surah An-Naml 27:16"
        ),

        // SEERAH OF PROPHET MUHAMMAD ﷺ
        QuizQuestion(
            id = 14,
            category = QuizCategory.SEERAH,
            question = "In which cave did Prophet Muhammad ﷺ receive the very first revelation?",
            options = listOf("Cave of Thawr", "Cave of Hira", "Cave of Uhud", "Cave of Badr"),
            correctIndex = 1,
            explanation = "Angel Jibreel (AS) brought the first verses of Surah Al-Alaq to the Prophet ﷺ in Cave Hira on Jabal al-Nour.",
            reference = "Sahih al-Bukhari 3"
        ),
        QuizQuestion(
            id = 15,
            category = QuizCategory.SEERAH,
            question = "Who was the first person to accept Islam?",
            options = listOf("Abu Bakr As-Siddiq (RA)", "Ali ibn Abi Talib (RA)", "Khadijah bint Khuwaylid (RA)", "Zayd ibn Harithah (RA)"),
            correctIndex = 2,
            explanation = "Sayyidah Khadijah (RA), the beloved wife of the Prophet ﷺ, was the very first believer.",
            reference = "Seerah Ibn Hisham"
        ),
        QuizQuestion(
            id = 16,
            category = QuizCategory.SEERAH,
            question = "Which companion accompanied Prophet Muhammad ﷺ during the Hijrah in Cave Thawr?",
            options = listOf("Umar ibn Al-Khattab (RA)", "Uthman ibn Affan (RA)", "Abu Bakr As-Siddiq (RA)", "Bilal ibn Rabah (RA)"),
            correctIndex = 2,
            explanation = "Abu Bakr (RA) accompanied the Prophet ﷺ during the migration to Madinah and is referred to in Surah At-Tawbah 9:40.",
            reference = "Surah At-Tawbah 9:40"
        ),
        QuizQuestion(
            id = 17,
            category = QuizCategory.SEERAH,
            question = "What was the title given to Prophet Muhammad ﷺ by the people of Makkah before Prophethood?",
            options = listOf("Al-Farooq", "As-Sadiq Al-Amin", "Dhun-Nurayn", "Asadullah"),
            correctIndex = 1,
            explanation = "Because of his unmatched honesty and integrity, he was universally called As-Sadiq (The Truthful) and Al-Amin (The Trustworthy).",
            reference = "Seerah Nabawiyyah"
        ),
        QuizQuestion(
            id = 18,
            category = QuizCategory.SEERAH,
            question = "Which was the first mosque built by Prophet Muhammad ﷺ in Madinah?",
            options = listOf("Masjid al-Haram", "Masjid Quba", "Masjid al-Aqsa", "Masjid Qiblatayn"),
            correctIndex = 1,
            explanation = "Upon arriving on the outskirts of Madinah, the Prophet ﷺ laid the foundation of Masjid Quba.",
            reference = "Sahih al-Bukhari 3906"
        ),
        QuizQuestion(
            id = 19,
            category = QuizCategory.SEERAH,
            question = "During which miraculous night journey were the five daily prayers enjoined?",
            options = listOf("Hijrah", "Fath Makkah", "Al-Isra wal-Mi'raj", "Treaty of Hudaybiyyah"),
            correctIndex = 2,
            explanation = "During Al-Isra wal-Mi'raj, the Prophet ﷺ traveled to Masjid al-Aqsa and ascended through the heavens.",
            reference = "Sahih al-Bukhari 349"
        ),

        // ISLAMIC HISTORY
        QuizQuestion(
            id = 20,
            category = QuizCategory.HISTORY,
            question = "Which Caliph initiated the Islamic Hijri calendar?",
            options = listOf("Abu Bakr As-Siddiq (RA)", "Umar ibn Al-Khattab (RA)", "Uthman ibn Affan (RA)", "Ali ibn Abi Talib (RA)"),
            correctIndex = 1,
            explanation = "Umar ibn Al-Khattab (RA) established the Hijri calendar anchored to the Prophet's migration to Madinah.",
            reference = "Tarikh al-Tabari"
        ),
        QuizQuestion(
            id = 21,
            category = QuizCategory.HISTORY,
            question = "Which decisive battle took place on the 17th of Ramadan, 2 AH?",
            options = listOf("Battle of Uhud", "Battle of Badr", "Battle of Khandaq", "Battle of Khaybar"),
            correctIndex = 1,
            explanation = "The Battle of Badr (Yawm al-Furqan) took place in 2 AH with 313 companions.",
            reference = "Surah Aal-i-Imran 3:123"
        ),
        QuizQuestion(
            id = 22,
            category = QuizCategory.HISTORY,
            question = "Who was the first Muezzin (caller to prayer) in Islam?",
            options = listOf("Salman al-Farsi (RA)", "Bilal ibn Rabah (RA)", "Ammar ibn Yasir (RA)", "Abdullah ibn Mas'ud (RA)"),
            correctIndex = 1,
            explanation = "Sayyiduna Bilal ibn Rabah (RA) was appointed by the Prophet ﷺ to call the Adhan.",
            reference = "Sunan Abi Dawud 499"
        ),
        QuizQuestion(
            id = 23,
            category = QuizCategory.HISTORY,
            question = "In which year did the peaceful Conquest of Makkah (Fath Makkah) take place?",
            options = listOf("5 AH", "6 AH", "8 AH", "10 AH"),
            correctIndex = 2,
            explanation = "Fath Makkah occurred in Ramadan, 8 AH, when the Prophet ﷺ forgave the people of Makkah.",
            reference = "Sahih al-Bukhari 4280"
        ),
        QuizQuestion(
            id = 24,
            category = QuizCategory.HISTORY,
            question = "Which Caliph standardized the compilation of the Uthmani Mushaf across the Muslim lands?",
            options = listOf("Abu Bakr (RA)", "Umar (RA)", "Uthman ibn Affan (RA)", "Ali (RA)"),
            correctIndex = 2,
            explanation = "Uthman ibn Affan (RA) commissioned official copies from the compilation preserved from Abu Bakr's era.",
            reference = "Sahih al-Bukhari 4987"
        ),

        // BASIC ISLAMIC TEACHINGS
        QuizQuestion(
            id = 25,
            category = QuizCategory.TEACHINGS,
            question = "How many Pillars of Islam (Arkan al-Islam) are there?",
            options = listOf("4", "5", "6", "7"),
            correctIndex = 1,
            explanation = "The five pillars are Shahadah, Salah, Zakah, Sawm (fasting Ramadan), and Hajj.",
            reference = "Sahih al-Bukhari 8"
        ),
        QuizQuestion(
            id = 26,
            category = QuizCategory.TEACHINGS,
            question = "How many Articles of Faith (Arkan al-Iman) are there?",
            options = listOf("5", "6", "7", "4"),
            correctIndex = 1,
            explanation = "Iman comprises belief in Allah, His Angels, His Books, His Messengers, the Last Day, and Divine Decree (Qadr).",
            reference = "Sahih Muslim 8"
        ),
        QuizQuestion(
            id = 27,
            category = QuizCategory.TEACHINGS,
            question = "Which prayer is offered just before sunrise?",
            options = listOf("Dhuhr", "Fajr", "Asr", "Isha"),
            correctIndex = 1,
            explanation = "Fajr is the dawn prayer consisting of 2 Fard rak'ahs (and 2 emphasized Sunnah rak'ahs before it).",
            reference = "Fiqh of Salah"
        ),
        QuizQuestion(
            id = 28,
            category = QuizCategory.TEACHINGS,
            question = "What is the standard annual rate of Zakah on qualifying savings and gold?",
            options = listOf("1.5%", "2.5%", "5%", "10%"),
            correctIndex = 1,
            explanation = "Zakah on cash, gold, silver, and trade goods held for one lunar year above Nisab is 2.5%.",
            reference = "Sunan Abi Dawud 1572"
        ),
        QuizQuestion(
            id = 29,
            category = QuizCategory.TEACHINGS,
            question = "What does the phrase 'SubhanAllah' mean?",
            options = listOf("All praise is for Allah", "Glory be to Allah (He is free of imperfection)", "Allah is the Greatest", "I seek forgiveness from Allah"),
            correctIndex = 1,
            explanation = "SubhanAllah declares Allah's absolute perfection and transcendence above all flaws.",
            reference = "Islamic Vocabulary"
        ),
        QuizQuestion(
            id = 30,
            category = QuizCategory.TEACHINGS,
            question = "Which direction do Muslims face during Salah?",
            options = listOf("East", "Toward Masjid al-Nabawi", "Qibla (Toward the Holy Kaaba in Makkah)", "North"),
            correctIndex = 2,
            explanation = "All Muslims face the Sacred Mosque (Al-Masjid al-Haram) in Makkah during prayer.",
            reference = "Surah Al-Baqarah 2:144"
        )
    )

    val tasbeehDhikrs: List<TasbeehDhikr> = listOf(
        TasbeehDhikr("subhanallah", "SubhanAllah", "سُبْحَانَ اللَّهِ", "اللہ پاک ہے", "Glory be to Allah", 33),
        TasbeehDhikr("alhamdulillah", "Alhamdulillah", "الْحَمْدُ لِلَّهِ", "تمام تعریفیں اللہ کے لیے ہیں", "All praise is due to Allah", 33),
        TasbeehDhikr("allahuakbar", "Allahu Akbar", "اللَّهُ أَكْبَرُ", "اللہ سب سے بڑا ہے", "Allah is the Greatest", 34),
        TasbeehDhikr("astaghfirullah", "Astaghfirullah", "أَسْتَغْفِرُ اللَّهَ", "میں اللہ سے بخشش مانگتا ہوں", "I seek forgiveness from Allah", 100),
        TasbeehDhikr("kalima", "La ilaha illallah", "لَا إِلَٰهَ إِلَّا اللَّهُ", "اللہ کے سوا کوئی معبود نہیں", "There is no deity worthy of worship except Allah", 100),
        TasbeehDhikr("durood", "Durood Sharif", "اللَّهُمَّ صَلِّ عَلَى مُحَمَّدٍ", "اے اللہ محمد ﷺ پر رحمت نازل فرما", "O Allah, send blessings upon Muhammad ﷺ", 100)
    )

    val worldCities: List<CityLocation> = listOf(
        CityLocation("Lahore", "Pakistan", 31.5204, 74.3587, "Asia/Karachi"),
        CityLocation("Karachi", "Pakistan", 24.8607, 67.0011, "Asia/Karachi"),
        CityLocation("Islamabad", "Pakistan", 33.6844, 73.0479, "Asia/Karachi"),
        CityLocation("Rawalpindi", "Pakistan", 33.5651, 73.0169, "Asia/Karachi"),
        CityLocation("Faisalabad", "Pakistan", 31.4504, 73.1350, "Asia/Karachi"),
        CityLocation("Multan", "Pakistan", 30.1575, 71.5249, "Asia/Karachi"),
        CityLocation("Peshawar", "Pakistan", 34.0151, 71.5249, "Asia/Karachi"),
        CityLocation("Quetta", "Pakistan", 30.1798, 66.9750, "Asia/Karachi"),
        CityLocation("Sialkot", "Pakistan", 32.4945, 74.5229, "Asia/Karachi"),
        CityLocation("Makkah", "Saudi Arabia", 21.4225, 39.8262, "Asia/Riyadh"),
        CityLocation("Madinah", "Saudi Arabia", 24.4672, 39.6112, "Asia/Riyadh"),
        CityLocation("Riyadh", "Saudi Arabia", 24.7136, 46.6753, "Asia/Riyadh"),
        CityLocation("Dubai", "UAE", 25.2048, 55.2708, "Asia/Dubai"),
        CityLocation("Abu Dhabi", "UAE", 24.4539, 54.3773, "Asia/Dubai"),
        CityLocation("Doha", "Qatar", 25.2854, 51.5310, "Asia/Qatar"),
        CityLocation("Istanbul", "Turkey", 41.0082, 28.9784, "Europe/Istanbul"),
        CityLocation("Cairo", "Egypt", 30.0444, 31.2357, "Africa/Cairo"),
        CityLocation("Kuala Lumpur", "Malaysia", 3.1390, 101.6869, "Asia/Kuala_Lumpur"),
        CityLocation("Jakarta", "Indonesia", -6.2088, 106.8456, "Asia/Jakarta"),
        CityLocation("Dhaka", "Bangladesh", 23.8103, 90.4125, "Asia/Dhaka"),
        CityLocation("New Delhi", "India", 28.6139, 77.2090, "Asia/Kolkata"),
        CityLocation("Mumbai", "India", 19.0760, 72.8777, "Asia/Kolkata"),
        CityLocation("London", "United Kingdom", 51.5072, -0.1276, "Europe/London"),
        CityLocation("Birmingham", "United Kingdom", 52.4862, -1.8904, "Europe/London"),
        CityLocation("New York", "USA", 40.7128, -74.0060, "America/New_York"),
        CityLocation("Chicago", "USA", 41.8781, -87.6298, "America/Chicago"),
        CityLocation("Toronto", "Canada", 43.6532, -79.3832, "America/Toronto"),
        CityLocation("Sydney", "Australia", -33.8688, 151.2093, "Australia/Sydney")
    )
}
