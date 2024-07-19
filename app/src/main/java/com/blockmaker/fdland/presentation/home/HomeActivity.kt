package com.blockmaker.fdland.presentation.home

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.blockmaker.fdland.R
import com.blockmaker.fdland.presentation.main.MainActivity
import com.blockmaker.fdland.presentation.practice.view.PracticePickerActivity

class HomeActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val button1: Button = findViewById(R.id.home_button1)
        val button2: Button = findViewById(R.id.home_button2)

        // 연습해보기 버튼 클릭 이벤트 처리
        button1.setOnClickListener {
            val intent = Intent(this, PracticePickerActivity::class.java)
            startActivity(intent)
        }

        // 시작해보기 버튼 클릭 이벤트 처리
        button2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val lowerVolume = maxVolume / 10
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, lowerVolume, 0)

        mediaPlayer = MediaPlayer.create(this, R.raw.animation_music)
        mediaPlayer?.start()
    }

    override fun onPause() { // 음악 일시정지: 액티비티가 일시정지될 때 음악 일시정지
        super.onPause()
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
    }

    override fun onResume() { // 음악 다시 재생: 액티비티 재활성화 시 음악 재생
        super.onResume()
        if (mediaPlayer != null && !mediaPlayer!!.isPlaying) {
            mediaPlayer?.start()
        }
    }

    override fun onDestroy() { // MediaPlayer 해제: 액티비티 파괴 시 mediaPlayer 해제(메모리 누수 방지)
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
