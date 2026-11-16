package com.microcareer.match.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {

    // =========================
    // ROOT GRAPHS
    // =========================

    object AuthGraph : Screen("auth_graph")
    object MainGraph : Screen("main_graph")

    // =========================
    // AUTH DESTINATIONS
    // =========================

    object Login    : Screen("login")
    object Register : Screen("register")

    // =========================
    // MAIN DESTINATIONS
    // =========================

    object Home       : Screen("home")
    object SkillInput : Screen("skill_input")
    object Learn      : Screen("learn")           // ← added

    object CvBuilder : Screen("cv_builder?startTab={startTab}") {

        const val ARG_START_TAB = "startTab"

        const val TAB_PROFILE    = 0
        const val TAB_SKILLS     = 1
        const val TAB_EXPERIENCE = 2
        const val TAB_EDUCATION  = 3

        val arguments = listOf(
            navArgument(ARG_START_TAB) {
                type         = NavType.IntType
                defaultValue = TAB_PROFILE
            }
        )

        fun createRoute(startTab: Int = TAB_PROFILE): String =
            "cv_builder?startTab=$startTab"
    }

    object Profile : Screen("profile")

    // =========================
    // JOB DETAILS
    // =========================

    object JobDetails : Screen("job_details/{jobId}") {

        const val ARG_JOB_ID = "jobId"

        fun createRoute(jobId: String): String {
            require(jobId.isNotBlank()) { "Job ID cannot be blank" }
            return "job_details/$jobId"
        }

        val arguments = listOf(
            navArgument(ARG_JOB_ID) {
                type     = NavType.StringType
                nullable = false
            }
        )
    }
    object LearningModuleDetail : Screen("learning_module/{moduleId}") {
        fun createRoute(moduleId: String) = "learning_module/$moduleId"
    }
}