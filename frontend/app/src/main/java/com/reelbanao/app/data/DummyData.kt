package com.reelbanao.app.data

import com.reelbanao.app.models.*

object DummyData {

    val categories = listOf(
        Category("cat_trending", "Trending Reels", "ट्रेंडिंग रील्स", "Whatshot", 420, true),
        Category("cat_insta_reels", "Instagram Reels", "इंस्टाग्राम रील्स", "CameraAlt", 380, true),
        Category("cat_birthday", "Birthday", "जन्मदिन", "Cake", 260),
        Category("cat_love", "Love", "प्यार व रोमांस", "Favorite", 340, true),
        Category("cat_sad", "Sad Status", "सैड स्टेटस", "SentimentDissatisfied", 190),
        Category("cat_attitude", "Attitude", "एटीट्यूड", "FlashOn", 310, true),
        Category("cat_cinematic", "Cinematic", "सिनेमैटिक", "Movie", 290, true),
        Category("cat_travel", "Travel", "सफ़र व व्लॉग", "FlightTakeoff", 275),
        Category("cat_wedding", "Wedding", "शादी ब्याह", "Celebration", 210),
        Category("cat_festival", "Festival", "त्योहार", "Festival", 330),
        Category("cat_friendship", "Friendship", "दोस्ती व यारी", "People", 185),
        Category("cat_family", "Family", "परिवार", "Diversity3", 160),
        Category("cat_slideshow", "Photo Slideshow", "फोटो स्लाइड शो", "Collections", 410),
        Category("cat_slowmo", "Slow Motion", "स्लो मोशन (Smooth)", "SlowMotionVideo", 240, true),
        Category("cat_beatsync", "Beat Sync", "बीट सिंक (Dhol & Bass)", "GraphicEq", 520, true),
        Category("cat_motivation", "Motivation", "मोटिवेशन", "FitnessCenter", 195),
        Category("cat_nature", "Nature", "कुदरत व हरियाली", "Forest", 170),
        Category("cat_business", "Business", "बिज़नेस व प्रोमो", "Storefront", 140),
        Category("cat_yt_shorts", "YouTube Shorts", "यूट्यूब शॉर्ट्स", "SmartDisplay", 310),
        Category("cat_insta_stories", "Instagram Stories", "इंस्टाग्राम स्टोरीज़", "AutoAwesomeMotion", 280)
    )

