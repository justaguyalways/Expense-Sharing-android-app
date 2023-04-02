package com.example.expense_sharing

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expense_sharing.databinding.ActivityHistoryBinding
import com.google.firebase.database.*


class HistoryActivity : AppCompatActivity() {



    private lateinit var dbref: DatabaseReference
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var groupsArray: ArrayList<User>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val items = listOf("hi", "bye", "gn", "gm")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.dropdownField.setAdapter(adapter)
        binding.eventConfirm.setOnClickListener {
            val test = binding.dropdownField.text.toString()
            if (test == "hi") {
                binding.TestField.text = "test hi" + "\n" + "bye"
            } else {
                binding.TestField.text = null
            }
        }


    }
    private fun getUserData(){

        dbref = FirebaseDatabase.getInstance().getReference("Users")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val member = userSnapshot.getValue(User::class.java)

                        groupsArray.add(member!!)

                    }


                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HistoryActivity, "Its a toast!", Toast.LENGTH_SHORT).show()
            }


        })
    }


}

