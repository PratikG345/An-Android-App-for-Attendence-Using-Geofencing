package com.example.student_attendance_app

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Geocoder
import android.media.ThumbnailUtils
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.student_attendance_app.databinding.ActivityStudentdashBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException
import java.util.Locale

class studentdash : AppCompatActivity() {

    var fusedLocationProviderClient: FusedLocationProviderClient? = null

    private lateinit var binding: ActivityStudentdashBinding
    var lat: String? = null
    var log: String? = null
    var address: String? =null

    var sharedpreferences: SharedPreferences? = null
    var firebaseDatabase: FirebaseDatabase? = null
    var mref: DatabaseReference? = null
    var firebaseStorage: FirebaseStorage? = null

    private var imageView: ImageView?=null
    var imageurl: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentdashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseDatabase = FirebaseDatabase.getInstance()
        mref = firebaseDatabase!!.getReference().child("location")
        firebaseStorage = FirebaseStorage.getInstance()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        val btncamera = binding.btncamera

        val btnlocation = binding.btnlocation
        val btnupload = binding.btnupload


        btnlocation.setOnClickListener {
            getlocation()
        }
        imageView = binding.imageView2



        btncamera.setOnClickListener {
            Toast.makeText(applicationContext,"Camera", Toast.LENGTH_LONG).show()
            val cameraintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraintent, 3)
        }

    }
    private fun getlocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
               android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationProviderClient!!.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(applicationContext, Locale.getDefault())
                try {
                    val addresses =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)


                    lat = addresses?.get(0)?.latitude.toString()
                    log = addresses?.get(0)?.longitude.toString()
                    address = addresses?.get(0)?.getAddressLine(0)



                    Toast.makeText(applicationContext, address.toString(), Toast.LENGTH_LONG).show()


                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 3) {
            var image = data!!.extras!!["data"] as Bitmap?
            val dimension = Math.min(image!!.width, image!!.height)
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
            imageView!!.setImageBitmap(image)
            onCaptureImageresult(data)

        }
    }

    private fun onCaptureImageresult(data: Intent) {


//
    }
}