    val templates = listOf(
        Template(
            id = "tmpl_01",
            title = "Cyber Beat Drop 3D",
            categoryId = "cat_beatsync",
            categoryName = "Beat Sync",
            coverUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?w=600",
            slots = listOf(
                TemplateSlot(0, 1800, "Intro Shot"),
                TemplateSlot(1, 1200, "Beat 1"),
                TemplateSlot(2, 900, "Beat 2"),
                TemplateSlot(3, 1100, "Beat 3"),
                TemplateSlot(4, 3000, "Drop Climax")
            ),
            totalDurationMs = 8000,
            usesCount = 142000,
            likesCount = 38400,
            isTrending = true,
            isPopular = true,
            aspectRatio = "9:16",
            musicName = "Neon Bass Drop - DJ Pulse",
            tags = listOf("Fast Cuts", "Flash", "Bass")
        ),
        Template(
            id = "tmpl_02",
            title = "Aesthetic Vintage Grain",
            categoryId = "cat_trending",
            categoryName = "Trending Reels",
            coverUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=600",
            slots = listOf(
                TemplateSlot(0, 3500, "Memory 1"),
                TemplateSlot(1, 3500, "Memory 2"),
                TemplateSlot(2, 4000, "Memory 3")
            ),
            totalDurationMs = 11000,
            usesCount = 89000,
            likesCount = 21000,
            isTrending = true,
            isLatest = true,
            aspectRatio = "9:16",
            musicName = "Golden Hour Lo-Fi Chill",
            tags = listOf("Vintage", "Grain", "Lo-Fi")
        ),
        Template(
            id = "tmpl_03",
            title = "Attitude Mafia BGM 4K",
            categoryId = "cat_attitude",
            categoryName = "Attitude",
            coverUrl = "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=600",
            slots = listOf(
                TemplateSlot(0, 2200, "Entry Walk"),
                TemplateSlot(1, 1800, "Glasses Flick"),
                TemplateSlot(2, 4000, "Slow-Mo Look")
            ),
            totalDurationMs = 8000,
            usesCount = 210000,
            likesCount = 65000,
            isTrending = true,
            isPopular = true,
            aspectRatio = "9:16",
            musicName = "Villain Theme - Slowed & Reverb",
            tags = listOf("Attitude", "Slowmo", "Bass")
        ),
        Template(
            id = "tmpl_04",
            title = "Romantic Sunset Shayari",
            categoryId = "cat_love",
            categoryName = "Love",
            coverUrl = "https://images.unsplash.com/photo-1518199266791-5375a83190b7?w=600",
            slots = listOf(
                TemplateSlot(0, 4000, "Couple Hand In Hand"),
                TemplateSlot(1, 4500, "Sunset Smile"),
                TemplateSlot(2, 3500, "Quote Reveal")
            ),
            totalDurationMs = 12000,
            usesCount = 67000,
            likesCount = 18500,
            isLatest = true,
            isFavorite = true,
            aspectRatio = "9:16",
            musicName = "Dil Ki Baat - Acoustic Guitar",
            tags = listOf("Romantic", "Shayari", "Sunset")
        ),
        Template(
            id = "tmpl_05",
            title = "Himalayan Cinematic Wander",
            categoryId = "cat_cinematic",
            categoryName = "Cinematic",
            coverUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=600",
            slots = listOf(
                TemplateSlot(0, 2500, "Drone Peak"),
                TemplateSlot(1, 3000, "Road Trip"),
                TemplateSlot(2, 2800, "Campfire Fog"),
                TemplateSlot(3, 3700, "Horizon Peak")
            ),
            totalDurationMs = 12000,
            usesCount = 115000,
            likesCount = 34000,
            isPopular = true,
            aspectRatio = "9:16",
            musicName = "Epic Mountain Ambience",
            tags = listOf("4K", "Mountains", "Drone")
        ),
        Template(
            id = "tmpl_06",
            title = "Royal Indian Wedding Glow",
            categoryId = "cat_wedding",
            categoryName = "Wedding",
            coverUrl = "https://images.unsplash.com/photo-1583939003579-730e3918a45a?w=600",
            slots = listOf(
                TemplateSlot(0, 3000, "Dulhan Entry"),
                TemplateSlot(1, 2500, "Varmala Moment"),
                TemplateSlot(2, 4500, "Pheras Glow")
            ),
            totalDurationMs = 10000,
            usesCount = 76000,
            likesCount = 22400,
            aspectRatio = "9:16",
            musicName = "Mangalam - Shehnai Fusion",
            tags = listOf("Wedding", "Royal", "Haldi")
        ),
        Template(
            id = "tmpl_07",
            title = "Birthday Celebration Confetti Pop",
            categoryId = "cat_birthday",
            categoryName = "Birthday",
            coverUrl = "https://images.unsplash.com/photo-1513151233558-d860c5398176?w=600",
            slots = listOf(
                TemplateSlot(0, 2000, "Cake Cutting"),
                TemplateSlot(1, 1500, "Sparkler Pop"),
                TemplateSlot(2, 2500, "Group Cheer")
            ),
            totalDurationMs = 6000,
            usesCount = 94000,
            likesCount = 29000,
            isTrending = true,
            aspectRatio = "9:16",
            musicName = "Happy Birthday Modern Remix",
            tags = listOf("Birthday", "Celebration", "Cake")
        ),
        Template(
            id = "tmpl_08",
            title = "Ultra Smooth 120FPS Slow Mo",
            categoryId = "cat_slowmo",
            categoryName = "Slow Motion",
            coverUrl = "https://images.unsplash.com/photo-1517649763962-0c623266ddc0?w=600",
            slots = listOf(
                TemplateSlot(0, 4000, "Hair Flip / Action"),
                TemplateSlot(1, 5000, "Water Splash")
            ),
            totalDurationMs = 9000,
            usesCount = 188000,
            likesCount = 57000,
            isPopular = true,
            isFavorite = true,
            aspectRatio = "9:16",
            musicName = "Ethereal Echoes - Slow Motion",
            tags = listOf("120fps", "Optical Flow", "Smooth")
        ),
        Template(
            id = "tmpl_09",
            title = "30 Photos 5-Second Flash Slideshow",
            categoryId = "cat_slideshow",
            categoryName = "Photo Slideshow",
            coverUrl = "https://images.unsplash.com/photo-1492684223066-81342ee5ff30?w=600",
            slots = (1..10).map { TemplateSlot(it - 1, 600, "Photo $it") },
            totalDurationMs = 6000,
            usesCount = 310000,
            likesCount = 98000,
            isTrending = true,
            aspectRatio = "9:16",
            musicName = "Fast Snaps - Tik Beat",
            tags = listOf("Photo Recap", "Rapid Flash", "Memories")
        ),
        Template(
            id = "tmpl_10",
            title = "Gym Beast Mode Motivation",
            categoryId = "cat_motivation",
            categoryName = "Motivation",
            coverUrl = "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=600",
            slots = listOf(
                TemplateSlot(0, 2000, "Deadlift Setup"),
                TemplateSlot(1, 2000, "Heavy Rep"),
                TemplateSlot(2, 3000, "Victory Breath")
            ),
            totalDurationMs = 7000,
            usesCount = 82000,
            likesCount = 24000,
            aspectRatio = "9:16",
            musicName = "Hardstyle Phonk - Workout Energy",
            tags = listOf("Fitness", "Phonk", "Beast")
        )
    )

