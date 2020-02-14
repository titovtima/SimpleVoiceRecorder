package com.example.simplevoicerecorder

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaRecorder
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    private var filename: String = ""
    private var recorder: MediaRecorder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO,
                 android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions,0)
        }

        start.setOnClickListener {
            startRecord()
        }

        stop.setOnClickListener {
            endRecord()
        }
    }

    fun startRecord() {
        val outFile = File(filename)
        if (outFile.exists()) {
            outFile.delete()
        }

        filename = Environment.getExternalStorageDirectory().absolutePath + "/recording.3gpp"

        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        recorder!!.setOutputFile(filename)
        recorder!!.prepare()
        recorder!!.start()

        Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show()
    }

    fun endRecord() {
        recorder!!.stop()
        recorder!!.reset()
        recorder!!.release()
        recorder = null

        Toast.makeText(this, "Record is saved", Toast.LENGTH_SHORT).show()
    }
}
