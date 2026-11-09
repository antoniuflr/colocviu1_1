package ro.pub.cs.systems.eim.colocviu1_1

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.savedstate.serialization.saved
import ro.pub.cs.systems.eim.colocviu1_1.ui.theme.Colocviu1_1Theme
import kotlin.toString

class Colocviu1_MainActivity : ComponentActivity() {

    private var num_presses = 0
    var listener = Listener()

    var serviceStarted: Boolean = false

    companion object {
        const val TAG = "colocviu_debug"
        const val RESULT_REGISTER = 0
        const val RESULT_CANCEL = 1
        const val REQUEST_SECONDARY = 2

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colocviu1_1_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.button_north).setOnClickListener(listener)
        findViewById<Button>(R.id.button_east).setOnClickListener(listener)
        findViewById<Button>(R.id.button_west).setOnClickListener(listener)
        findViewById<Button>(R.id.button_south).setOnClickListener(listener)
        findViewById<Button>(R.id.button_navigate).setOnClickListener(listener)

        if (savedInstanceState != null) {
            num_presses = savedInstanceState.getInt("number_of_presses")
            Log.d(TAG, "Am restaurat starea num_presses = " + num_presses)
        }

    }

    inner class Listener : View.OnClickListener {
        override fun onClick(view: View) {
            when (view.id) {
                R.id.button_north -> {
                    var text_view_cardinals = findViewById<TextView>(R.id.text_cardinals)
                    var button_text = findViewById<Button>(R.id.button_north).text.toString()
                    text_view_cardinals.append(", " + button_text)
                }
                R.id.button_east -> {
                    var text_view_cardinals = findViewById<TextView>(R.id.text_cardinals)
                    var button_text = findViewById<Button>(R.id.button_east).text.toString()
                    text_view_cardinals.append(", " + button_text)
                }
                R.id.button_west -> {
                    var text_view_cardinals = findViewById<TextView>(R.id.text_cardinals)
                    var button_text = findViewById<Button>(R.id.button_west).text.toString()
                    text_view_cardinals.append(", " + button_text)
                }
                R.id.button_south -> {
                    var text_view_cardinals = findViewById<TextView>(R.id.text_cardinals)
                    var button_text = findViewById<Button>(R.id.button_south).text.toString()
                    text_view_cardinals.append(", " + button_text)
                }
                R.id.button_navigate -> {
                    val intent = Intent(
                        this@Colocviu1_MainActivity,
                        Colocviu1_1SecondaryActivity::class.java
                    )
                    var instructions: String = findViewById<TextView>(R.id.text_cardinals).text.toString()

                    intent.putExtra("instructions", instructions)

                    num_presses = 0
                    findViewById<TextView>(R.id.text_cardinals).setText("")

                    startActivityForResult(intent, REQUEST_SECONDARY)
                }
            }
            num_presses += 1
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("number_of_presses", num_presses)
        Log.d(TAG, "Am salvat starea num_presses = " + num_presses)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SECONDARY) {
            val message = if (resultCode == RESULT_REGISTER) {
                "Am inregistrat instrucitunile"
            } else {
                "Operatie anulata"
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startServiceIfNeeded() {
        val instructions = findViewById<TextView>(R.id.text_instructions)
            .text.toString()

        if (!serviceStarted &&
            num_presses > 3
        ) {
            val serviceIntent = Intent(
                this,
                Colociu1_1Service::class.java
            ).apply {
                putExtra("instruction", instructions)
            }

            ContextCompat.startForegroundService(this, serviceIntent)
            serviceStarted = true
        }
    }
}
