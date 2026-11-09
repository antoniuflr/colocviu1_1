package ro.pub.cs.systems.eim.practicaltest01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Constants.ACTION_ONE,
            Constants.ACTION_TWO,
            Constants.ACTION_THREE -> {
                val timestamp = intent.getStringExtra(
                    Constants.EXTRA_TIMESTAMP
                )

                val arithmetic = intent.getDoubleExtra(
                    Constants.EXTRA_ARITHMETIC,
                    0.0
                )

                val geometric = intent.getDoubleExtra(
                    Constants.EXTRA_GEOMETRIC,
                    0.0
                )

                Log.d(
                    Constants.TAG,
                    "action=${intent.action}, " +
                            "time=$timestamp, " +
                            "arithmetic=$arithmetic, " +
                            "geometric=$geometric"
                )
            }
        }
    }
}