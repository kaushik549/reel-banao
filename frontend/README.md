# 🎬 Reel Banao - Professional AI Video Editing Android App (Phase 1 Frontend)

नमस्ते! यह **Reel Banao** Android ऐप्लिकेशन का **Phase 1 (Complete Frontend)** सोर्स कोड है। इसे **Kotlin** और आधुनिक **Jetpack Compose** (Material 3) में बनाया गया है। इसका UI लुक और फील बिल्कुल **CapCut** और **VN Video Editor** जैसा प्रीमियम डार्क थीम पर आधारित है।

---

## 📁 Frontend Folder Structure (फ़ोल्डर संरचना)

```text
frontend/
├── build.gradle.kts                   # रूट प्रोजेक्ट बिल्ड कॉन्फ़िगरेशन
├── settings.gradle.kts                # मॉड्यूल सेटिंग्स
├── gradle.properties                  # JVM व AndroidX सेटिंग्स
├── gradle/
│   ├── libs.versions.toml             # वर्ज़न कैटलॉग (Dependencies)
│   └── wrapper/
│       └── gradle-wrapper.properties  # Gradle 8.7 रैपर
└── app/
    ├── build.gradle.kts               # ऐप मॉड्यूल निर्भरताएं व Compose सेटिंग्स
    ├── proguard-rules.pro             # रिलीज़ ऑप्टिमाइज़ेशन नियम
    └── src/main/
        ├── AndroidManifest.xml        # ऐप अनुमतियां व मुख्य Activity
        ├── res/
        │   ├── values/                # Strings, Colors, Themes (Dark AMOLED)
        │   └── xml/                   # Data extraction व Backup rules
        └── java/com/reelbanao/app/
            ├── MainActivity.kt        # ऐप की मुख्य Activity (Compose Entrypoint)
            ├── ReelBanaoApp.kt        # Application क्लास
            │
            ├── theme/                 # ऐप का डिज़ाइन सिस्टम (CapCut Dark Theme)
            │   ├── Color.kt           # पर्पल, नियॉन स्यान, डार्क चारकोल कलर्स
            │   ├── Theme.kt           # Material3 Dark Color Scheme
            │   └── Type.kt            # फॉन्ट टाइपोग्राफी
            │
            ├── models/                # डेटा मॉडल्स
            │   ├── Category.kt        # 20 श्रेणियां (Trending, Beat Sync, Attitude...)
            │   ├── Template.kt        # 5,000+ टेम्प्लेट्स आर्किटेक्चर व स्लॉट सिस्टम
            │   ├── VideoClip.kt       # ट्रिम, कट, स्पीड, वॉल्यूम वाला वीडियो क्लिप
            │   ├── AudioTrack.kt      # बीजीएम, बीट पॉइंट्स, ऑडियो ट्रैक
            │   ├── FilterEffect.kt    # ग्लिच, वीएचएस, 35mm सिनेमैटिक फिल्टर्स
            │   ├── TextLayer.kt       # टेक्स्ट सबटाइटल, एनिमेशन व फ़ॉन्ट्स
            │   ├── AiEditStyle.kt     # AI ऑटो एडिट स्टाइल्स (Beat-Punch, Cinematic...)
            │   ├── Project.kt         # ड्राफ्ट्स व वीडियो प्रोजेक्ट्स
            │   └── User.kt            # यूज़र प्रोफ़ाइल व स्टोरेज स्टेटिस्टिक्स
            │
            ├── data/
            │   └── DummyData.kt       # सभी 20 कैटेगरीज व डेमो टेम्प्लेट्स का डेटा
            │
            ├── repositories/          # डेटा लेयर (Repositories)
            │   ├── TemplateRepository.kt # 5000+ टेम्प्लेट्स की सर्च, फ़िल्टर व पसंदीदा लॉजिक
            │   ├── ProjectRepository.kt  # ड्राफ्ट्स सेव, डुप्लिकेट, डिलीट व न्यू प्रोजेक्ट
            │   ├── AudioRepository.kt    # म्यूजिक ट्रैक्स व कैटेगरीज
            │   └── EffectsRepository.kt  # फिल्टर्स और वीएफएक्स
            │
            ├── editor/                # वीडियो एडिटिंग इंजन व स्टेट
            │   └── EditorViewModel.kt # प्लेबैक, स्प्लिट, ट्रिम, स्पीड, वॉल्यूम स्टेट होल्डर
            │
            ├── templates/             # टेम्प्लेट इंजन
            │   └── TemplateEngine.kt  # यूज़र की क्लिप्स को टेम्प्लेट में बिठाने का लॉजिक
            │
            ├── utils/                 # यूटिलिटी फंक्शन्स
            │   ├── AspectRatio.kt     # 9:16 (Reels), 1:1, 16:9, 21:9 आस्पेक्ट रेशियो
            │   └── TimeUtils.kt       # टाइमकोड (00:02.4 / 00:15.0) फॉर्मैटिंग
            │
            ├── components/            # दोबारा इस्तेमाल होने वाले UI कंपोनेंट्स
            │   ├── AppTopBar.kt       # शीर्ष बार (Top Bar)
            │   ├── AppBottomBar.kt    # कैपकट-स्टाइल बॉटम नेविगेशन बार (+ बटन सहित)
            │   ├── CommonButtons.kt   # ग्रेडिएंट बटन, फ़िल्टर चिप्स
            │   ├── TemplateCard.kt    # 9:16 टेम्प्लेट कार्ड (लाइक्स, यूज़ेज, प्रीव्यू)
            │   ├── VideoPlayerView.kt # वीडियो कैनवास प्लेयर (फिल्टर्स व सबटाइटल रेंडरर)
            │   └── TimelineScrubber.kt# मल्टि-ट्रैक विजुअल टाइमलाइन स्क्रबर
            │
            ├── navigation/            # नेविगेशन सिस्टम
            │   ├── Screen.kt          # 15 स्क्रीन्स के रूट्स (Routes)
            │   └── NavGraph.kt        # Jetpack Compose नेविगेशन ग्राफ
            │
            └── screens/               # सभी 15 स्क्रीन्स
                ├── SplashScreen.kt            # 1. स्पलैश स्क्रीन (एनिमेटेड लोगो)
                ├── OnboardingScreen.kt        # 2. ऑनबोर्डिंग स्क्रीन (3 गाइडेड कार्ड्स)
                ├── AuthScreen.kt              # 3. लॉगिन व साइनअप स्क्रीन (गेस्ट मोड सहित)
                ├── HomeScreen.kt              # 4. होम स्क्रीन (क्विक एक्शन, बैनर, रीसेंट)
                ├── TemplatesScreen.kt         # 5. टेम्प्लेट्स स्क्रीन (सर्च, फ़िल्टर, ग्रिड)
                ├── TemplateCategoriesScreen.kt# 6. 20 कैटेगरीज स्क्रीन
                ├── TemplatePreviewScreen.kt   # 7. फुल-स्क्रीन टेम्प्लेट प्रीव्यू स्क्रीन
                ├── VideoEditorScreen.kt       # 8. प्रो वीडियो एडिटर (VN/CapCut टाइमलाइन)
                ├── AiAutoEditScreen.kt        # 9. AI ऑटो एडिट स्क्रीन (वर्कफ़्लो)
                ├── MusicLibraryScreen.kt      # 10. म्यूजिक व साउंड लाइब्रेरी
                ├── EffectsScreen.kt           # 11. स्पेशल इफेक्ट्स व कलर फिल्टर्स
                ├── TextSubtitleScreen.kt      # 12. टेक्स्ट, फॉन्ट व ऑटो-सबटाइटल स्क्रीन
                ├── MyProjectsScreen.kt        # 13. माय प्रोजेक्ट्स / ड्राफ्ट्स स्क्रीन
                ├── ExportScreen.kt            # 14. 720p/1080p/4K एक्सपोर्ट व रेंडर स्क्रीन
                └── ProfileSettingsScreen.kt   # 15. प्रोफ़ाइल, सेटिंग्स व कैश मैनेजर
```

