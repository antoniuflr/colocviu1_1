package ro.pub.cs.systems.eim.colocviu1_1

import android.content.Context
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sqrt

class ProcessingThread(
    private val context: Context,
    private val instructions: String,
) : Thread() {

    override fun run() {
        try {
            while (!isInterrupted) {
                Thread.sleep(Constants.INTERVAL_MS.toLong())

                if (isInterrupted) return

                val timestamp = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
                ).format(Date())

                val broadcastIntent = Intent().apply {
                    putExtra(
                        Constants.EXTRA_TIMESTAMP,
                        timestamp
                    )
                    putExtra(
                        "instructions",
                        instructions
                    )
                    setPackage(context.packageName)
                }

                context.sendBroadcast(broadcastIntent)
            }
        } catch (exception: InterruptedException) {
            currentThread().interrupt()
        }
    }
}
