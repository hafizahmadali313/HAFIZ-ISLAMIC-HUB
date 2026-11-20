package com.example.data.catalog

import com.example.data.local.SurahAyahEntity
import com.example.data.model.DailyReminder
import com.example.data.model.DailyVerse
import com.example.data.model.JuzInfo
import com.example.data.model.SurahInfo

object QuranCatalogData {

    val allSurahs: List<SurahInfo> = listOf(
        SurahInfo(1, "الفاتحة", "Al-Fatihah", "The Opening", "الفاتحہ", "Makki", 7, 1),
        SurahInfo(2, "البقرة", "Al-Baqarah", "The Cow", "البقرہ", "Madani", 286, 1),
        SurahInfo(3, "آل عمران", "Aal-i-Imran", "Family of Imran", "آل عمران", "Madani", 200, 3),
        SurahInfo(4, "النساء", "An-Nisa", "The Women", "النساء", "Madani", 176, 4),
        SurahInfo(5, "المائدة", "Al-Ma'idah", "The Table Spread", "المائدہ", "Madani", 120, 6),
        SurahInfo(6, "الأنعام", "Al-An'am", "The Cattle", "الانعام", "Makki", 165, 7),
        SurahInfo(7, "الأعراف", "Al-A'raf", "The Heights", "الاعراف", "Makki", 206, 8),
        SurahInfo(8, "الأنفال", "Al-Anfal", "The Spoils of War", "الانفال", "Madani", 75, 9),
        SurahInfo(9, "التوبة", "At-Tawbah", "The Repentance", "التوبہ", "Madani", 129, 10),
        SurahInfo(10, "يونس", "Yunus", "Prophet Jonah", "یونس", "Makki", 109, 11),
        SurahInfo(11, "هود", "Hud", "Prophet Hud", "ہود", "Makki", 123, 11),
        SurahInfo(12, "يوسف", "Yusuf", "Prophet Joseph", "یوسف", "Makki", 111, 12),
        SurahInfo(13, "الرعد", "Ar-Ra'd", "The Thunder", "الرعد", "Madani", 43, 13),
        SurahInfo(14, "إبراهيم", "Ibrahim", "Prophet Abraham", "ابراہیم", "Makki", 52, 13),
        SurahInfo(15, "الحجر", "Al-Hijr", "The Rocky Tract", "الحجر", "Makki", 99, 14),
        SurahInfo(16, "النحل", "An-Nahl", "The Bee", "النحل", "Makki", 128, 14),
        SurahInfo(17, "الإسراء", "Al-Isra", "The Night Journey", "بنی اسرائیل", "Makki", 111, 15),
        SurahInfo(18, "الكهف", "Al-Kahf", "The Cave", "الکہف", "Makki", 110, 15),
        SurahInfo(19, "مريم", "Maryam", "Mary", "مریم", "Makki", 98, 16),
        SurahInfo(20, "طه", "Ta-Ha", "Ta-Ha", "طٰہٰ", "Makki", 135, 16),
        SurahInfo(21, "الأنبياء", "Al-Anbiya", "The Prophets", "الانبیاء", "Makki", 112, 17),
        SurahInfo(22, "الحج", "Al-Hajj", "The Pilgrimage", "الحج", "Madani", 78, 17),
        SurahInfo(23, "المؤمنون", "Al-Mu'minun", "The Believers", "المؤمنون", "Makki", 118, 18),
        SurahInfo(24, "النور", "An-Nur", "The Light", "النور", "Madani", 64, 18),
        SurahInfo(25, "الفرقان", "Al-Furqan", "The Criterion", "الفرقان", "Makki", 77, 18),
        SurahInfo(26, "الشعراء", "Ash-Shu'ara", "The Poets", "الشعراء", "Makki", 227, 19),
        SurahInfo(27, "النمل", "An-Naml", "The Ant", "النمل", "Makki", 93, 19),
        SurahInfo(28, "القصص", "Al-Qasas", "The Stories", "القصص", "Makki", 88, 20),
        SurahInfo(29, "العنكبوت", "Al-Ankabut", "The Spider", "العنکبوت", "Makki", 69, 20),
        SurahInfo(30, "الروم", "Ar-Rum", "The Romans", "الروم", "Makki", 60, 21),
        SurahInfo(31, "لقمان", "Luqman", "Luqman", "لقمان", "Makki", 34, 21),
        SurahInfo(32, "السجدة", "As-Sajdah", "The Prostration", "السجدہ", "Makki", 30, 21),
        SurahInfo(33, "الأحزاب", "Al-Ahzab", "The Combined Forces", "الاحزاب", "Madani", 73, 21),
        SurahInfo(34, "سبأ", "Saba", "Sheba", "سبا", "Makki", 54, 22),
        SurahInfo(35, "فاطر", "Fatir", "Originator", "فاطر", "Makki", 45, 22),
        SurahInfo(36, "يس", "Ya-Sin", "Ya-Sin", "یٰسٓ", "Makki", 83, 22),
        SurahInfo(37, "الصافات", "As-Saffat", "Those Drawn Up in Ranks", "الصافات", "Makki", 182, 23),
        SurahInfo(38, "ص", "Sad", "The Letter Sad", "صٓ", "Makki", 88, 23),
        SurahInfo(39, "الزمر", "Az-Zumar", "The Troops", "الزمر", "Makki", 75, 23),
        SurahInfo(40, "غافر", "Ghafir", "The Forgiver", "المؤمن", "Makki", 85, 24),
        SurahInfo(41, "فصلت", "Fussilat", "Explained in Detail", "حٰمٓ السجدہ", "Makki", 54, 24),
        SurahInfo(42, "الشورى", "Ash-Shura", "The Consultation", "الشورٰی", "Makki", 53, 25),
        SurahInfo(43, "الزخرف", "Az-Zukhruf", "The Ornaments of Gold", "الزخرف", "Makki", 89, 25),
        SurahInfo(44, "الدخان", "Ad-Dukhan", "The Smoke", "الدخان", "Makki", 59, 25),
        SurahInfo(45, "الجاثية", "Al-Jathiyah", "The Crouching", "الجاثیہ", "Makki", 37, 25),
        SurahInfo(46, "الأحقاف", "Al-Ahqaf", "The Wind-Curved Sandhills", "الاحقاف", "Makki", 35, 26),
        SurahInfo(47, "محمد", "Muhammad", "Prophet Muhammad ﷺ", "محمد", "Madani", 38, 26),
        SurahInfo(48, "الفتح", "Al-Fath", "The Victory", "الفتح", "Madani", 29, 26),
        SurahInfo(49, "الحجرات", "Al-Hujurat", "The Rooms", "الحجرات", "Madani", 18, 26),
        SurahInfo(50, "ق", "Qaf", "The Letter Qaf", "قٓ", "Makki", 45, 26),
        SurahInfo(51, "الذاريات", "Adh-Dhariyat", "The Winnowing Winds", "الذاریات", "Makki", 60, 26),
        SurahInfo(52, "الطور", "At-Tur", "The Mount", "الطور", "Makki", 49, 27),
        SurahInfo(53, "النجم", "An-Najm", "The Star", "النجم", "Makki", 62, 27),
        SurahInfo(54, "القمر", "Al-Qamar", "The Moon", "القمر", "Makki", 55, 27),
        SurahInfo(55, "الرحمن", "Ar-Rahman", "The Beneficent", "الرحمٰن", "Madani", 78, 27),
        SurahInfo(56, "الواقعة", "Al-Waqi'ah", "The Inevitable", "الواقعہ", "Makki", 96, 27),
        SurahInfo(57, "الحديد", "Al-Hadid", "The Iron", "الحدید", "Madani", 29, 27),
        SurahInfo(58, "المجادلة", "Al-Mujadila", "The Pleading Woman", "المجادلہ", "Madani", 22, 28),
        SurahInfo(59, "الحشر", "Al-Hashr", "The Exile", "الحشر", "Madani", 24, 28),
        SurahInfo(60, "الممتحنة", "Al-Mumtahanah", "She That is Examined", "الممتحنہ", "Madani", 13, 28),
        SurahInfo(61, "الصف", "As-Saff", "The Ranks", "الصف", "Madani", 14, 28),
        SurahInfo(62, "الجمعة", "Al-Jumu'ah", "The Congregation, Friday", "الجمعہ", "Madani", 11, 28),
        SurahInfo(63, "المنافقون", "Al-Munafiqun", "The Hypocrites", "المنافقون", "Madani", 11, 28),
        SurahInfo(64, "التغابن", "At-Taghabun", "The Mutual Disillusion", "التغابن", "Madani", 18, 28),
        SurahInfo(65, "الطلاق", "At-Talaq", "The Divorce", "الطلاق", "Madani", 12, 28),
        SurahInfo(66, "التحريم", "At-Tahrim", "The Prohibition", "التحریم", "Madani", 12, 28),
        SurahInfo(67, "الملك", "Al-Mulk", "The Sovereignty", "الملک", "Makki", 30, 29),
        SurahInfo(68, "القلم", "Al-Qalam", "The Pen", "القلم", "Makki", 52, 29),
        SurahInfo(69, "الحاقة", "Al-Haqqah", "The Reality", "الحاقہ", "Makki", 52, 29),
        SurahInfo(70, "المعارج", "Al-Ma'arij", "The Ascending Stairways", "المعارج", "Makki", 44, 29),
        SurahInfo(71, "نوح", "Nuh", "Prophet Noah", "نوح", "Makki", 28, 29),
        SurahInfo(72, "الجن", "Al-Jinn", "The Jinn", "الجن", "Makki", 28, 29),
        SurahInfo(73, "المزمل", "Al-Muzzammil", "The Enshrouded One", "المزمل", "Makki", 20, 29),
        SurahInfo(74, "المدثر", "Al-Muddaththir", "The Cloaked One", "المدثر", "Makki", 56, 29),
        SurahInfo(75, "القيامة", "Al-Qiyamah", "The Resurrection", "القیامہ", "Makki", 40, 29),
        SurahInfo(76, "الإنسان", "Al-Insan", "The Man", "الدہر", "Madani", 31, 29),
        SurahInfo(77, "المرسلات", "Al-Mursalat", "The Emissaries", "المرسلات", "Makki", 50, 29),
        SurahInfo(78, "النبأ", "An-Naba", "The Tidings", "النبأ", "Makki", 40, 30),
        SurahInfo(79, "النازعات", "An-Nazi'at", "Those Who Drag Forth", "النازعات", "Makki", 46, 30),
        SurahInfo(80, "عبس", "Abasa", "He Frowned", "عبس", "Makki", 42, 30),
        SurahInfo(81, "التكوير", "At-Takwir", "The Overthrowing", "التکویر", "Makki", 29, 30),
        SurahInfo(82, "الإنفطار", "Al-Infitar", "The Cleaving", "الانفطار", "Makki", 19, 30),
        SurahInfo(83, "المطففين", "Al-Mutaffifin", "The Defrauding", "المطففین", "Makki", 36, 30),
        SurahInfo(84, "الإنشقاق", "Al-Inshiqaq", "The Sundering", "الانشقاق", "Makki", 25, 30),
        SurahInfo(85, "البروج", "Al-Buruj", "The Mansions of the Stars", "البروج", "Makki", 22, 30),
        SurahInfo(86, "الطارق", "At-Tariq", "The Nightcomer", "الطارق", "Makki", 17, 30),
        SurahInfo(87, "الأعلى", "Al-A'la", "The Most High", "الاعلٰی", "Makki", 19, 30),
        SurahInfo(88, "الغاشية", "Al-Ghashiyah", "The Overwhelming", "الغاشیہ", "Makki", 26, 30),
        SurahInfo(89, "الفجر", "Al-Fajr", "The Dawn", "الفجر", "Makki", 30, 30),
        SurahInfo(90, "البلد", "Al-Balad", "The City", "البلد", "Makki", 20, 30),
        SurahInfo(91, "الشمس", "Ash-Shams", "The Sun", "الشمس", "Makki", 15, 30),
        SurahInfo(92, "الليل", "Al-Layl", "The Night", "اللیل", "Makki", 21, 30),
        SurahInfo(93, "الضحى", "Ad-Duha", "The Morning Hours", "الضحٰی", "Makki", 11, 30),
        SurahInfo(94, "الشرح", "Ash-Sharh", "The Relief", "الم نشرح", "Makki", 8, 30),
        SurahInfo(95, "التين", "At-Tin", "The Fig", "التین", "Makki", 8, 30),
        SurahInfo(96, "العلق", "Al-Alaq", "The Clot", "العلق", "Makki", 19, 30),
        SurahInfo(97, "القدر", "Al-Qadr", "The Power", "القدر", "Makki", 5, 30),
        SurahInfo(98, "البينة", "Al-Bayyinah", "The Clear Proof", "البینہ", "Madani", 8, 30),
        SurahInfo(99, "الزلزلة", "Az-Zalzalah", "The Earthquake", "الزلزال", "Madani", 8, 30),
        SurahInfo(100, "العاديات", "Al-Adiyat", "The Courser", "العادیات", "Makki", 11, 30),
        SurahInfo(101, "القارعة", "Al-Qari'ah", "The Calamity", "القارعہ", "Makki", 11, 30),
        SurahInfo(102, "التكاثر", "At-Takathur", "The Rivalry in World Increase", "التکاثر", "Makki", 8, 30),
        SurahInfo(103, "العصر", "Al-Asr", "The Declining Day", "العصر", "Makki", 3, 30),
        SurahInfo(104, "الهمزة", "Al-Humazah", "The Traducer", "الہمزہ", "Makki", 9, 30),
        SurahInfo(105, "الفيل", "Al-Fil", "The Elephant", "الفیل", "Makki", 5, 30),
        SurahInfo(106, "قريش", "Quraysh", "Quraysh", "قریش", "Makki", 4, 30),
        SurahInfo(107, "الماعون", "Al-Ma'un", "The Small Kindnesses", "الماعون", "Makki", 7, 30),
        SurahInfo(108, "الكوثر", "Al-Kawthar", "The Abundance", "الکوثر", "Makki", 3, 30),
        SurahInfo(109, "الكافرون", "Al-Kafirun", "The Disbelievers", "الکافرون", "Makki", 6, 30),
        SurahInfo(110, "النصر", "An-Nasr", "The Divine Support", "النصر", "Madani", 3, 30),
        SurahInfo(111, "المسد", "Al-Masad", "The Palm Fiber", "اللہب", "Makki", 5, 30),
        SurahInfo(112, "الإخلاص", "Al-Ikhlas", "The Sincerity", "الاخلاص", "Makki", 4, 30),
        SurahInfo(113, "الفلق", "Al-Falaq", "The Daybreak", "الفلق", "Makki", 5, 30),
        SurahInfo(114, "الناس", "An-Nas", "Mankind", "الناس", "Makki", 6, 30)
    )

