package edu.uw.ischool.zachaz.awty

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import edu.uw.ischool.zachaz.awty.databinding.ActivityMainBinding

const val ALARM_ACTION = "edu.uw.ischool.zachaz.awty.ALARM"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.SEND_SMS
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED -> { }
            shouldShowRequestPermissionRationale(android.Manifest.permission.SEND_SMS) -> { }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.SEND_SMS),
                    1
                )
            }
        }

        var message = binding.message.text.toString()
        var phoneNumber = binding.phoneNumber.text.toString()
        var minutes = binding.minutes.text.toString()


        binding.phoneNumber.doOnTextChanged { text, _, _, _ -> phoneNumber = text.toString() }
        binding.message.doOnTextChanged { text, _, _, _ -> message = text.toString() }
        binding.minutes.doOnTextChanged { text, _, _, _ -> minutes = text.toString() }

        val smsManager = SmsManager.getDefault()

        fun sendMessage(): String {
            if (receiver == null) {
                receiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        smsManager.sendTextMessage(
                            phoneNumber,
                            null,
                            message,
                            null,
                            null
                        )
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
                Toast.makeText(
                    this,
                    R.string.please_fill_all_the_fields, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