    val audioTracks = listOf(
        AudioTrack("aud_01", "Dholak Bass Drop", "DJ Rohit", 28000, "", "https://example.com/audio1.mp3", "Beat Sync", listOf(1200, 2400, 3600, 4800, 7200), true, true, 89000),
        AudioTrack("aud_02", "Midnight Lo-Fi Chill", "Acoustic Cafe", 45000, "", "https://example.com/audio2.mp3", "Vlog", listOf(4000, 8000, 16000), false, true, 54000),
        AudioTrack("aud_03", "Phonk Samurai Tokyo", "Kenta Drift", 32000, "", "https://example.com/audio3.mp3", "Trending", listOf(800, 1600, 2400, 3200, 4800), true, false, 142000),
        AudioTrack("aud_04", "Romantic Piano Serenade", "Armaan Melody", 60000, "", "https://example.com/audio4.mp3", "Romantic", listOf(3000, 6000, 9000), false, false, 32000),
        AudioTrack("aud_05", "Cinematic Trailer Boom", "Hans Impact", 24000, "", "https://example.com/audio5.mp3", "Cinematic", listOf(2000, 5000, 10000), true, true, 76000),
        AudioTrack("aud_06", "Sufi Flute Euphoria", "Mystic Souls", 52000, "", "https://example.com/audio6.mp3", "BGM", listOf(4000, 8000), false, false, 28000)
    )

    val filtersAndEffects = listOf(
        FilterEffect("eff_glitch", "Cyber Glitch", EffectType.VISUAL_EFFECT, "Glitch", 0xFFFF2A6D, 0.85f),
        FilterEffect("eff_vhs", "VHS Retro 90s", EffectType.VISUAL_EFFECT, "Retro", 0xFF00E5FF, 0.70f),
        FilterEffect("eff_film", "Cinematic 35mm", EffectType.FILTER, "Cinematic", 0xFFFFB800, 0.90f),
        FilterEffect("eff_light_leak", "Sun Light Leak", EffectType.VISUAL_EFFECT, "Light", 0xFFFF6584, 0.65f),
        FilterEffect("eff_black_gold", "Moody Black & Gold", EffectType.FILTER, "Mood", 0xFFE0A96D, 0.80f),
        FilterEffect("eff_rgb_split", "RGB Chromatic Split", EffectType.VISUAL_EFFECT, "Glitch", 0xFF7052FF, 0.75f),
        FilterEffect("eff_glow", "Dreamy Soft Glow", EffectType.VISUAL_EFFECT, "Mood", 0xFFB388FF, 0.60f),
        FilterEffect("eff_film_grain", "Vintage Kodak Grain", EffectType.FILTER, "Retro", 0xFF8D6E63, 0.50f)
    )

