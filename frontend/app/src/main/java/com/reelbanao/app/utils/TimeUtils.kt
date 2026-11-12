package com.reelbanao.app.utils

import java.util.Locale

object TimeUtils {
    fun formatMsToTime(ms: Long): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        return String.format(Locale.getDefault(), "%02d:%02d", min, sec)
    }

    fun formatMsToDetailedTime(ms: Long): String {
        val totalSec = ms / 1000
        val min = totalSec / 60
        val sec = totalSec % 60
        val millis = (ms % 1000) / 100
        return String.format(Locale.getDefault(), "%02d:%02d.%01d", min, sec, millis)
    }
}

