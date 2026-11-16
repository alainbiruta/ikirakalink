package com.microcareer.match.presentation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.microcareer.match.data.fcm.MicroCareerMessagingService
import com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion.EXTRA_APPLICATION_ID
import com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion.EXTRA_JOB_ID
import com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion.EXTRA_MODULE_ID
import com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion.EXTRA_NOTIFICATION_TYPE
import com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion.TYPE_APPLICATION_ACCEPTED
import com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion.TYPE_APPLICATION_REJECTED
import com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion.TYPE_JOB_MATCHED
import com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion.TYPE_PAYMENT
import com.microcareer.match.data.fcm.MicroCareerMessagingService.Companion.TYPE_SKILL_GAP
import com.microcareer.match.data.sync.SyncManager
import com.microcareer.match.presentation.navigation.AppNavigation
import com.microcareer.match.presentation.navigation.NotificationDestination
import com.microcareer.match.presentation.theme.MicroCareerTheme
import com.microcareer.match.utils.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var syncManager: SyncManager
    private val _notificationDestination = mutableStateOf<String?>(null)
    val notificationDestination: State<String?> = _notificationDestination

    // ── Locale ────────────────────────────────────────────────────────────────
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.applyLocale(base))
    }

    // ── Lifecycle ─────────────────────────────────────────────────────────────
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("DeviceCheck", "FINGERPRINT: ${Build.FINGERPRINT}")
        Log.d("DeviceCheck", "MODEL: ${Build.MODEL}")
        Log.d("DeviceCheck", "MANUFACTURER: ${Build.MANUFACTURER}")
        Log.d("DeviceCheck", "BRAND: ${Build.BRAND}")
        Log.d("DeviceCheck", "DEVICE: ${Build.DEVICE}")

        installSplashScreen()

        syncManager = SyncManager(this)
        syncManager.schedulePeriodicSync()

        val notificationDestination = resolveNotificationDestination(intent)
        handleNotificationIntent(intent)

        setContent {
            MicroCareerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        notificationDestination = notificationDestination
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleNotificationIntent(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::syncManager.isInitialized) syncManager.cancelSync()
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private fun handleNotificationIntent(intent: Intent?) {
        val type          = intent?.getStringExtra(EXTRA_NOTIFICATION_TYPE) ?: return
        val jobId         = intent.getStringExtra(EXTRA_JOB_ID)

        val destination = when (type) {
            TYPE_JOB_MATCHED                              -> if (jobId != null) "job_detail/$jobId" else "home"
            TYPE_APPLICATION_ACCEPTED, TYPE_APPLICATION_REJECTED -> "saved_and_applications"
            TYPE_PAYMENT                                  -> "payments"
            TYPE_SKILL_GAP                                -> "learning"
            else                                          -> null
        }
        if (destination != null) _notificationDestination.value = destination
    }

    private fun resolveNotificationDestination(intent: Intent?): NotificationDestination? {
        val type     = intent?.getStringExtra(MicroCareerMessagingService.EXTRA_NOTIFICATION_TYPE) ?: return null
        val jobId    = intent.getStringExtra(MicroCareerMessagingService.EXTRA_JOB_ID)
        val moduleId = intent.getStringExtra(MicroCareerMessagingService.EXTRA_MODULE_ID)

        return when (type) {
            MicroCareerMessagingService.TYPE_JOB_MATCHED -> {
                if (jobId != null) NotificationDestination.JobDetail(jobId)
                else NotificationDestination.EmployeeHome
            }
            MicroCareerMessagingService.TYPE_APPLICATION_ACCEPTED,
            MicroCareerMessagingService.TYPE_APPLICATION_REJECTED -> NotificationDestination.SavedJobs
            MicroCareerMessagingService.TYPE_PAYMENT              -> NotificationDestination.EmployeeHome
            MicroCareerMessagingService.TYPE_SKILL_GAP -> {
                if (moduleId != null) NotificationDestination.LearningDetail(moduleId)
                else NotificationDestination.Learning
            }
            else -> null
        }
    }

    fun clearNotificationDestination() {
        _notificationDestination.value = null
    }
}