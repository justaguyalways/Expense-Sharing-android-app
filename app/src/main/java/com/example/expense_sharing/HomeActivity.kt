package com.example.expense_sharing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dbref : DatabaseReference
    private lateinit var groupsArray: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

       // val email = intent.getStringExtra("email")
        //val displayName = intent.getStringExtra("name")
        val intent: Intent=Intent(this, MainActivity::class.java)

        //findViewById<TextView>(R.id.textView).text = email + "\n" + displayName (textview is the id of that part)
        val newGroupBtn1: Button = findViewById<Button>(R.id.addGroupButton)
       newGroupBtn1.setOnClickListener {
           startActivity(Intent(this, UserlistActivity::class.java))
       }
        val newGroupBtn2: Button = findViewById<Button>(R.id.existingGroupsButton)
        newGroupBtn2.setOnClickListener {
            startActivity(Intent(this, AddlistActivity::class.java))
        }
       // val newGroupBtn3: Button = findViewById<Button>(R.id.editGroupsBtn)
       // newGroupBtn3.setOnClickListener {
      //      getUserData()
        //    val tIntent = Intent(this, HistoryActivity::class.java)
       //     tIntent.putExtra("groups", groupsArray)
         //   startActivity(tIntent)

        //}
        auth = FirebaseAuth.getInstance()
        findViewById<Button>(R.id.signOutBtn).setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity:: class.java))
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
                Toast.makeText(this@HomeActivity, "Its a toast!", Toast.LENGTH_SHORT).show()
            }


        })
    }




}