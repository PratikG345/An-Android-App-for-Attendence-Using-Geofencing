package com.example.student_attendance_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference

class Verificationlist : AppCompatActivity() {

    var name: String? = null
    var material: String? = null
    var manifacute: String? = null
    var origin: String? = null
    var weight: String? = null
    var rating: String? = null
    var demi: String? = null
    var location: String? = null

    var ref: DatabaseReference? = null
    var username: String? = null
    var usermobile: String? = null
    var useremail: String? = null
    var useraddress: String? = null
    var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificationlist)

        val txtproname = findViewById<TextView>(R.id.txtname)

        val txtaddress = findViewById<TextView>(R.id.txtmanifacture)

        val txtarea = findViewById<TextView>(R.id.txtorigin)
        val txtwight = findViewById<TextView>(R.id.txtweigth)
        val txtrating = findViewById<TextView>(R.id.txtrating)
        val txtdemi = findViewById<TextView>(R.id.txtdime)




        val bundle = intent.extras

        name = bundle?.getString("name")

        manifacute = bundle?.getString("number")
        origin = bundle?.getString("year")
        weight = bundle?.getString("dept")
        rating = bundle?.getString("date")
        demi = bundle?.getString("time")






        txtproname.setText("Name:" + name)

        txtaddress.setText("Number: " + manifacute)
        txtarea.setText("Year: " + origin)
        txtwight.setText("Department: " + weight)
        txtrating.setText("Date: " + rating)
        txtdemi.setText("Time: " + demi)

    }
}