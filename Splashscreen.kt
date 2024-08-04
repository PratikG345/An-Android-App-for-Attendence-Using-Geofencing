package com.example.student_attendance_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.student_attendance_app.databinding.ActivitySplashscreenBinding

class Splashscreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val img = binding.imageView



        img.alpha = 0f
        img.animate().setDuration(1500).alpha(1f).withEndAction{
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)

            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()

        }
    }
}