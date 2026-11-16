package com.microcareer.match.presentation.navigation

/**
 * Sealed class representing where a notification tap should land.
 * MainActivity resolves the incoming Intent extras into one of these,
 * and AppNavigation consumes it once after the NavHost is ready.
 */
sealed class NotificationDestination {
    /** Employee tapped a job_matched notification */
    data class JobDetail(val jobId: String) : NotificationDestination()

    /** Employee tapped application_accepted / application_rejected */
    object SavedJobs : NotificationDestination()

    /** Employee tapped a skill_gap notification with a specific module */
    data class LearningDetail(val moduleId: String) : NotificationDestination()

    /** Employee tapped a skill_gap notification without a module id */
    object Learning : NotificationDestination()

    /** Fallback — land on employee home */
    object EmployeeHome : NotificationDestination()
}