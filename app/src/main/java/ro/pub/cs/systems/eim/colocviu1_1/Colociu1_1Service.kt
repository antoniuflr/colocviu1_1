package ro.pub.cs.systems.eim.colocviu1_1

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder

class Colociu1_1Service : Service() {

    private var processingThread: ProcessingThread? = null

    companion object {
        private const val CHANNEL_ID = "11"
        private const val CHANNEL_NAME = "ForegroundServiceChannel"
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        dummyNotification()

        val instructions = intent?.getStringExtra("instructions")

        if (processingThread?.isAlive != true) {
            processingThread = ProcessingThread(
                applicationContext.instructions
            )

            processingThread?.start()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        processingThread?.interrupt()
        processingThread = null

        super.onDestroy()
    }

    private fun dummyNotification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            manager.createNotificationChannel(channel)
        }

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(applicationContext, CHANNEL_ID)
        } else {
            @Suppress("DEPRECATION")
            Notification.Builder(applicationContext)
        }

        val notification = builder
            .setContentTitle("Foreground Service")
            .setContentText("Service is running...")
            .setSmallIcon(R.drawable.ic_media_play)
            .build()

        startForeground(1, notification)
    }
}