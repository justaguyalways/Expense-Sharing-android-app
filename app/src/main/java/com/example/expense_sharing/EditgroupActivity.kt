package com.example.expense_sharing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expense_sharing.databinding.ActivityEditgroupBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EditgroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditgroupBinding
    private lateinit var database : DatabaseReference
    private lateinit var database1 : DatabaseReference
    private lateinit var database2 : DatabaseReference
    private lateinit var database3 : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditgroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editGrpBtn.setOnClickListener {
            val groupName = binding.groupName.text.toString()
            val groupPurpose = binding.groupPurpose.text.toString()
            editGroupDetails(groupName, groupPurpose)
        }
    }

    private fun editGroupDetails(groupName: String, groupPurpose: String) {
        database = FirebaseDatabase.getInstance().getReference("Users")
        val group = mapOf<String,String>("groupName" to groupName, "groupPurpose" to groupPurpose)


    }
}


