package com.example.expense_sharing

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expense_sharing.databinding.ActivityAddmemberBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddMember : AppCompatActivity() {


    private lateinit var binding: ActivityAddmemberBinding
    private lateinit var database : DatabaseReference
    private lateinit var database1 : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addmember)

        val displayName: String? = intent.getStringExtra("groupId")


        binding = ActivityAddmemberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addMemberBtn.setOnClickListener {
            val memberName = binding.addMember.text.toString()
            val pendingDebts = binding.pendingDebts.text.toString()

            database =
                FirebaseDatabase.getInstance().getReference("Members").child(displayName.toString())
            val memberadd = Member(memberName, pendingDebts)
            database.child(memberName).setValue(memberadd).addOnSuccessListener {
                binding.addMember.text.clear()
                binding.pendingDebts.text.clear()

                Toast.makeText(this, "Member Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Member Save Failed", Toast.LENGTH_SHORT).show()

            }
        }
        binding.deleteMemberBtn.setOnClickListener {
            val memberName = binding.addMember.text.toString()
            deleteMember(memberName, displayName)
        }


    }

    private fun deleteMember(memberName: String, displayName: String?) {
        database1 = FirebaseDatabase.getInstance().getReference("Members").child(displayName.toString())
        database1.child(memberName).removeValue().addOnSuccessListener {
            binding.addMember.text.clear()
            Toast.makeText(this, "Delete Successful", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
        }
    }
}