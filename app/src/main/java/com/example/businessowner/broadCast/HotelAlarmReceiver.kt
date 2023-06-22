package com.example.businessowner.broadCast

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDeepLinkBuilder
import com.example.businessowner.R
import com.example.businessowner.Ui.Insights.Signup.CongratulationsFragment
import com.example.businessowner.Ui.Insights.viewmodel.SharedViewModel
import com.example.businessowner.di.Application
import com.example.businessowner.model.Respond.Hotel.Document

class HotelAlarmReceiver:BroadcastReceiver() {
    lateinit var sharedViewModel: SharedViewModel
    private val CHANNEL_ID = "your_channel_id"
    private val NOTIFICATION_ID = 1
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val application = context.applicationContext as Application
            val viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
                .create(SharedViewModel::class.java)
            sharedViewModel = viewModel

            sharedViewModel.hotelRequestLiveDataShared.observeForever { data ->
                val document: Document = data[0]

                val status = document.status
                if (status == "active") {
                    showNotification(
                        context,
                        "Rahal Owner",
                        "Congratulations your place have been added successfully ",
                        R.drawable.signup_logo, // Replace with your small icon
                        Color.GREEN, // Replace with your desired light color
                        CongratulationsFragment::class.java,
                        "welcome"
                    )

                }
            }

        }

    }
    private fun showNotification(
        context: Context,
        title: String,
        text: String,
        smallIcon: Int,
        color: Int,
        targetActivity: Class<*>,
        fragmentName: String
    ) {
        // Create an explicit intent to open the application in WelcomeFragment
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_hostt)
            .setDestination(R.id.congratulationsFragment)
            .createPendingIntent()

        // Create the notification channel (required for API 26+)
        createNotificationChannel(context)

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(color)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notification = notificationBuilder.build()

        // Show the notification
        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        return
        }
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Channel Name",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Channel Description"
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

}
