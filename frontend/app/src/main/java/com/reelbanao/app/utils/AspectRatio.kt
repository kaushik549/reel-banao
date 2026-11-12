package com.reelbanao.app.utils

enum class AspectRatio(val label: String, val ratioWidth: Float, val ratioHeight: Float, val subtitle: String) {
    REELS_9_16("9:16", 9f, 16f, "Reels / Shorts / TikTok"),
    INSTA_1_1("1:1", 1f, 1f, "Instagram Post"),
    INSTA_4_5("4:5", 4f, 5f, "Instagram Portrait"),
    YOUTUBE_16_9("16:9", 16f, 9f, "YouTube Landscape"),
    CINEMATIC_21_9("21:9", 21f, 9f, "Cinematic Movie");

    val floatRatio: Float
        get() = ratioWidth / ratioHeight
}

