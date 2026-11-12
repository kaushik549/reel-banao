package com.reelbanao.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.reelbanao.app.editor.EditorViewModel
import com.reelbanao.app.repositories.AudioRepository
import com.reelbanao.app.repositories.EffectsRepository
import com.reelbanao.app.repositories.ProjectRepository
import com.reelbanao.app.repositories.TemplateRepository
import com.reelbanao.app.screens.*

@Composable
fun ReelBanaoNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Splash.route
) {
    val templateRepository = remember { TemplateRepository() }
    val projectRepository = remember { ProjectRepository() }
    val audioRepository = remember { AudioRepository() }
    val effectsRepository = remember { EffectsRepository() }
    val editorViewModel: EditorViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // 1. Splash Screen
        composable(Screen.Splash.route) {
            SplashScreen(
                onSplashComplete = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        // 2. Onboarding Screen
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onFinishOnboarding = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        // 3. Auth Screen
        composable(Screen.Auth.route) {
            AuthScreen(
                onAuthSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }
            )
        }

        // 4. Home Screen
        composable(Screen.Home.route) {
            HomeScreen(
                templateRepository = templateRepository,
                projectRepository = projectRepository,
                onNavigate = { route -> navController.navigate(route) },
                onOpenTemplate = { tmplId -> navController.navigate(Screen.TemplatePreview.createRoute(tmplId)) },
                onOpenEditor = { projId -> navController.navigate(Screen.VideoEditor.createRoute(projId)) },
                onOpenAiEdit = { navController.navigate(Screen.AiAutoEdit.route) }
            )
        }

        // 5. Templates Screen
        composable(Screen.Templates.route) {
            TemplatesScreen(
                templateRepository = templateRepository,
                projectRepository = projectRepository,
                onNavigate = { route -> navController.navigate(route) },
                onOpenTemplate = { tmplId -> navController.navigate(Screen.TemplatePreview.createRoute(tmplId)) },
                onOpenEditor = { projId -> navController.navigate(Screen.VideoEditor.createRoute(projId)) }
            )
        }

        // 6. Template Categories Screen
        composable(Screen.TemplateCategories.route) {
            TemplateCategoriesScreen(
                templateRepository = templateRepository,
                onBackClick = { navController.popBackStack() },
                onCategoryClick = {
                    navController.navigate(Screen.Templates.route)
                }
            )
        }

        // 7. Template Preview Screen
        composable(
            route = Screen.TemplatePreview.route,
            arguments = listOf(navArgument("templateId") { type = NavType.StringType })
        ) { backStackEntry ->
            val templateId = backStackEntry.arguments?.getString("templateId") ?: "tmpl_01"
            TemplatePreviewScreen(
                templateId = templateId,
                templateRepository = templateRepository,
                projectRepository = projectRepository,
                onBackClick = { navController.popBackStack() },
                onLaunchEditor = { projId ->
                    navController.navigate(Screen.VideoEditor.createRoute(projId))
                }
            )
        }

        // 8. Video Editor Screen
        composable(
            route = Screen.VideoEditor.route,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: "proj_01"
            VideoEditorScreen(
                projectId = projectId,
                projectRepository = projectRepository,
                editorViewModel = editorViewModel,
                onBackClick = { navController.popBackStack() },
                onOpenExport = { id -> navController.navigate(Screen.Export.createRoute(id)) },
                onOpenMusicLibrary = { navController.navigate(Screen.MusicLibrary.route) },
                onOpenEffects = { navController.navigate(Screen.Effects.route) },
                onOpenTextSubtitle = { navController.navigate(Screen.TextSubtitle.route) }
            )
        }

        // 9. AI Auto Edit Screen
        composable(Screen.AiAutoEdit.route) {
            AiAutoEditScreen(
                projectRepository = projectRepository,
                onBackClick = { navController.popBackStack() },
                onVideoGenerated = { projId ->
                    navController.navigate(Screen.VideoEditor.createRoute(projId))
                }
            )
        }

        // 10. Music Library Screen
        composable(Screen.MusicLibrary.route) {
            MusicLibraryScreen(
                audioRepository = audioRepository,
                onBackClick = { navController.popBackStack() },
                onTrackSelected = { track ->
                    editorViewModel.addAudioTrack(track)
                }
            )
        }

        // 11. Effects and Filters Screen
        composable(Screen.Effects.route) {
            EffectsScreen(
                effectsRepository = effectsRepository,
                onBackClick = { navController.popBackStack() },
                onEffectApplied = { effectName ->
                    editorViewModel.applyFilter(effectName)
                }
            )
        }

        // 12. Text and Subtitle Screen
        composable(Screen.TextSubtitle.route) {
            TextSubtitleScreen(
                onBackClick = { navController.popBackStack() },
                onAddText = { text ->
                    editorViewModel.addTextLayer(text)
                }
            )
        }

        // 13. My Projects Screen
        composable(Screen.MyProjects.route) {
            MyProjectsScreen(
                projectRepository = projectRepository,
                onNavigate = { route -> navController.navigate(route) },
                onOpenEditor = { projId -> navController.navigate(Screen.VideoEditor.createRoute(projId)) },
                onOpenExport = { projId -> navController.navigate(Screen.Export.createRoute(projId)) }
            )
        }

        // 14. Export Screen
        composable(
            route = Screen.Export.route,
            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: "proj_01"
            ExportScreen(
                projectId = projectId,
                projectRepository = projectRepository,
                onBackClick = { navController.popBackStack() },
                onDoneClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        // 15. Profile and Settings Screen
        composable(Screen.ProfileSettings.route) {
            ProfileSettingsScreen(
                projectRepository = projectRepository,
                onNavigate = { route -> navController.navigate(route) },
                onLogout = {
                    navController.navigate(Screen.Auth.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