    val allJuz: List<JuzInfo> = listOf(
        JuzInfo(1, "الم", "Alif Lam Meem", 1, 1),
        JuzInfo(2, "سيقول", "Sayaqool", 2, 142),
        JuzInfo(3, "تلك الرسل", "Tilkal Rusul", 2, 253),
        JuzInfo(4, "لن تنالوا", "Lan Tana Loo", 3, 93),
        JuzInfo(5, "والمحصنات", "Wal Mohsanat", 4, 24),
        JuzInfo(6, "لا يحب الله", "La Yuhibbullah", 4, 148),
        JuzInfo(7, "وإذا سمعوا", "Wa Iza Samiu", 5, 82),
        JuzInfo(8, "ولو أننا", "Wa Lau Annana", 6, 111),
        JuzInfo(9, "قال الملأ", "Qalal Malao", 7, 88),
        JuzInfo(10, "واعلموا", "Wa A'lamu", 8, 41),
        JuzInfo(11, "يعتذرون", "Yatazeroon", 9, 93),
        JuzInfo(12, "وما من دابة", "Wa Mamin Da'abat", 11, 6),
        JuzInfo(13, "وما أبرئ", "Wa Ma Ubrioo", 12, 53),
        JuzInfo(14, "ربما", "Rubama", 15, 1),
        JuzInfo(15, "سبحان الذي", "Subhanallazi", 17, 1),
        JuzInfo(16, "قال ألم", "Qal Alam", 18, 75),
        JuzInfo(17, "اقترب", "Aqtarabo", 21, 1),
        JuzInfo(18, "قد أفلح", "Qadd Aflaha", 23, 1),
        JuzInfo(19, "وقال الذين", "Wa Qalallazina", 25, 21),
        JuzInfo(20, "أمن خلق", "A'man Khalaq", 27, 56),
        JuzInfo(21, "اتل ما أوحي", "Utlu Ma Oohi", 29, 46),
        JuzInfo(22, "ومن يقنت", "Wa Manyaqnut", 33, 31),
        JuzInfo(23, "وما لي", "Wa Mali", 36, 28),
        JuzInfo(24, "فمن أظلم", "Faman Azlam", 39, 32),
        JuzInfo(25, "إليه يرد", "Elahe Yuruddo", 41, 47),
        JuzInfo(26, "حم", "Ha'a Meem", 46, 1),
        JuzInfo(27, "قال فما خطبكم", "Qala Fama Khatbukum", 51, 31),
        JuzInfo(28, "قد سمع الله", "Qadd Sami Allah", 58, 1),
        JuzInfo(29, "تبارك الذي", "Tabarakallazi", 67, 1),
        JuzInfo(30, "عم يتساءلون", "Amma Yatasa'aloon", 78, 1)
    )

