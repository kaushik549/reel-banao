package com.reelbanao.app.java_engine;

import java.util.ArrayList;
import java.util.List;

/**
 * Pure Java Template Catalog & Filter Manager
 */
public class TemplateManager {

    public static class TemplateItem {
        public String id;
        public String title;
        public String category;
        public int slotsCount;
        public long durationMs;
        public boolean isTrending;

        public TemplateItem(String id, String title, String category, int slotsCount, long durationMs, boolean isTrending) {
            this.id = id;
            this.title = title;
            this.category = category;
            this.slotsCount = slotsCount;
            this.durationMs = durationMs;
            this.isTrending = isTrending;
        }
    }

    private final List<TemplateItem> templateList = new ArrayList<>();

    public TemplateManager() {
        // Sample standard templates in Java
        templateList.add(new TemplateItem("tmpl_01", "Cyber Beat Drop 3D", "Beat Sync", 5, 8000, true));
        templateList.add(new TemplateItem("tmpl_02", "Aesthetic Vintage Grain", "Trending Reels", 3, 11000, true));
        templateList.add(new TemplateItem("tmpl_03", "Attitude Mafia BGM", "Attitude", 3, 8000, true));
        templateList.add(new TemplateItem("tmpl_04", "Romantic Sunset Shayari", "Love", 3, 12000, false));
    }

    public List<TemplateItem> searchTemplates(String keyword) {
        List<TemplateItem> results = new ArrayList<>();
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(templateList);
        }
        String lowerKey = keyword.toLowerCase().trim();
        for (TemplateItem item : templateList) {
            if (item.title.toLowerCase().contains(lowerKey) || item.category.toLowerCase().contains(lowerKey)) {
                results.add(item);
            }
        }
        return results;
    }

    public List<TemplateItem> getTrendingTemplates() {
        List<TemplateItem> trending = new ArrayList<>();
        for (TemplateItem item : templateList) {
            if (item.isTrending) {
                trending.add(item);
            }
        }
        return trending;
    }
}

