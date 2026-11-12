package com.reelbanao.app.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Auth : Screen("auth")
    object Home : Screen("home")
    object Templates : Screen("templates")
    object TemplateCategories : Screen("template_categories")
    object TemplatePreview : Screen("template_preview/{templateId}") {
        fun createRoute(templateId: String) = "template_preview/$templateId"
    }
    object VideoEditor : Screen("video_editor/{projectId}") {
        fun createRoute(projectId: String) = "video_editor/$projectId"
    }
    object AiAutoEdit : Screen("ai_auto_edit")
    object MusicLibrary : Screen("music_library")
    object Effects : Screen("effects")
    object TextSubtitle : Screen("text_subtitle")
    object MyProjects : Screen("my_projects")
    object Export : Screen("export/{projectId}") {
        fun createRoute(projectId: String) = "export/$projectId"
    }
    object ProfileSettings : Screen("profile")
}

