package ro.pub.cs.systems.eim.colocviu1_1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
class Colocviu1_1SecondaryActivity : ComponentActivity() {
    private var listener = Listener()
    companion object {
        const val RESULT_REGISTER = 0
        const val RESULT_CANCEL = 1

        const val REQUEST_SECONDARY = 2

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_colocviu1_1_secondary)

        findViewById<Button>(R.id.button_register).setOnClickListener(listener)
        findViewById<Button>(R.id.button_cancel).setOnClickListener(listener)

        val instructions: String? = intent.getStringExtra("instructions")
        if (instructions != null) {
            findViewById<TextView>(R.id.text_instructions).text = instructions
        }
    }

    private inner class Listener : View.OnClickListener {
        override fun onClick(view: View) {
            when (view.id) {
                R.id.button_register -> {
                    setResult(RESULT_REGISTER)
                    finish()
                }
                R.id.button_cancel -> {
                    setResult(RESULT_CANCEL)
                    finish()
                }
            }
        }
    }
}