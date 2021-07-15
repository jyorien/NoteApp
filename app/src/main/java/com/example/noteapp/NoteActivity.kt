package com.example.noteapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.databinding.ActivityNoteBinding

const val NOTIF_CHANNEL_ID = "CHANNEL 1"

class NoteActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityNoteBinding
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val adapter = NoteAdapter(
            {
                // checkbox onclick
                Toast.makeText(this, it.noteTitle, Toast.LENGTH_SHORT).show()
                viewModel.deleteNote(it)
            },
            {
                // bell onclick
                sendNotification(it.noteTitle, it.noteDesc)

            }
        )
        binding.noteList.adapter = adapter
        viewModel.noteList.observe(this, {
            (binding.noteList.adapter as NoteAdapter).submitList(it)
        })

        binding.noteAddBtn.setOnClickListener {
            val title = binding.noteTitle.text.toString().trim()
            val desc = binding.noteDesc.text.toString().trim()
            viewModel.addNote(title, desc)
        }

    }

    private fun sendNotification(title: String, desc: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIF_CHANNEL_ID,
                NOTIF_CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val intent = Intent(this, NoteActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
             NotificationManagerCompat.from(this).createNotificationChannel(channel)
            val builder = NotificationCompat.Builder(this, NOTIF_CHANNEL_ID)
                .setChannelId(NOTIF_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(R.drawable.ic_baseline_favorite_border_24)
                .setContentIntent(pendingIntent)
            with(NotificationManagerCompat.from(this)) {
                notify(100,builder.build())
            }

        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { eve ->
            Log.i("hello", eve.values[0].toString())

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}