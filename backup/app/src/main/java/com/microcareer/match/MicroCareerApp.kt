package com.microcareer.match

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MicroCareerApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channels = listOf(
                NotificationChannel(
                    "new_jobs",
                    "New Job Opportunities",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Notifications for new job matches"
                },
                NotificationChannel(
                    "skill_gaps",
                    "Skill Improvement",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "Recommendations to improve your skills"
                },
                NotificationChannel(
                    "payments",
                    "Payments",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Payment notifications"
                },
                NotificationChannel(
                    "sync",
                    "Data Sync",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "Background data synchronization"
                }
            )

            val notificationManager = getSystemService(NotificationManager::class.java)
            channels.forEach { notificationManager.createNotificationChannel(it) }
        }
    }
}