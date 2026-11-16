
package com.microcareer.match.data.network
import com.microcareer.match.BuildConfig

object ApiConfig {
    /**
     * Base URL for API
     *
     * IMPORTANT: Change based on your environment:
     *
     * - For Android Emulator (localhost):
     *  val BASE_URL: String get() = BuildConfig.API_BASE_URL
     *
     * - For Real Device on same WiFi:
     *   const val BASE_URL = "http://YOUR_COMPUTER_IP:8000/"
     *   (Get your IP: ipconfig/ifconfig)
     *
     * - For Production:
     *   const val BASE_URL = "https://api.microcareer.com/"
     */

    private val BASE_URL_EMULATOR: String get() = BuildConfig.API_BASE_URL
    val BASE_URL: String get() = BuildConfig.API_BASE_URL
    /**
     * API Endpoints
     */
    object Endpoints {
        // Auth
        const val LOGIN = "api/v1/auth/login"
        const val REGISTER = "api/v1/auth/register"
        const val REFRESH_TOKEN = "api/v1/auth/refresh"

        // Users
        const val ME = "api/v1/users/me"
        const val UPDATE_PROFILE = "api/v1/users/profile"

        // Jobs
        const val JOBS = "api/v1/jobs"
        const val JOB_DETAIL = "api/v1/jobs/{id}"
        const val MY_JOBS = "api/v1/jobs/my-jobs"
        const val SEARCH_JOBS = "api/v1/jobs/search"

        // Applications
        const val APPLICATIONS = "api/v1/applications"
        const val MY_APPLICATIONS = "api/v1/applications/my-applications"
        const val APPLICATION_DETAIL = "api/v1/applications/{id}"

        // Reviews
        const val REVIEWS = "api/v1/reviews"
        const val MY_REVIEWS = "api/v1/reviews/my-reviews"

        // Matching
        const val RECOMMENDATIONS = "api/v1/matching/recommendations/advanced"
        const val MATCH_SCORE = "api/v1/matching/match-score/{id}"
    }

    /**
     * Request timeouts
     */
    const val CONNECT_TIMEOUT = 30L  // seconds
    const val READ_TIMEOUT = 30L     // seconds
    const val WRITE_TIMEOUT = 30L    // seconds

    /**
     * Pagination
     */
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 50
}