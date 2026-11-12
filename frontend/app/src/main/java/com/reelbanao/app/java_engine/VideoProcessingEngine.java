package com.reelbanao.app.java_engine;

import java.util.Locale;

/**
 * Reel Banao - Pure Java Video Processing Engine
 * Handles timeline calculations, bitrate estimations, and frame metrics.
 */
public class VideoProcessingEngine {

    public static final String VERSION = "1.0.0-JavaEngine";

    /**
     * Formats milliseconds to human-readable video timecode (MM:SS.S)
     */
    public static String formatTimecode(long millis) {
        long totalSeconds = millis / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long fractional = (millis % 1000) / 100;
        return String.format(Locale.getDefault(), "%02d:%02d.%d", minutes, seconds, fractional);
    }

    /**
     * Calculates estimated file size in Megabytes based on bitrate and duration
     */
    public static double estimateFileSizeMb(long durationMs, float bitrateMbps) {
        double durationSeconds = durationMs / 1000.0;
        double bits = durationSeconds * (bitrateMbps * 1_000_000.0);
        double bytes = bits / 8.0;
        return bytes / (1024.0 * 1024.0);
    }

    /**
     * Calculates total video frames for rendering
     */
    public static long calculateTotalFrames(long durationMs, int targetFps) {
        double durationSeconds = durationMs / 1000.0;
        return (long) Math.ceil(durationSeconds * targetFps);
    }

    /**
     * Re-scales clip time based on playback speed multiplier
     */
    public static long calculateScaledDuration(long originalDurationMs, float speedMultiplier) {
        if (speedMultiplier <= 0.0f) {
            speedMultiplier = 1.0f;
        }
        return (long) (originalDurationMs / speedMultiplier);
    }
}

