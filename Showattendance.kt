package com.example.student_attendance_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.student_attendance_app.Verificationlist
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Showattendance : AppCompatActivity() {

    var ref: DatabaseReference? = null
    var list: ArrayList<Attendance>? = null
    private var listener: AttenAdapter.RecyclerViewClickListener? = null

    var recyclerView: RecyclerView? = null

    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showattendance)

        ref = FirebaseDatabase.getInstance().reference.child("Attend")
        recyclerView = findViewById(R.id.recyclerview)
        searchView = findViewById(R.id.searchview)
    }

    override fun onStart() {
        super.onStart()

        if (ref != null) {
            ref!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        list = ArrayList()
                        for (ds in snapshot.children) {
                            list!!.add(ds.getValue(Attendance::class.java)!!)
                        }
                        setOnClickListner()
                        val adapter = AttenAdapter(list, listener)
                        recyclerView!!.adapter = adapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Showattendance, "error", Toast.LENGTH_SHORT).show()
                }
            })
        }


        if (searchView != null) {
            searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(s: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(s: String): Boolean {
                    search(s)
                    return true
                }
            })
        }
    }

    private fun setOnClickListner() {
        listener = AttenAdapter.RecyclerViewClickListener { v, position ->
            val intent = Intent(applicationContext, Verificationlist::class.java)
            intent.putExtra("name", list!![position].name)

            intent.putExtra("number",list!![position].number)
            intent.putExtra("shift",list!![position].shift)
            intent.putExtra("dept",list!![position].dept)
            intent.putExtra("date",list!![position].date)
            intent.putExtra("time",list!![position].time)


            startActivity(intent)
        }
    }

    private fun search(s: String) {

        try{
            val mylist = ArrayList<Attendance?>()
            for (`object` in list!!) {
                if (`object`!!.dept.toLowerCase().contains(s.toLowerCase())) {
                    mylist.add(`object`)
                }
            }
            val adapter = AttenAdapter(mylist,listener)
            recyclerView!!.adapter = adapter
        }catch (e:Exception){
            e.printStackTrace()
        }


    }
}