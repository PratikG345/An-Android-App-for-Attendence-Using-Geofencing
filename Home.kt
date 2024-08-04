package com.example.student_attendance_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.student_attendance_app.Dashboard
import com.example.student_attendance_app.databinding.ActivityHomeBinding
import com.example.student_attendance_app.studentdash


class Home : AppCompatActivity() {


    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val img1 = binding.client
        val img2 = binding.owner

        img1.setOnClickListener {
            val intent = Intent(applicationContext, Dashboard::class.java)
            startActivity(intent)
        }
        img2.setOnClickListener {
            val intent = Intent(applicationContext, Teacherdash::class.java)
            startActivity(intent)
        }

    }
}