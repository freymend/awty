package edu.uw.ischool.zachaz.awty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import edu.uw.ischool.zachaz.awty.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var message = binding.message.text.toString()
        var phoneNumber = binding.phoneNumber.text.toString()
        var minutes = binding.minutes.text.toString()


        fun validInformation(): Boolean {
            return !(message.isEmpty() || phoneNumber.isEmpty() || minutes.isEmpty())
        }

        binding.phoneNumber.setOnEditorActionListener { it, _, event ->
            minutes = (it as EditText).text.toString()
            event != null
        }
        binding.message.setOnEditorActionListener { it, _, event ->
            phoneNumber = (it as EditText).text.toString()
            event != null
        }
        binding.minutes.setOnEditorActionListener { it, _, event ->
            message = (it as EditText).text.toString()
            event != null
        }

        fun sendMessage() : String {
            Log.i("MainActivity", "Sending message: $message to $phoneNumber")
            return getString(R.string.stop)
        }
        fun stopMessage() : String {
            Log.i("MainActivity", "Stopping message: $message to $phoneNumber")
            return getString(R.string.start)
        }

        binding.send.setOnClickListener {
            if (validInformation()) {
                binding.send.text = when (binding.send.text) {
                    getString(R.string.start) -> sendMessage()
                    getString(R.string.stop) -> stopMessage()
                    else -> stopMessage()
                }
            } else {
                Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show()
            }
        }
    }
}