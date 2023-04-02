
package com.example.expense_sharing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expense_sharing.databinding.ActivityAddlistBinding
import com.example.expense_sharing.databinding.ActivityMemberlistBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MemberlistActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var dbref1 : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var memeberAdapter : MemberAdapter
    private lateinit var memberArrayList : ArrayList<Member>
    private lateinit var memberArrayList1 : ArrayList<MemberHistory>
    private lateinit var binding: ActivityMemberlistBinding
    private lateinit var database : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memberlist)

        val displayName : String? = intent.getStringExtra("groupId")

        userRecyclerview = findViewById(R.id.memberList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        memberArrayList = arrayListOf<Member>()
        memberArrayList1 = arrayListOf<MemberHistory>()
        memeberAdapter = MemberAdapter(memberArrayList)
        userRecyclerview.adapter = memeberAdapter

       if (displayName != null) {
            getUserData(displayName)
       }
        val newMember: Button = findViewById<Button>(R.id.makeMemberBtn)
        newMember.setOnClickListener {
            val myIntent = Intent(this, AddMember::class.java)
            myIntent.putExtra("groupId",displayName);
            startActivity(myIntent);
        }
        val split : Button = findViewById<Button>(R.id.splitBtn)
        split.setOnClickListener {
            val splitIntent = Intent(this, SplitActivity::class.java)
            splitIntent.putExtra("groupId", displayName)
            startActivity(splitIntent)
        }
        val history : Button = findViewById<Button>(R.id.HistoryBtn)
        history.setOnClickListener {
            if (displayName != null) {
                getgroupHistory(displayName)
            }
            val btn : TextView = findViewById<TextView>(R.id.HistoryView)
            var  s : String = "Transaction Details -\n Date- "
            var t: String = ""
            var pm: String = ""
            var ed : String = ""
            for(i in memberArrayList1){
                t = i.date.toString()
                pm = i.payingMember.toString()
                ed = i.eventName.toString()
            }
            s += t
            s += "\n Event Details- "
            s += ed
            s +="\n"
            for(i in memberArrayList1){
                if(i.share != "0"){
                s += i.payeeMember
                s += " pays "
                s += i.payingMember
                s += " Rs "
                s += i.share
                s += "\n"
                }
            }
            btn.text = s
        }
    }
    private fun getUserData(displayName : String){

        dbref = FirebaseDatabase.getInstance().getReference("Members")

        dbref.child(displayName).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val member = userSnapshot.getValue(Member::class.java)

                        memberArrayList.add(member!!)

                    }

                    userRecyclerview.adapter = MemberAdapter(memberArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MemberlistActivity, "Its a toast!", Toast.LENGTH_SHORT).show()
            }


        })
    }
    private fun getgroupHistory(displayName : String){

        database = FirebaseDatabase.getInstance().getReference("History")

        database.child(displayName).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){

                    for (userSnapshot in snapshot.children){


                        val member = userSnapshot.getValue(MemberHistory::class.java)

                        memberArrayList1.add(member!!)

                    }


                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MemberlistActivity, "Its a toast!", Toast.LENGTH_SHORT).show()
            }


        })
    }



}