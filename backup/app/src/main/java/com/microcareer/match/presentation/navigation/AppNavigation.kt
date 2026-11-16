package com.microcareer.match.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.microcareer.match.presentation.auth.AuthViewModel
import com.microcareer.match.presentation.auth.LoginScreen
import com.microcareer.match.presentation.auth.RegisterScreen
import com.microcareer.match.presentation.employer.EmployerHomeScreen
import com.microcareer.match.presentation.employer.PostJobScreen
import com.microcareer.match.presentation.employer.EmployerProfileScreen
import com.microcareer.match.presentation.employee.EmployeeHomeScreen
import com.microcareer.match.presentation.employee.EmployeeProfileScreen
import com.microcareer.match.presentation.employee.JobDetailScreen
import com.microcareer.match.presentation.employee.SavedAndApplicationsScreen
import com.microcareer.match.presentation.home.HomeViewModel
import com.microcareer.match.presentation.skill.CvBuilderScreen
import com.microcareer.match.presentation.skill.SkillInputScreen
import com.microcareer.match.presentation.skill.SkillsScreen
import com.microcareer.match.presentation.skill.SkillViewModel
import com.microcareer.match.presentation.learning.LearningModuleDetailScreen
import com.microcareer.match.presentation.learning.LearningScreen
import com.microcareer.match.presentation.learning.LearningViewModel

// ─────────────────────────────────────────────────────────
// ROUTES
// ─────────────────────────────────────────────────────────

object Routes {
    const val LOGIN            = "login"
    const val REGISTER         = "register"
    const val SPLASH           = "splash"

    // Employer
    const val EMPLOYER_HOME    = "employer/home"
    const val EMPLOYER_PROFILE = "employer/profile"
    const val POST_JOB         = "employer/post-job"
    const val EDIT_JOB         = "employer/edit-job/{jobId}"

    // Employee nested graph
    const val EMPLOYEE_GRAPH   = "employee_graph"
    const val EMPLOYEE_HOME    = "employee/home"
    const val EMPLOYEE_PROFILE = "employee/profile"
    const val JOB_DETAIL       = "job/{jobId}"

    // Skills & CV (inside employee graph)
    const val SKILLS           = "employee/skills"
    const val SKILL_ADD        = "employee/skills/add"
    const val CV_BUILDER       = "employee/cv-builder/{startTab}"

    // Learning (inside employee graph)
    const val LEARNING         = "employee/learning"
    const val LEARNING_DETAIL  = "employee/learning/{moduleId}"

    // Saved jobs & applications
    const val SAVED_JOBS       = "employee/saved"

    // ── Helpers ───────────────────────────────────────────
    fun editJob(jobId: String)           = "employer/edit-job/$jobId"
    fun jobDetail(jobId: String)         = "job/$jobId"
    fun cvBuilder(startTab: Int = 0)     = "employee/cv-builder/$startTab"
    fun learningDetail(moduleId: String) = "employee/learning/$moduleId"
}

// ─────────────────────────────────────────────────────────
// ROOT NAV HOST
// ─────────────────────────────────────────────────────────

