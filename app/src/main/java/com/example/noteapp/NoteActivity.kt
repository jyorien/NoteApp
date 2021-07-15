package com.example.noteapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.databinding.ActivityNoteBinding

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

        val adapter = NoteAdapter {
            Toast.makeText(this, it.noteTitle, Toast.LENGTH_SHORT).show()
            viewModel.deleteNote(it)
        }
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