    val aiStyles = listOf(
        AiEditStyle(
            id = "ai_beat_punch",
            name = "Beat-Punch High Energy",
            description = "हर एक म्यूजिक बीट पर फास्ट कट्स, फ्लैश और 3D ज़ूम इफेक्ट्स अपने आप सिंक होंगे।",
            recommendedMood = "Aggressive / Party / Gym",
            suggestedMusicGenre = "Hardstyle & Phonk",
            gradientColors = listOf(0xFFFF2A6D, 0xFF7052FF),
            tags = listOf("Fast Cuts", "Camera Shakes", "Bass Sync")
        ),
        AiEditStyle(
            id = "ai_cinematic_travel",
            name = "Cinematic Storyteller",
            description = "स्मूथ स्पीड-रैंपिंग, 24fps फिल्म लुक और ऑटोमैटिक कलर ग्रेडिंग के साथ।",
            recommendedMood = "Vlog / Travel / Aesthetic",
            suggestedMusicGenre = "Acoustic & Ambient",
            gradientColors = listOf(0xFF00E5FF, 0xFF00D26A),
            tags = listOf("Speed Ramp", "Film LUTs", "Smooth Pan")
        ),
        AiEditStyle(
            id = "ai_retro_nostalgia",
            name = "Retro VHS Memories",
            description = "पुराने 90s के कैमरे जैसी डेट स्टैम्प, विंटेज वॉर्म टोन और लाइट लीक्स।",
            recommendedMood = "Sad / Romantic / Memories",
            suggestedMusicGenre = "Lo-Fi & Acoustic",
            gradientColors = listOf(0xFFFFB800, 0xFFFF6584),
            tags = listOf("VHS Grain", "Warm Tones", "Date Stamp")
        ),
        AiEditStyle(
            id = "ai_smart_slideshow",
            name = "Smart Face-Sync Slideshow",
            description = "AI अपने आप चेहरों को डिटेक्ट करके बिना किसी का सिर काटे परफेक्ट क्रॉप करेगा।",
            recommendedMood = "Birthday / Family / Friends",
            suggestedMusicGenre = "Pop & Uplifting",
            gradientColors = listOf(0xFF7052FF, 0xFF00E5FF),
            tags = listOf("Face Detection", "Smart Crop", "Smooth Fade")
        )
    )

    val sampleProjects = listOf(
        Project(
            id = "proj_01",
            title = "Manali Roadtrip Reel",
            coverUri = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=600",
            durationMs = 15400,
            aspectRatio = "9:16",
            resolution = "1080p",
            fps = 60,
            updatedAt = System.currentTimeMillis() - 3600000L * 2,
            clips = listOf(
                VideoClip("clip_1", "content://media/1", "Bike_Pass.mp4", 5000),
                VideoClip("clip_2", "content://media/2", "Snow_Peak.mp4", 6000),
                VideoClip("clip_3", "content://media/3", "Campfire.mp4", 4400)
            )
        ),
        Project(
            id = "proj_02",
            title = "Birthday Surprise Reel",
            coverUri = "https://images.unsplash.com/photo-1513151233558-d860c5398176?w=600",
            durationMs = 9200,
            aspectRatio = "9:16",
            resolution = "1080p",
            fps = 30,
            updatedAt = System.currentTimeMillis() - 3600000L * 28,
            clips = listOf(
                VideoClip("clip_4", "content://media/4", "Cake_Cut.mp4", 4500),
                VideoClip("clip_5", "content://media/5", "Confetti.mp4", 4700)
            )
        ),
        Project(
            id = "proj_03",
            title = "Gym PR Deadlift 180kg",
            coverUri = "https://images.unsplash.com/photo-1534438327276-14e5300c3a48?w=600",
            durationMs = 7800,
            aspectRatio = "9:16",
            resolution = "4K",
            fps = 60,
            updatedAt = System.currentTimeMillis() - 3600000L * 72,
            clips = listOf(
                VideoClip("clip_6", "content://media/6", "Lift.mp4", 7800)
            )
        )
    )

    val currentUser = User(
        id = "usr_9981",
        name = "Rahul Sharma",
        username = "@rahul_creator",
        email = "rahul@reelbanao.com",
        isVip = false,
        totalProjects = 8,
        totalExports = 24,
        storageUsedMb = 380,
        maxStorageMb = 2048
    )
}

