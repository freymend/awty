package edu.uw.ischool.zachaz.awty

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import edu.uw.ischool.zachaz.awty.databinding.ActivityMainBinding

const val ALARM_ACTION = "edu.uw.ischool.zachaz.awty.ALARM"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var message = binding.message.text.toString()
        var phoneNumber = binding.phoneNumber.text.toString()
        var minutes = binding.minutes.text.toString()


        binding.phoneNumber.setOnEditorActionListener { it, _, event ->
            phoneNumber = (it as EditText).text.toString()
            event != null
        }
        binding.message.setOnEditorActionListener { it, _, event ->
            message = (it as EditText).text.toString()
            event != null
        }
        binding.minutes.setOnEditorActionListener { it, _, event ->
            minutes = (it as EditText).text.toString()
            event != null
        }

        val activityThis = this

        fun sendMessage(): String {
            if (receiver == null) {
                receiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        Toast.makeText(activityThis, message, Toast.LENGTH_SHORT).show()
                    }
                }

                val filter = IntentFilter(ALARM_ACTION)
                registerReceiver(receiver, filter)
            }


            val intent = Intent(ALARM_ACTION)
            val pendingIntent =
                PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(),
                minutes.toLong() * 60000,
                pendingIntent
            )

            return getString(R.string.stop)
        }

        fun stopMessage(): String {
            if (receiver != null) {
                unregisterReceiver(receiver)
                receiver = null
            }
            return getString(R.string.start)
        }

        fun validInformation(): Boolean {
            return !(message.isEmpty() || phoneNumber.isEmpty() || minutes.isEmpty())
        }

        binding.send.setOnClickListener {
            if (validInformation()) {
                binding.send.text = when (binding.send.text) {
                    getString(R.string.start) -> sendMessage()
                    getString(R.string.stop) -> stopMessage()
                    else -> stopMessage()
                }
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
