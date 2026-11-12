package com.reelbanao.app.java_engine;

/**
 * Pure Java Native Video Bridge
 * Connects high-level Android UI with native video player and export configurations
 */
public class NativeVideoBridge {

    public enum AspectRatioType {
        RATIO_9_16(9, 16, "Reels / TikTok"),
        RATIO_1_1(1, 1, "Instagram Square"),
        RATIO_16_9(16, 9, "YouTube Landscape");

        public final int widthRatio;
        public final int heightRatio;
        public final String description;

        AspectRatioType(int widthRatio, int heightRatio, String description) {
            this.widthRatio = widthRatio;
            this.heightRatio = heightRatio;
            this.description = description;
        }

        public float getFloatRatio() {
            return (float) widthRatio / (float) heightRatio;
        }
    }

    public static class ExportConfig {
        public String resolution;
        public int fps;
        public float bitrateMbps;
        public String outputFormat;

        public ExportConfig(String resolution, int fps, float bitrateMbps, String outputFormat) {
            this.resolution = resolution;
            this.fps = fps;
            this.bitrateMbps = bitrateMbps;
            this.outputFormat = outputFormat;
        }

        @Override
        public String toString() {
            return "ExportConfig{" +
                    "resolution='" + resolution + '\'' +
                    ", fps=" + fps +
                    ", bitrateMbps=" + bitrateMbps +
                    ", outputFormat='" + outputFormat + '\'' +
                    '}';
        }
    }

    public static ExportConfig createDefaultConfig() {
        return new ExportConfig("1080p", 30, 12.0f, "MP4");
    }
}

