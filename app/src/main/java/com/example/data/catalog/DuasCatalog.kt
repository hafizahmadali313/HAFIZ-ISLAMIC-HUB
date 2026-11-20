package com.example.data.catalog

import com.example.data.model.DuaCategory
import com.example.data.model.DuaItem

object DuasCatalog {
    val allDuas: List<DuaItem> = listOf(
        // DAILY DUAS
        DuaItem(
            id = "daily_1",
            category = DuaCategory.DAILY,
            title = "Upon Waking Up",
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
            urdu = "تمام تعریفیں اس اللہ کے لیے ہیں جس نے ہمیں موت (نیند) کے بعد زندگی عطا فرمائی اور اسی کی طرف لوٹ کر جانا ہے۔",
            english = "All praise is for Allah who gave us life after having taken it from us and unto Him is the resurrection.",
            reference = "Sahih al-Bukhari 6312"
        ),
        DuaItem(
            id = "daily_2",
            category = DuaCategory.DAILY,
            title = "Leaving the Home",
            arabic = "بِسْمِ اللَّهِ ، تَوَكَّلْتُ عَلَى اللَّهِ ، وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ",
            urdu = "اللہ کے نام سے، میں نے اللہ پر بھروسہ کیا، اور گناہ سے بچنے اور نیکی کرنے کی طاقت صرف اللہ ہی کی توفیق سے ہے۔",
            english = "In the name of Allah, I place my trust in Allah, and there is no might nor power except with Allah.",
            reference = "Sunan Abi Dawud 5095"
        ),
        DuaItem(
            id = "daily_3",
            category = DuaCategory.DAILY,
            title = "Entering the Home",
            arabic = "بِسْمِ اللَّهِ وَلَجْنَا، وَبِسْمِ اللَّهِ خَرَجْنَا، وَعَلَى اللَّهِ رَبِّنَا تَوَكَّلْنَا",
            urdu = "اللہ کے نام سے ہم داخل ہوئے اور اللہ کے نام سے ہم نکلے اور اپنے پروردگار اللہ پر ہم نے بھروسہ کیا۔",
            english = "In the name of Allah we enter, in the name of Allah we leave, and upon our Lord we place our trust.",
            reference = "Sunan Abi Dawud 5096"
        ),
        DuaItem(
            id = "daily_4",
            category = DuaCategory.DAILY,
            title = "Entering the Masjid",
            arabic = "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
            urdu = "اے اللہ! میرے لیے اپنی رحمت کے دروازے کھول دے۔",
            english = "O Allah, open the gates of Your mercy for me.",
            reference = "Sahih Muslim 713"
        ),
        DuaItem(
            id = "daily_5",
            category = DuaCategory.DAILY,
            title = "Leaving the Masjid",
            arabic = "اللَّهُمَّ إِنِّي أَسْأَلُكَ مِنْ فَضْلِكَ",
            urdu = "اے اللہ! بے شک میں تجھ سے تیرا فضل مانگتا ہوں۔",
            english = "O Allah, indeed I ask You from Your bounty.",
            reference = "Sahih Muslim 713"
        ),

        // MORNING & EVENING AZKAR
        DuaItem(
            id = "azkar_1",
            category = DuaCategory.MORNING_EVENING,
            title = "Sayyid-ul-Istighfar (Chief Prayer for Forgiveness)",
            arabic = "اللَّهُمَّ أَنْتَ رَبِّي لَا إِلَهَ إِلَّا أَنْتَ، خَلَقْتَنِي وَأَنَا عَبْدُكَ، وَأَنَا عَلَى عَهْدِكَ وَوَعْدِكَ مَا اسْتَطَعْتُ، أَعُوذُ بِكَ مِنْ شَرِّ مَا صَنَعْتُ، أَبُوءُ لَكَ بِنِعْمَتِكَ عَلَيَّ، وَأَبُوءُ بِذَنْبِي فَاغْفِرْ لِي فَإِنَّهُ لَا يَغْفِرُ الذُّنُوبَ إِلَّا أَنْتَ",
            urdu = "اے اللہ! تو میرا رب ہے، تیرے سوا کوئی معبود نہیں، تو نے مجھے پیدا کیا اور میں تیرا بندہ ہوں، میں اپنی طاقت کے مطابق تیرے عہد اور وعدے پر قائم ہوں۔ میں اپنے کیے کے شر سے تیری پناہ مانگتا ہوں، مجھ پر تیری نعمتوں کا اقرار کرتا ہوں اور اپنے گناہوں کا اعتراف کرتا ہوں، پس مجھے بخش دے کیونکہ تیرے سوا کوئی گناہوں کو معاف نہیں کر سکتا۔",
            english = "O Allah, You are my Lord, none has the right to be worshipped except You. You created me and I am Your servant, and I abide to Your covenant and promise as best I can. I seek refuge in You from the evil of what I have done. I acknowledge Your favor upon me and I acknowledge my sin, so forgive me, for verily none can forgive sins except You.",
            reference = "Sahih al-Bukhari 6306"
        ),
        DuaItem(
            id = "azkar_2",
            category = DuaCategory.MORNING_EVENING,
            title = "Morning Protection & Reliance",
            arabic = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
            urdu = "اس اللہ کے نام کے ساتھ جس کے نام کی برکت سے زمین اور آسمان کی کوئی چیز نقصان نہیں پہنچا سکتی اور وہ خوب سننے والا، سب کچھ جاننے والا ہے۔ (تین مرتبہ)",
            english = "In the name of Allah with whose name nothing is harmed on earth nor in the heavens, and He is the All-Hearing, the All-Knowing. (Recite 3 times)",
            reference = "Sunan Abi Dawud 5088, Tirmidhi 3388"
        ),
        DuaItem(
            id = "azkar_3",
            category = DuaCategory.MORNING_EVENING,
            title = "Evening Gratitude & Protection",
            arabic = "أَمْسَيْنَا وَأَمْسَى الْمُلْكُ لِلَّهِ، وَالْحَمْدُ لِلَّهِ، لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ",
            urdu = "ہم نے شام کی اور اللہ کی بادشاہت نے شام کی، اور تمام تعریفیں اللہ کے لیے ہیں، اللہ کے سوا کوئی معبود نہیں، وہ اکیلا ہے اس کا کوئی شریک نہیں۔",
            english = "We have reached the evening and at this very time all sovereignty belongs to Allah, and all praise is for Allah. None has the right to be worshipped except Allah, alone, without partner.",
            reference = "Sahih Muslim 2723"
        ),
        DuaItem(
            id = "azkar_4",
            category = DuaCategory.MORNING_EVENING,
            title = "Seeking Wellbeing (Afiyah)",
            arabic = "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْعَافِيَةَ فِي الدُّنْيَا وَالْآخِرَةِ",
            urdu = "اے اللہ! میں تجھ سے دنیا اور آخرت میں عافیت کا سوال کرتا ہوں۔",
            english = "O Allah, I ask You for well-being and pardon in this world and in the Hereafter.",
            reference = "Sunan Ibn Majah 3871"
        ),

        // SLEEPING DUAS
        DuaItem(
            id = "sleep_1",
            category = DuaCategory.SLEEPING,
            title = "Before Going to Sleep",
            arabic = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
            urdu = "اے اللہ! تیرے ہی نام کے ساتھ میں مرتا (سوتا) ہوں اور زندہ ہوتا (جاگتا) ہوں۔",
            english = "In Your name, O Allah, I die and I live.",
            reference = "Sahih al-Bukhari 6324"
        ),
        DuaItem(
            id = "sleep_2",
            category = DuaCategory.SLEEPING,
            title = "Protection Before Sleeping",
            arabic = "بِاسْمِكَ رَبِّي وَضَعْتُ جَنْبِي، وَبِكَ أَرْفَعُهُ، فَإِنْ أَمْسَكْتَ نَفْسِي فَارْحَمْهَا، وَإِنْ أَرْسَلْتَهَا فَاحْفَظْهَا بِمَا تَحْفَظُ بِهِ عِبَادَكَ الصَّالِحِينَ",
            urdu = "اے میرے رب! تیرے ہی نام سے میں نے اپنا پہلو (بستر پر) رکھا اور تیری ہی توفیق سے اسے اٹھاؤں گا۔ پس اگر تو میری روح کو روک لے تو اس پر رحم فرما اور اگر چھوڑ دے تو اس کی اس طرح حفاظت فرما جس طرح تو اپنے نیک بندوں کی حفاظت فرماتا ہے۔",
            english = "In Your name my Lord, I lie down and in Your name I rise, so if You should take my soul then have mercy upon it, and if You should return my soul then protect it in the manner You protect Your righteous servants.",
            reference = "Sahih al-Bukhari 6320, Muslim 2714"
        ),

        // EATING DUAS
        DuaItem(
            id = "eat_1",
            category = DuaCategory.EATING,
            title = "Before Eating",
            arabic = "بِسْمِ اللَّهِ",
            urdu = "اللہ کے نام سے شروع کرتا ہوں۔ (اور اگر شروع میں بھول جائیں تو کہیں: بِسْمِ اللَّهِ أَوَّلَهُ وَآخِرَهُ)",
            english = "In the name of Allah. (If forgotten at the start: Bismillahi awwalahu wa akhirahu)",
            reference = "Sunan Abi Dawud 3767"
        ),
        DuaItem(
            id = "eat_2",
            category = DuaCategory.EATING,
            title = "After Finishing a Meal",
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنَا وَسَقَانَا وَجَعَلَنَا مُسْلِمِينَ",
            urdu = "تمام تعریفیں اس اللہ کے لیے ہیں جس نے ہمیں کھلایا اور پلایا اور ہمیں مسلمان بنایا۔",
            english = "All praise is due to Allah who fed us, gave us drink, and made us Muslims.",
            reference = "Sunan Abi Dawud 3850, Tirmidhi 3457"
        ),
        DuaItem(
            id = "eat_3",
            category = DuaCategory.EATING,
            title = "Breaking the Fast (Iftar)",
            arabic = "ذَهَبَ الظَّمَأُ وَابْتَلَّتِ الْعُرُوقُ، وَثَبَتَ الْأَجْرُ إِنْ شَاءَ اللَّهُ",
            urdu = "پیاس بجھ گئی، رگیں تر ہو گئیں اور ان شاء اللہ اجر ثابت ہو گیا۔",
            english = "The thirst is gone, the veins are moistened, and the reward is confirmed, if Allah wills.",
            reference = "Sunan Abi Dawud 2357"
        ),

        // TRAVELLING DUAS
        DuaItem(
            id = "travel_1",
            category = DuaCategory.TRAVELLING,
            title = "Dua for Travelling (Safar)",
            arabic = "سُبْحَانَ الَّذِي سَخَّرَ لَنَا هَذَا وَمَا كُنَّا لَهُ مُقْرِنِينَ وَإِنَّا إِلَى رَبِّنَا لَمُنْقَلِبُونَ",
            urdu = "پاک ہے وہ ذات جس نے اسے ہمارے تابع کر دیا حالانکہ ہم اسے قابو کرنے کی طاقت نہ رکھتے تھے، اور بے شک ہم اپنے رب ہی کی طرف لوٹ کر جانے والے ہیں۔",
            english = "Glory to Him who has subjected this to us, and we could never have it by our efforts. And verily, to our Lord we indeed are to return.",
            reference = "Surah Az-Zukhruf 43:13-14 & Sahih Muslim 1342"
        ),
        DuaItem(
            id = "travel_2",
            category = DuaCategory.TRAVELLING,
            title = "Returning from a Journey",
            arabic = "آيِبُونَ تَائِبُونَ عَابِدُونَ لِرَبِّنَا حَامِدُونَ",
            urdu = "ہم لوٹنے والے ہیں، توبہ کرنے والے ہیں، عبادت کرنے والے ہیں اور اپنے رب کی حمد بیان کرنے والے ہیں۔",
            english = "We return, repenting, worshipping, and praising our Lord.",
            reference = "Sahih Muslim 1344"
        ),

        // MASNOON DUAS
        DuaItem(
            id = "masnoon_1",
            category = DuaCategory.MASNOON,
            title = "For Guidance, Piety & Self-Sufficiency",
            arabic = "اللَّهُمَّ إِنِّي أَسْأَلُكَ الْهُدَى وَالتُّقَى وَالْعَفَافَ وَالْغِنَى",
            urdu = "اے اللہ! میں تجھ سے ہدایت، تقویٰ، پاکدامنی اور بے نیازی کا سوال کرتا ہوں۔",
            english = "O Allah, I ask You for guidance, piety, chastity, and self-sufficiency.",
            reference = "Sahih Muslim 2721"
        ),
        DuaItem(
            id = "masnoon_2",
            category = DuaCategory.MASNOON,
            title = "Seeking Relief from Anxiety & Grief",
            arabic = "اللَّهُمَّ إِنِّي أَعُوذُ بِكَ مِنَ الْهَمِّ وَالْحَزَنِ، وَالْعَجْزِ وَالْكَسَلِ، وَالْبُخْلِ وَالْجُبْنِ",
            urdu = "اے اللہ! میں تیری پناہ مانگتا ہوں فکر اور غم سے، عاجزی اور سستی سے، اور بخل اور بزدلی سے۔",
            english = "O Allah, I seek refuge in You from worry and grief, helplessness and laziness, miserliness and cowardice.",
            reference = "Sahih al-Bukhari 6369"
        ),
        DuaItem(
            id = "masnoon_3",
            category = DuaCategory.MASNOON,
            title = "Beneficial Knowledge & Accepted Deeds",
            arabic = "اللَّهُمَّ إِنِّي أَسْأَلُكَ عِلْمًا نَافِعًا، وَرِزْقًا طَيِّبًا، وَعَمَلًا مُتَقَبَّلًا",
            urdu = "اے اللہ! میں تجھ سے نفع دینے والا علم، پاکیزہ رزق اور مقبول عمل کا سوال کرتا ہوں۔",
            english = "O Allah, I ask You for beneficial knowledge, pure provision, and accepted deeds.",
            reference = "Sunan Ibn Majah 925"
        ),

        // IMPORTANT QURANIC DUAS
        DuaItem(
            id = "quran_1",
            category = DuaCategory.QURANIC,
            title = "Good in This World & the Hereafter",
            arabic = "رَبَّنَآ ءَاتِنَا فِى ٱلدُّنْيَا حَسَنَةً وَفِى ٱلْـَٔاخِرَةِ حَسَنَةً وَقِنَا عَذَابَ ٱلنَّارِ",
            urdu = "اے ہمارے رب! ہمیں دنیا میں بھی بھلائی عطا فرما اور آخرت میں بھی بھلائی عطا فرما اور ہمیں آگ کے عذاب سے بچا لے۔",
            english = "Our Lord, give us in this world [that which is] good and in the Hereafter [that which is] good and protect us from the punishment of the Fire.",
            reference = "Surah Al-Baqarah 2:201"
        ),
        DuaItem(
            id = "quran_2",
            category = DuaCategory.QURANIC,
            title = "Increase in Knowledge",
            arabic = "رَّبِّ زِدْنِى عِلْمًا",
            urdu = "اے میرے رب! میرے علم میں اضافہ فرما۔",
            english = "My Lord, increase me in knowledge.",
            reference = "Surah Ta-Ha 20:114"
        ),
        DuaItem(
            id = "quran_3",
            category = DuaCategory.QURANIC,
            title = "Dua for Parents",
            arabic = "رَّبِّ ٱرْحَمْهُمَا كَمَا رَبَّيَانِى صَغِيرًا",
            urdu = "اے میرے رب! ان دونوں (والدین) پر رحم فرما جیسا کہ انہوں نے بچپن میں میری پرورش کی۔",
            english = "My Lord, have mercy upon them as they brought me up [when I was] small.",
            reference = "Surah Al-Isra 17:24"
        ),
        DuaItem(
            id = "quran_4",
            category = DuaCategory.QURANIC,
            title = "Dua of Prophet Yunus (AS)",
            arabic = "لَّآ إِلَٰهَ إِلَّآ أَنتَ سُبْحَٰنَكَ إِنِّى كُنتُ مِنَ ٱلظَّٰلِمِينَ",
            urdu = "تیرے سوا کوئی معبود نہیں، تو پاک ہے، بے شک میں ہی قصورواروں میں سے تھا۔",
            english = "There is no deity except You; exalted are You. Indeed, I have been of the wrongdoers.",
            reference = "Surah Al-Anbiya 21:87"
        ),
        DuaItem(
            id = "quran_5",
            category = DuaCategory.QURANIC,
            title = "Steadfastness of the Heart",
            arabic = "رَبَّنَا لَا تُزِغْ قُلُوبَنَا بَعْدَ إِذْ هَدَيْتَنَا وَهَبْ لَنَا مِن لَّدُنكَ رَحْمَةً ۚ إِنَّكَ أَنتَ ٱلْوَهَّابُ",
            urdu = "اے ہمارے رب! ہمیں ہدایت دینے کے بعد ہمارے دلوں کو ٹیڑھا نہ کر اور ہمیں اپنے پاس سے رحمت عطا فرما، بے شک تو ہی بڑا عطا فرمانے والا ہے۔",
            english = "Our Lord, let not our hearts deviate after You have guided us and grant us from Yourself mercy. Indeed, You are the Bestower.",
            reference = "Surah Aal-i-Imran 3:8"
        )
    )
}