@Composable
fun AppNavigation(
    navController: NavHostController       = rememberNavController(),
    authViewModel: AuthViewModel           = hiltViewModel(),
    notificationDestination: NotificationDestination? = null   // ← NEW
) {

    val authState by authViewModel.uiState.collectAsState()

    val startDestination = if (!authState.isLoggedIn) Routes.LOGIN else Routes.SPLASH

    // ── Notification deep-link ─────────────────────────────
    // Fires once when AppNavigation first enters composition with a
    // non-null destination. We wait until the user is confirmed logged in
    // before navigating so we never deep-link into a protected screen
    // while unauthenticated.
    LaunchedEffect(notificationDestination, authState.isLoggedIn) {
        if (notificationDestination == null) return@LaunchedEffect
        if (!authState.isLoggedIn) return@LaunchedEffect

        when (notificationDestination) {
            is NotificationDestination.JobDetail -> {
                navController.navigate(Routes.EMPLOYEE_GRAPH) {
                    popUpTo(0) { inclusive = true }
                }
                navController.navigate(Routes.jobDetail(notificationDestination.jobId))
            }
            is NotificationDestination.SavedJobs -> {
                navController.navigate(Routes.EMPLOYEE_GRAPH) {
                    popUpTo(0) { inclusive = true }
                }
                navController.navigate(Routes.SAVED_JOBS)
            }
            is NotificationDestination.LearningDetail -> {
                navController.navigate(Routes.EMPLOYEE_GRAPH) {
                    popUpTo(0) { inclusive = true }
                }
                navController.navigate(Routes.learningDetail(notificationDestination.moduleId))
            }
            is NotificationDestination.Learning -> {
                navController.navigate(Routes.EMPLOYEE_GRAPH) {
                    popUpTo(0) { inclusive = true }
                }
                navController.navigate(Routes.LEARNING)
            }
            is NotificationDestination.EmployeeHome -> {
                navController.navigate(Routes.EMPLOYEE_GRAPH) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }
    }

    NavHost(
        navController    = navController,
        startDestination = startDestination
    ) {

        // ── SPLASH ────────────────────────────────────────
        composable(Routes.SPLASH) {
            LaunchedEffect(authState.user) {
                val user = authState.user ?: return@LaunchedEffect
                val dest = if (user.role?.lowercase() == "employer")
                    Routes.EMPLOYER_HOME
                else
                    Routes.EMPLOYEE_GRAPH
                navController.navigate(dest) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        // ── AUTH ──────────────────────────────────────────
        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel            = authViewModel,
                onNavigateToRegister = { navController.navigate(Routes.REGISTER) },
                onLoginSuccess       = {
                    val role = authViewModel.uiState.value.user?.role?.lowercase()
                    val dest = if (role == "employer") Routes.EMPLOYER_HOME else Routes.EMPLOYEE_GRAPH
                    navController.navigate(dest) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                viewModel         = authViewModel,
                onNavigateToLogin = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                },
                onRegisterSuccess = {
                    val role = authViewModel.uiState.value.user?.role?.lowercase()
                    val dest = if (role == "employer") Routes.EMPLOYER_HOME else Routes.EMPLOYEE_GRAPH
                    navController.navigate(dest) {
                        popUpTo(Routes.REGISTER) { inclusive = true }
                    }
                }
            )
        }

        // ── EMPLOYER ──────────────────────────────────────
        composable(Routes.EMPLOYER_HOME) {
            EmployerHomeScreen(
                onNavigateToPostJob = { navController.navigate(Routes.POST_JOB) },
                onNavigateToProfile = { navController.navigate(Routes.EMPLOYER_PROFILE) },
                onEditJob           = { jobId -> navController.navigate(Routes.editJob(jobId)) },
                onLogout            = {
                    authViewModel.logout()
                    navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
                }
            )
        }

        composable(Routes.POST_JOB) {
            PostJobScreen(
                onBack    = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }

        composable(Routes.EDIT_JOB) { entry ->
            val jobId = entry.arguments?.getString("jobId") ?: return@composable
            PostJobScreen(
                editJobId = jobId,
                onBack    = { navController.popBackStack() },
                onSuccess = { navController.popBackStack() }
            )
        }

        composable(Routes.EMPLOYER_PROFILE) {
            EmployerProfileScreen(
                onBack   = { navController.popBackStack() },
                onLogout = {
                    authViewModel.logout()
                    navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
                }
            )
        }

        // ── EMPLOYEE NESTED GRAPH ─────────────────────────
        navigation(
            startDestination = Routes.EMPLOYEE_HOME,
            route            = Routes.EMPLOYEE_GRAPH
        ) {

            composable(Routes.EMPLOYEE_HOME) { entry ->
                val graphEntry = remember(entry) {
                    navController.getBackStackEntry(Routes.EMPLOYEE_GRAPH)
                }
                val homeVm: HomeViewModel = hiltViewModel(graphEntry)
                EmployeeHomeScreen(
                    viewModel             = homeVm,
                    onNavigateToJobDetail = { jobId -> navController.navigate(Routes.jobDetail(jobId)) },
                    onNavigateToProfile   = { navController.navigate(Routes.EMPLOYEE_PROFILE) },
                    onNavigateToSkills    = { navController.navigate(Routes.SKILLS) },
                    onNavigateToLearn     = { navController.navigate(Routes.LEARNING) },
                    onNavigateToSaved     = { navController.navigate(Routes.SAVED_JOBS) },
                    onLogout              = {
                        authViewModel.logout()
                        navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
                    }
                )
            }

            composable(Routes.EMPLOYEE_PROFILE) {
                EmployeeProfileScreen(
                    onBack   = { navController.popBackStack() },
                    onLogout = {
                        authViewModel.logout()
                        navController.navigate(Routes.LOGIN) { popUpTo(0) { inclusive = true } }
                    }
                )
            }

            composable(
                route     = Routes.JOB_DETAIL,
                arguments = listOf(navArgument("jobId") { type = NavType.StringType })
            ) { entry ->
                val graphEntry = remember(entry) {
                    navController.getBackStackEntry(Routes.EMPLOYEE_GRAPH)
                }
                val homeVm: HomeViewModel = hiltViewModel(graphEntry)
                val jobId = entry.arguments?.getString("jobId") ?: return@composable
                JobDetailScreen(
                    jobId     = jobId,
                    viewModel = homeVm,
                    onBack    = { navController.popBackStack() }
                )
            }

            composable(Routes.SAVED_JOBS) { entry ->
                val graphEntry = remember(entry) {
                    navController.getBackStackEntry(Routes.EMPLOYEE_GRAPH)
                }
                val homeVm: HomeViewModel = hiltViewModel(graphEntry)
                SavedAndApplicationsScreen(
                    viewModel  = homeVm,
                    onBack     = { navController.popBackStack() },
                    onJobClick = { jobId -> navController.navigate(Routes.jobDetail(jobId)) }
                )
            }

            // ── Skills & CV ────────────────────────────────
            composable(Routes.SKILLS) { entry ->
                val graphEntry = remember(entry) {
                    navController.getBackStackEntry(Routes.EMPLOYEE_GRAPH)
                }
                val skillVm: SkillViewModel = hiltViewModel(graphEntry)
                SkillsScreen(
                    viewModel  = skillVm,
                    onBack     = { navController.popBackStack() },
                    onAddClick = { navController.navigate(Routes.SKILL_ADD) }
                )
            }

            composable(Routes.SKILL_ADD) { entry ->
                val graphEntry = remember(entry) {
                    navController.getBackStackEntry(Routes.EMPLOYEE_GRAPH)
                }
                val skillVm: SkillViewModel = hiltViewModel(graphEntry)
                SkillInputScreen(
                    viewModel             = skillVm,
                    onBack                = { navController.popBackStack() },
                    onNavigateToCvBuilder = { startTab ->
                        navController.navigate(Routes.cvBuilder(startTab))
                    }
                )
            }

            composable(
                route     = Routes.CV_BUILDER,
                arguments = listOf(
                    navArgument("startTab") {
                        type         = NavType.IntType
                        defaultValue = 0
                    }
                )
            ) { entry ->
                val graphEntry = remember(entry) {
                    navController.getBackStackEntry(Routes.EMPLOYEE_GRAPH)
                }
                val skillVm: SkillViewModel = hiltViewModel(graphEntry)
                val startTab = entry.arguments?.getInt("startTab") ?: 0
                CvBuilderScreen(
                    viewModel = skillVm,
                    startTab  = startTab,
                    onBack    = { navController.popBackStack() }
                )
            }

            // ── Learning ───────────────────────────────────
            composable(Routes.LEARNING) { entry ->
                val graphEntry = remember(entry) {
                    navController.getBackStackEntry(Routes.EMPLOYEE_GRAPH)
                }
                val learningVm: LearningViewModel = hiltViewModel(graphEntry)
                LearningScreen(
                    viewModel                = learningVm,
                    onBack                   = { navController.popBackStack() },
                    onNavigateToModuleDetail = { moduleId ->
                        navController.navigate(Routes.learningDetail(moduleId))
                    }
                )
            }

            composable(
                route     = Routes.LEARNING_DETAIL,
                arguments = listOf(navArgument("moduleId") { type = NavType.StringType })
            ) { entry ->
                val graphEntry = remember(entry) {
                    navController.getBackStackEntry(Routes.EMPLOYEE_GRAPH)
                }
                val learningVm: LearningViewModel = hiltViewModel(graphEntry)
                val moduleId = entry.arguments?.getString("moduleId") ?: return@composable
                LearningModuleDetailScreen(
                    moduleId  = moduleId,
                    viewModel = learningVm,
                    onBack    = { navController.popBackStack() }
                )
            }
        }
    }
}