    // Pre-bundled verified Surahs for immediate offline reading (Al-Fatiha & Last 10 Surahs)
    fun getBundledSurahAyahs(surahNumber: Int): List<SurahAyahEntity> = when (surahNumber) {
        1 -> listOf(
            SurahAyahEntity("1:1", 1, 1, 1, "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ", "شروع الله کا نام لے کر جو بڑا مہربان نہایت رحم والا ہے", 1, 1),
            SurahAyahEntity("1:2", 1, 2, 2, "ٱلْحَمْدُ لِلَّهِ رَبِّ ٱلْعَٰلَمِينَ", "سب طرح کی تعریف خدا ہی کو (سزاوار) ہے جو تمام مخلوقات کا پروردگار ہے", 1, 1),
            SurahAyahEntity("1:3", 1, 3, 3, "ٱلرَّحْمَٰنِ ٱلرَّحِيمِ", "بڑا مہربان نہایت رحم والا", 1, 1),
            SurahAyahEntity("1:4", 1, 4, 4, "مَٰلِكِ يَوْمِ ٱلدِّينِ", "انصاف کے دن کا حاکم", 1, 1),
            SurahAyahEntity("1:5", 1, 5, 5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "(اے پروردگار) ہم تیری ہی عبادت کرتے ہیں اور تجھ ہی سے مدد مانگتے ہیں", 1, 1),
            SurahAyahEntity("1:6", 1, 6, 6, "ٱهْدِنَا ٱلصِّرَٰطَ ٱلْمُسْتَقِيمَ", "ہم کو سیدھے رستے چلا", 1, 1),
            SurahAyahEntity("1:7", 1, 7, 7, "صِرَٰطَ ٱلَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ ٱلْمَغْضُوبِ عَلَيْهِمْ وَلَا ٱلضَّآلِّينَ", "ان لوگوں کے رستے جن پر تو اپنا فضل و کرم کرتا رہا نہ ان کے جن پر غصے ہوتا رہا اور نہ گمراہوں کے", 1, 1)
        )
        105 -> listOf(
            SurahAyahEntity("105:1", 105, 1, 6189, "أَلَمْ تَرَ كَيْفَ فَعَلَ رَبُّكَ بِأَصْحَٰبِ ٱلْفِيلِ", "کیا تم نے نہیں دیکھا کہ تمہارے پروردگار نے ہاتھی والوں کے ساتھ کیا کیا", 30, 601),
            SurahAyahEntity("105:2", 105, 2, 6190, "أَلَمْ يَجْعَلْ كَيْدَهُمْ فِى تَضْلِيلٍ", "کیا ان کا داؤ غلط نہیں کیا؟", 30, 601),
            SurahAyahEntity("105:3", 105, 3, 6191, "وَأَرْسَلَ عَلَيْهِمْ طَيْرًا أَبَابِيلَ", "اور ان پر جھنڈ کے جھنڈ جانور بھیجے", 30, 601),
            SurahAyahEntity("105:4", 105, 4, 6192, "تَرْمِيهِم بِحِجَارَةٍ مِّن سِجِّيلٍ", "جو ان پر کھنگر کی پتھریاں پھینکتے تھے", 30, 601),
            SurahAyahEntity("105:5", 105, 5, 6193, "فَجَعَلَهُمْ كَعَصْفٍ مَّأْكُولٍۭ", "تو ان کو ایسا کر دیا جیسے کھایا ہوا بھس", 30, 601)
        )
        106 -> listOf(
            SurahAyahEntity("106:1", 106, 1, 6194, "لِإِيلَٰفِ قُرَيْشٍ", "قریش کے مانوس کرنے کے سبب", 30, 602),
            SurahAyahEntity("106:2", 106, 2, 6195, "إِۦلَٰفِهِمْ رِحْلَةَ ٱلشِّتَآءِ وَٱلصَّيْفِ", "(یعنی) ان کو جاڑے اور گرمی کے سفر سے مانوس کرنے کے سبب", 30, 602),
            SurahAyahEntity("106:3", 106, 3, 6196, "فَلْيَعْبُدُوا۟ رَبَّ هَٰذَا ٱلْبَيْتِ", "ان کو چاہئے کہ اس گھر کے مالک کی عبادت کریں", 30, 602),
            SurahAyahEntity("106:4", 106, 4, 6197, "ٱلَّذِىٓ أَطْعَمَهُم مِّن جُوعٍ وَءَامَنَهُم مِّنْ خَوْفٍۭ", "جس نے ان کو بھوک میں کھانا دیا اور خوف سے امن بخشا", 30, 602)
        )
        107 -> listOf(
            SurahAyahEntity("107:1", 107, 1, 6198, "أَرَءَيْتَ ٱلَّذِى يُكَذِّبُ بِٱلدِّينِ", "بھلا تم نے اس شخص کو دیکھا جو (روز) جزا کو جھٹلاتا ہے؟", 30, 602),
            SurahAyahEntity("107:2", 107, 2, 6199, "فَذَٰلِكَ ٱلَّذِى يَدُعُّ ٱلْيَتِيمَ", "یہ وہی (بدبخت) ہے، جو یتیم کو دھکے دیتا ہے", 30, 602),
            SurahAyahEntity("107:3", 107, 3, 6200, "وَلَا يَحُضُّ عَلَىٰ طَعَامِ ٱلْمِسْكِينِ", "اور فقیر کو کھانا کھلانے کے لیے (لوگوں کو) ترغیب نہیں دیتا", 30, 602),
            SurahAyahEntity("107:4", 107, 4, 6201, "فَوَيْلٌ لِّلْمُصَلِّينَ", "تو ایسے نمازیوں کی خرابی ہے", 30, 602),
            SurahAyahEntity("107:5", 107, 5, 6202, "ٱلَّذِينَ هُمْ عَن صَلَاتِهِمْ سَاهُونَ", "جو نماز کی طرف سے غافل رہتے ہیں", 30, 602),
            SurahAyahEntity("107:6", 107, 6, 6203, "ٱلَّذِينَ هُمْ يُرَآءُونَ", "جو ریاکاری کرتے ہیں", 30, 602),
            SurahAyahEntity("107:7", 107, 7, 6204, "وَيَمْنَعُونَ ٱلْمَاعُونَ", "اور برتنے کی چیزیں عاریتہً نہیں دیتے", 30, 602)
        )
        108 -> listOf(
            SurahAyahEntity("108:1", 108, 1, 6205, "إِنَّآ أَعْطَيْنَٰكَ ٱلْكَوْثَرَ", "(اے محمدﷺ) ہم نے تم کو کوثر عطا فرمائی ہے", 30, 602),
            SurahAyahEntity("108:2", 108, 2, 6206, "فَصَلِّ لِرَبِّكَ وَٱنْحَرْ", "تو تم اپنے پروردگار کے لیے نماز پڑھا کرو اور قربانی دیا کرو", 30, 602),
            SurahAyahEntity("108:3", 108, 3, 6207, "إِنَّ شَانِئَكَ هُوَ ٱلْأَبْتَرُ", "کچھ شک نہیں کہ تمہارا دشمن ہی بےاولاد رہے گا", 30, 602)
        )
        109 -> listOf(
            SurahAyahEntity("109:1", 109, 1, 6208, "قُلْ يَٰٓأَيُّهَا ٱلْكَٰفِرُونَ", "(اے پیغمبر ان منکروں سے) کہہ دو کہ اے کافرو!", 30, 603),
            SurahAyahEntity("109:2", 109, 2, 6209, "لَآ أَعْبُدُ مَا تَعْبُدُونَ", "جن (بتوں) کو تم پوچتے ہو ان کو میں نہیں پوجتا", 30, 603),
            SurahAyahEntity("109:3", 109, 3, 6210, "وَلَآ أَنتُمْ عَٰبِدُونَ مَآ أَعْبُدُ", "اور جس (معبود) کی میں عبادت کرتا ہوں اس کی تم عبادت نہیں کرتے", 30, 603),
            SurahAyahEntity("109:4", 109, 4, 6211, "وَلَآ أَنَا۠ عَابِدٌ مَّا عَبَدتُّمْ", "اور میں (پھر کہتا ہوں کہ) جن کی تم پرستش کرتے ہو ان کی میں پرستش کرنے والا نہیں ہوں", 30, 603),
            SurahAyahEntity("109:5", 109, 5, 6212, "وَلَآ أَنتُمْ عَٰبِدُونَ مَآ أَعْبُدُ", "اور نہ تم اس کی بندگی کرنے والے (معلوم ہوتے) ہو جس کی میں بندگی کرتا ہوں", 30, 603),
            SurahAyahEntity("109:6", 109, 6, 6213, "لَكُمْ دِينُكُمْ وَلِىَ دِينِ", "تم اپنے دین پر میں اپنے دین پر", 30, 603)
        )
        110 -> listOf(
            SurahAyahEntity("110:1", 110, 1, 6214, "إِذَا جَآءَ نَصْرُ ٱللَّهِ وَٱلْفَتْحُ", "جب خدا کی مدد آ پہنچی اور فتح (حاصل ہو گئی)", 30, 603),
            SurahAyahEntity("110:2", 110, 2, 6215, "وَرَأَيْتَ ٱلنَّاسَ يَدْخُلُونَ فِى دِينِ ٱللَّهِ أَفْوَاجًا", "اور تم نے دیکھ لیا کہ لوگ غول کے غول خدا کے دین میں داخل ہو رہے ہیں", 30, 603),
            SurahAyahEntity("110:3", 110, 3, 6216, "فَسَبِّحْ بِحَمْدِ رَبِّكَ وَٱسْتَغْفِرْهُ ۚ إِنَّهُۥ كَانَ تَوَّابًۢا", "تو اپنے پروردگار کی تعریف کے ساتھ تسبیح کرو اور اس سے مغفرت مانگو، بےشک وہ معاف کرنے والا ہے", 30, 603)
        )
        111 -> listOf(
            SurahAyahEntity("111:1", 111, 1, 6217, "تَبَّتْ يَدَآ أَبِى لَهَبٍ وَتَبَّ", "ابولہب کے ہاتھ ٹوٹیں اور وہ ہلاک ہو", 30, 603),
            SurahAyahEntity("111:2", 111, 2, 6218, "مَآ أَغْنَىٰ عَنْهُ مَالُهُۥ وَمَا كَسَبَ", "نہ تو اس کا مال ہی اس کے کچھ کام آیا اور نہ وہ جو اس نے کمایا", 30, 603),
            SurahAyahEntity("111:3", 111, 3, 6219, "سَيَصْلَىٰ نَارًا ذَاتَ لَهَبٍ", "وہ جلد بھڑکتی ہوئی آگ میں داخل ہو گا", 30, 603),
            SurahAyahEntity("111:4", 111, 4, 6220, "وَٱمْرَأَتُهُۥ حَمَّالَةَ ٱلْحَطَبِ", "اور اس کی جورو بھی جو ایندھن سر پر اٹھائے پھرتی ہے", 30, 603),
            SurahAyahEntity("111:5", 111, 5, 6221, "فِى جِيدِهَا حَبْلٌ مِّن مَّسَدٍۭ", "اس کے گلے میں مونج کی رسی ہو گی", 30, 603)
        )
        112 -> listOf(
            SurahAyahEntity("112:1", 112, 1, 6222, "قُلْ هُوَ ٱللَّهُ أَحَدٌ", "کہو کہ وہ (ذات پاک جس کا نام) الله (ہے) ایک ہے", 30, 604),
            SurahAyahEntity("112:2", 112, 2, 6223, "ٱللَّهُ ٱلصَّمَدُ", "معبود برحق جو بےنیاز ہے", 30, 604),
            SurahAyahEntity("112:3", 112, 3, 6224, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "نہ کسی کا باپ ہے اور نہ کسی کا بیٹا", 30, 604),
            SurahAyahEntity("112:4", 112, 4, 6225, "وَلَمْ يَكُن لَّهُۥ كُفُوًا أَحَدٌۢ", "اور کوئی اس کا ہمسر نہیں", 30, 604)
        )
        113 -> listOf(
            SurahAyahEntity("113:1", 113, 1, 6226, "قُلْ أَعُوذُ بِرَبِّ ٱلْفَلَقِ", "کہو کہ میں صبح کے پروردگار کی پناہ مانگتا ہوں", 30, 604),
            SurahAyahEntity("113:2", 113, 2, 6227, "مِن شَرِّ مَا خَلَقَ", "ہر چیز کی بدی سے جو اس نے پیدا کی", 30, 604),
            SurahAyahEntity("113:3", 113, 3, 6228, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "اور شب تاریک کی برائی سے جب (اس کا اندھیرا) چھا جائے", 30, 604),
            SurahAyahEntity("113:4", 113, 4, 6229, "وَمِن شَرِّ ٱلنَّفَّٰثَٰتِ فِى ٱلْعُقَدِ", "اور گنڈوں پر (پڑھ پڑھ کر) پھونکنے والیوں کی برائی سے", 30, 604),
            SurahAyahEntity("113:5", 113, 5, 6230, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "اور حسد کرنے والے کی برائی سے جب حسد کرنے لگے", 30, 604)
        )
        114 -> listOf(
            SurahAyahEntity("114:1", 114, 1, 6231, "قُلْ أَعُوذُ بِرَبِّ ٱلنَّاسِ", "کہو کہ میں لوگوں کے پروردگار کی پناہ مانگتا ہوں", 30, 604),
            SurahAyahEntity("114:2", 114, 2, 6232, "مَلِكِ ٱلنَّاسِ", "(یعنی) لوگوں کے حقیقی بادشاہ کی", 30, 604),
            SurahAyahEntity("114:3", 114, 3, 6233, "إِلَٰهِ ٱلنَّاسِ", "لوگوں کے معبود برحق کی", 30, 604),
            SurahAyahEntity("114:4", 114, 4, 6234, "مِن شَرِّ ٱلْوَسْوَاسِ ٱلْخَنَّاسِ", "(شیطان) وسوسہ انداز کی برائی سے جو (خدا کا نام سن کر) پیچھے ہٹ جاتا ہے", 30, 604),
            SurahAyahEntity("114:5", 114, 5, 6235, "ٱلَّذِى يُوَسْوِسُ فِى صُدُورِ ٱلنَّاسِ", "جو لوگوں کے دلوں میں وسوسے ڈالتا ہے", 30, 604),
            SurahAyahEntity("114:6", 114, 6, 6236, "مِنَ ٱلْجِنَّةِ وَٱلنَّاسِ", "وہ جنّات میں سے (ہو) یا انسانوں میں سے", 30, 604)
        )
        else -> emptyList()
    }

