package com.example.student_attendance_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Showlocation : AppCompatActivity() {

    var ref: DatabaseReference? = null
    var list: ArrayList<Profile>? = null
    private var listener: MyAdapter.RecyclerViewClickListener? = null

    var recyclerView: RecyclerView? = null

    var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showlocation)

        ref = FirebaseDatabase.getInstance().reference.child("EmployeeInfo")
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
                            list!!.add(ds.getValue(Profile::class.java)!!)
                        }
                        setOnClickListner()
                        val adapter = MyAdapter(list, listener)
                        recyclerView!!.adapter = adapter
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Showlocation, "error", Toast.LENGTH_SHORT).show()
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
        listener = MyAdapter.RecyclerViewClickListener { v, position ->
            val intent = Intent(applicationContext, Prodetails::class.java)
            intent.putExtra("name", list!![position].name)
            intent.putExtra("address",list!![position].address)
            intent.putExtra("number",list!![position].number)
            intent.putExtra("shift",list!![position].shift)
            intent.putExtra("dept",list!![position].dept)
            intent.putExtra("date",list!![position].date)
            intent.putExtra("time",list!![position].time)
            intent.putExtra("location",list!![position].location)
            intent.putExtra("url",list!![position].imageurl)


            startActivity(intent)
        }
    }

    private fun search(s: String) {
        try{
            val mylist = ArrayList<Profile?>()
            for (`object` in list!!) {
                if (`object`!!.dept.toLowerCase().contains(s.toLowerCase())) {
                    mylist.add(`object`)
                }
            }
            val adapter = MyAdapter(mylist,listener)
            recyclerView!!.adapter = adapter
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

}