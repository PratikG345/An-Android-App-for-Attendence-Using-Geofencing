package com.example.student_attendance_app

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.student_attendance_app.PhoneAuth
import com.google.android.material.bottomnavigation.BottomNavigationView

class Dashboard : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val bottom = findViewById<BottomNavigationView>(R.id.bottom)

        bottom.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {




                R.id.shop ->
                {
                    //Toast.makeText(applicationContext,"Recipe",Toast.LENGTH_LONG).show()

                    val i = Intent(applicationContext,Ourlocation::class.java)
                    startActivity(i)
                    true

                }


                R.id.feedback ->
                {

                    Toast.makeText(applicationContext,"fingerprint", Toast.LENGTH_LONG).show()
                    val i = Intent(applicationContext,Biofingureprintverification::class.java)
                    startActivity(i)
                    true
                }
                R.id.showre ->
                {

                    //Toast.makeText(applicationContext,"Upload Video",Toast.LENGTH_LONG).show()
                    val i = Intent(applicationContext, PhoneAuth::class.java)
                    startActivity(i)
                    true
                }

                R.id.profile ->
                {

                    Toast.makeText(applicationContext,"logout",Toast.LENGTH_LONG).show()
                    val i = Intent(applicationContext,MainActivity::class.java)
                    startActivity(i)
                    true
                }


                else -> {false}
            }
        }
    }
}