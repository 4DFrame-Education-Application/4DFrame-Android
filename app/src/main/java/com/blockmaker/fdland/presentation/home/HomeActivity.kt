package com.blockmaker.fdland.presentation.home

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.practice.view.PracticePickerActivity
import com.blockmaker.fdland.presentation.signin.SignInActivity

class HomeActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 오디오 파일 재생 설정
        setupMediaPlayer()

        val button1: Button = findViewById(R.id.home_button1)
        val button2: Button = findViewById(R.id.home_button2)

        button1.setOnClickListener {
            val intent = Intent(this, PracticePickerActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        // 볼륨 설정
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val desiredVolume = maxVolume / 2
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, desiredVolume, 0)
        Log.d("HomeActivity", "Current volume set to $desiredVolume")
    }

    private fun setupMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.animation_music)
            mediaPlayer?.start()
        } catch (e: Exception) {
            Log.e("HomeActivity", "Error initializing MediaPlayer: ${e.message}")
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}