    val dailyVerses: List<DailyVerse> = listOf(
        DailyVerse(
            surahName = "Al-Baqarah",
            reference = "Surah Al-Baqarah 2:152",
            arabic = "فَٱذْكُرُونِىٓ أَذْكُرْكُمْ وَٱشْكُرُوا۟ لِى وَلَا تَكْفُرُونِ",
            urdu = "سو تم مجھے یاد کرو، میں تمہیں یاد کروں گا اور میرا شکر کرتے رہو اور ناشکری نہ کرو۔",
            english = "So remember Me; I will remember you. And be grateful to Me and do not deny Me."
        ),
        DailyVerse(
            surahName = "Ash-Sharh",
            reference = "Surah Ash-Sharh 94:5-6",
            arabic = "فَإِنَّ مَعَ ٱلْعُسْرِ يُسْرًا ۝ إِنَّ مَعَ ٱلْعُسْرِ يُسْرًا",
            urdu = "پس بے شک مشکل کے ساتھ آسانی ہے۔ یقیناً مشکل کے ساتھ آسانی ہے۔",
            english = "For indeed, with hardship will be ease. Indeed, with hardship will be ease."
        ),
        DailyVerse(
            surahName = "Ar-Ra'd",
            reference = "Surah Ar-Ra'd 13:28",
            arabic = "أَلَا بِذِكْرِ ٱللَّهِ تَطْمَئِنُّ ٱلْقُلُوبُ",
            urdu = "خبردار! اللہ کے ذکر ہی سے دلوں کو اطمینان نصیب ہوتا ہے۔",
            english = "Unquestionably, by the remembrance of Allah hearts are assured."
        ),
        DailyVerse(
            surahName = "Al-Baqarah",
            reference = "Surah Al-Baqarah 2:286",
            arabic = "لَا يُكَلِّفُ ٱللَّهُ نَفْسًا إِلَّا وُسْعَهَا",
            urdu = "اللہ کسی شخص پر اس کی طاقت سے زیادہ بوجھ نہیں ڈالتا۔",
            english = "Allah does not charge a soul except [with that within] its capacity."
        ),
        DailyVerse(
            surahName = "Az-Zumar",
            reference = "Surah Az-Zumar 39:53",
            arabic = "لَا تَقْنَطُوا۟ مِن رَّحْمَةِ ٱللَّهِ ۚ إِنَّ ٱللَّهَ يَغْفِرُ ٱلذُّنُوبَ جَمِيعًا",
            urdu = "اللہ کی رحمت سے ناامید نہ ہو، بے شک اللہ تمام گناہوں کو بخش دیتا ہے۔",
            english = "Do not despair of the mercy of Allah. Indeed, Allah forgives all sins."
        )
    )

    val dailyReminders: List<DailyReminder> = listOf(
        DailyReminder(
            title = "Consistency in Good Deeds",
            message = "The most beloved deeds to Allah are those that are consistent, even if they are small.",
            source = "Sahih al-Bukhari 6464"
        ),
        DailyReminder(
            title = "Guard Your Salah",
            message = "Establish the five daily prayers with presence of heart; prayer is the anchor of a believer's day.",
            source = "Sahih Muslim 223"
        ),
        DailyReminder(
            title = "Light of the Quran",
            message = "Recite even a few verses of the Holy Quran daily with reflection, for it will intercede for its companions.",
            source = "Sahih Muslim 804"
        ),
        DailyReminder(
            title = "Kindness & Character",
            message = "The best among you are those who have the finest character and show mercy to creation.",
            source = "Sahih al-Bukhari 3559"
        ),
        DailyReminder(
            title = "Gratitude & Dhikr",
            message = "Keep your tongue moist with SubhanAllah, Alhamdulillah, and Astaghfirullah throughout your tasks.",
            source = "Jami' at-Tirmidhi 3375"
        )
    )
}