---

## 📱 15 स्क्रीन्स का सरल विवरण (Hindi Explanation)

1. **SplashScreen (`SplashScreen.kt`)**: ऐप खुलते ही एनिमेटेड लोगो और "Reel Banao - 100% Free" स्लोगन दिखाता है और सीधे ऑनबोर्डिंग पर ले जाता है।
2. **OnboardingScreen (`OnboardingScreen.kt`)**: नए यूज़र्स को 3 स्लाइड्स में समझाता है— 5000+ टेम्प्लेट्स, प्रो मल्टि-ट्रैक एडिटर और AI ऑटो एडिटिंग।
3. **AuthScreen (`AuthScreen.kt`)**: लॉगिन, साइन अप और **"Continue as Guest"** का विकल्प देता है ताकि बिना लॉगिन के भी ऐप को तुरंत टेस्ट किया जा सके।
4. **HomeScreen (`HomeScreen.kt`)**: मुख्य डैशबोर्ड जहां "New Project" और "AI Auto Edit" के बड़े बटन्स हैं, रीसेंट ड्राफ्ट्स हैं, और ट्रेंडिंग टेम्प्लेट्स की स्ट्रिप है।
5. **TemplatesScreen (`TemplatesScreen.kt`)**: सर्च बार, ट्रेंडिंग/पॉपुलर टैब्स, और 2-कॉलम ग्रिड जो 5,000+ टेम्प्लेट्स को फ़िल्टर करने में सक्षम है।
6. **TemplateCategoriesScreen (`TemplateCategoriesScreen.kt`)**: सभी 20 कैटेगरीज (Trending Reels, Beat Sync, Attitude, Love, Birthday, Slow Motion, YouTube Shorts आदि) को सुंदर ग्रिड में दिखाता है।
7. **TemplatePreviewScreen (`TemplatePreviewScreen.kt`)**: 9:16 वर्टिकल वीडियो प्रीव्यू, म्यूजिक ट्रैक, कितनी क्लिप्स लगेंगी उसकी जानकारी और एक-क्लिक में प्रोजेक्ट बनाने का बटन।
8. **VideoEditorScreen (`VideoEditorScreen.kt`)**: VN और CapCut जैसा असली एडिटर— इसमें कैनवास, प्ले-पॉज़, मल्टि-ट्रैक टाइमलाइन स्क्रबर, स्प्लिट (काटना), ट्रिम, स्पीड कंट्रोल (0.2x से 5x), वॉल्यूम और आस्पेक्ट रेशियो (9:16, 1:1, 16:9) काम करते हैं।
9. **AiAutoEditScreen (`AiAutoEditScreen.kt`)**: यूज़र मीडिया चुनेगा, स्टाइल चुनेगा (जैसे Beat-Punch या Cinematic), म्यूजिक मूड चुनेगा और AI वीडियो जनरेशन का 4-स्टेप एनिमेटेड वर्कफ़्लो चलेगा।
10. **MusicLibraryScreen (`MusicLibraryScreen.kt`)**: ऑडियो ट्रैक सर्च करने, प्ले करके सुनने, बीट पॉइंट्स देखने और वीडियो में म्यूजिक जोड़ने की स्क्रीन।
11. **EffectsScreen (`EffectsScreen.kt`)**: ग्लिच, वीएचएस, रेट्रो, 35mm फिल्म और कलर फिल्टर्स की तीव्रता (Intensity slider) एडजस्ट करके वीडियो पर लगाने की सुविधा।
12. **TextSubtitleScreen (`TextSubtitleScreen.kt`)**: नए टाइटल्स, हिंदी/इंग्लिश फ़ॉन्ट्स, टेक्स्ट कलर्स, एनिमेशन और AI ऑटो-कैप्शन जनरेटर प्रोटोटाइप।
13. **MyProjectsScreen (`MyProjectsScreen.kt`)**: आपके बनाए गए सभी ड्राफ्ट्स की लिस्ट, उन्हें दोबारा एडिट करने, डुप्लिकेट करने या डिलीट करने का मेनू।
14. **ExportScreen (`ExportScreen.kt`)**: 720p, 1080p और 4K क्वालिटी चयन, FPS (24/30/60), बिटरेट स्लाइडर, फ़ाइल साइज़ एस्टिमेटर और एक्सपोर्ट प्रोग्रेस बार। एक्सपोर्ट पूरा होने पर इंस्टाग्राम रील्स व व्हाट्सएप पर शेयर का विकल्प।
15. **ProfileSettingsScreen (`ProfileSettingsScreen.kt`)**: यूज़र स्टेटस, कुल प्रोजेक्ट्स, एक्सपोर्ट्स, डिवाइस मेमोरी (कैश खाली करने का विकल्प) और भाषा सेटिंग्स।

---

## 🚀 इस प्रोजेक्ट को कैसे रन करें (How to Run in Android Studio)

1. अपने कंप्यूटर पर **Android Studio** (Hedgehog, Iguana, Jellyfish, Koala या Ladybug) खोलें।
2. **Open** पर क्लिक करें और इस पाथ को चुनें:
   `c:\Users\RAHUL\Desktop\reel banao\frontend`
3. Android Studio ऑटोमैटिकली `settings.gradle.kts` और `libs.versions.toml` से सभी जरूरी लाइब्रेरीज़ डाउनलोड कर लेगा।
4. ऊपर दिए गए रन बटन (हरा त्रिकोण ▶️) पर क्लिक करें या **Shift + F10** दबाएं।
5. ऐप आपके एमुलेटर या कनेक्टेड रियल एंड्रॉइड मोबाइल पर खुल जाएगी!

