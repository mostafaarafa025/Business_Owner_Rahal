package com.example.businessowner

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkBuilder
import com.example.businessowner.Ui.Insights.viewmodel.RequestViewModel
import com.example.businessowner.broadCast.HotelAlarmReceiver
import com.example.businessowner.databinding.FragmentLoadingOwnerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadingOwnerFragment : Fragment() {
    private val requestViewModel: RequestViewModel by viewModels()
    private  val CHANNEL_ID = "my_channel"
    private  val NOTIFICATION_ID = 123
    private var statusRes: String = ""
    private  val ACTION_OPEN_INSIGHTS = "com.example.businessowner.OPEN_INSIGHTS"

    private var statushotel: String = ""
    lateinit var binding: FragmentLoadingOwnerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingOwnerBinding.inflate(inflater, container, false)
        return binding.root
        // Register the BroadcastReceiver to receive hourly broadcasts
        val broadcastReceiver = HotelAlarmReceiver()
        val filter = IntentFilter()
        filter.addAction("com.example.businessowner.ACTION_HOUR_NOTIFICATION")
        requireContext().registerReceiver(broadcastReceiver, filter)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        CoroutineScope(Dispatchers.Main).launch {
//            delay(2000)

//        }
     //   getRestaurantResponse()
        binding.next.setOnClickListener {
          showNotification()
        }

//        CoroutineScope(Dispatchers.Main).launch {
//            delay(5000)
//            val intent=Intent(requireContext(),InsightsActivity::class.java)
//            startActivity(intent)
//        }
    }

//    private fun getRestaurantResponse(){
//        requestViewModel.getRestaurantResponse()
//        requestViewModel.getRestaurantResponseLiveData.observe(viewLifecycleOwner){ it->
//            val restaurant : Restaurant=it[0]
//            val createdBy=restaurant.createdBy
//            Log.d("LoadingFragment", "Restaurant Response: $it")
//            Log.e("createdBY",createdBy.toString())
//
//        }
//    }


    private fun scheduleAlarm() {

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(requireContext(), HotelAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set the interval to 1 hour
        val intervalMillis = 60 * 60 * 1000

        // Set the initial trigger time to the next hour
        val currentTime = System.currentTimeMillis()
        val nextHour = currentTime + intervalMillis - (currentTime % intervalMillis)
        val triggerTimeMillis = nextHour

        // Set the alarm to repeat every 1 hour
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTimeMillis,
            intervalMillis.toLong(),
            pendingIntent
        )
    }
    override fun onStart() {
        super.onStart()
        scheduleAlarm()
    }
    @SuppressLint("ResourceAsColor")
    private fun showNotification() {
        val context = requireContext()

        // Create a PendingIntent for the notification click
        val pendingIntent = NavDeepLinkBuilder(context)
            .setGraph(R.navigation.nav_hostt) // Specify the other NavGraph
            .setDestination(R.id.congratulationsFragment) // Specify the other fragment
            .createPendingIntent()

        // Create the notification channel (required for API 26+)
        createNotificationChannel(context)

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.signup_logo) // Replace with your small icon
            .setContentTitle("congratulations")
            .setContentText("Your place has been added successfully ,Check it now")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(R.color.teal_200) // Replace with your desired light color
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
                var description = "Channel Description"
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}



