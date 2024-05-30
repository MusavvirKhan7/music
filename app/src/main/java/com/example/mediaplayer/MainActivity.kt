package com.example.mediaplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var mediaplayer : MediaPlayer
    var totaltime : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnplay =  findViewById<ImageView>(R.id.play)
        val btnpause =  findViewById<ImageView>(R.id.pause)
        val btnstop =  findViewById<ImageView>(R.id.stop)
        val btnseekbar = findViewById<SeekBar>(R.id.seekBar)

        mediaplayer = MediaPlayer.create(this, R.raw.shapeofyou)
        mediaplayer.setVolume(1f, 1f)
        totaltime = mediaplayer.duration



        btnplay.setOnClickListener{
            mediaplayer.start()
        }
        btnpause.setOnClickListener {
            mediaplayer.pause()
        }
        btnstop.setOnClickListener {
            mediaplayer?.stop()
            mediaplayer.reset()
            mediaplayer.release()
        }

        //when user changes the timestamp of the music, reflect that change

        btnseekbar.max= totaltime
        btnseekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            if (fromUser){
                mediaplayer.seekTo(progress)
               }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        // change the seeker position based on the music
        val handler = Handler()
        handler.postDelayed( object: Runnable{
            override fun run() {
                try {
                    btnseekbar.progress = mediaplayer.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (exception : java.lang.Exception){
                    btnseekbar.progress = 0
                }
            }

        },0)
    }
}