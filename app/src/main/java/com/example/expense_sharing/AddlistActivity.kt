package com.example.expense_sharing

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expense_sharing.databinding.ActivityAddlistBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddlistBinding
    private lateinit var database : DatabaseReference
    private lateinit var database1 : DatabaseReference
    private lateinit var database2 : DatabaseReference
    private lateinit var database3 : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.makeGrpBtn.setOnClickListener {
            val groupName = binding.groupName.text.toString()
            val groupPurpose = binding.groupPurpose.text.toString()

            database = FirebaseDatabase.getInstance().getReference("Users")
            database1 = FirebaseDatabase.getInstance().getReference("Members")
            database2 = FirebaseDatabase.getInstance().getReference("History")
            val User = Useradd(groupName,groupPurpose)
            val memberUser = Member("Owner", "aspoornash2355@gmail.com")
            val memberHis = MemberHistory("Owner", "0", "Owner", groupName)
            database1.child(groupName).child("Owner").setValue(memberUser)
            database2.child(groupName).child("Owner").setValue(memberHis)
            database.child(groupName).setValue(User).addOnSuccessListener {
                binding.groupName.text.clear()
                binding.groupPurpose.text.clear()

                Toast.makeText(this, "Group Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Group Save Failed", Toast.LENGTH_SHORT).show()

            }
        }

        binding.deleteGrpBtn.setOnClickListener {
            val groupName = binding.groupName.text.toString()
            if(groupName.isNotEmpty()){
                deleteUserData(groupName)
            }else{
                Toast.makeText(this, "Enter a Group Name", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun deleteUserData(groupName: String) {
        database3 = FirebaseDatabase.getInstance().getReference("Users")
        database3.child(groupName).removeValue().addOnSuccessListener {
            binding.groupName.text.clear()
            Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
        }


    }
}


