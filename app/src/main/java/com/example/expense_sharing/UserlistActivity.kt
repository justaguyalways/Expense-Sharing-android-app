package com.example.expense_sharing

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UserlistActivity : AppCompatActivity(), OnUserClickListener {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var myAdapter : MyAdapter
    private lateinit var userArrayList : ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<User>()
        myAdapter = MyAdapter(userArrayList, this)
        userRecyclerview.adapter = myAdapter
        getUserData()

    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Users")

        dbref.addValueEventListener(object : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)

                    }

                    userRecyclerview.adapter = MyAdapter(userArrayList,this@UserlistActivity)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UserlistActivity, "Its a toast!", Toast.LENGTH_SHORT).show()
            }


        })

    }

    override fun OnUserItemClicked(groupId :String) {

        val myIntent = Intent(this, MemberlistActivity::class.java)
        myIntent.putExtra("groupId",groupId);
        startActivity(myIntent);

    }
}