package com.example.student_attendance_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

class Teacherdash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacherdash)
        val bottom = findViewById<BottomNavigationView>(R.id.bottom)
//
        bottom.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {


                R.id.shop ->
                {
                    //Toast.makeText(applicationContext,"Recipe",Toast.LENGTH_LONG).show()

                    val i = Intent(applicationContext,Showlocation::class.java)
                    startActivity(i)
                    true

                }



                R.id.showre ->
                {

                    Toast.makeText(applicationContext," Show Attendance",Toast.LENGTH_LONG).show()
                    val i = Intent(applicationContext,Showattendance::class.java)
                    startActivity(i)
                    true
                }
//
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