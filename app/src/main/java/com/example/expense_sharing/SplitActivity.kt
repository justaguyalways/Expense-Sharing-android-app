package com.example.expense_sharing

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.expense_sharing.databinding.ActivitySplitBinding
import com.google.firebase.database.*

class SplitActivity  : AppCompatActivity() {

    private lateinit var binding: ActivitySplitBinding
    private lateinit var memberHistoryArray : ArrayList<MemberHistory>
    private lateinit var memberArray : ArrayList<Member>
    private lateinit var database : DatabaseReference
    private lateinit var dbref : DatabaseReference
    private lateinit var dbref1 : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_split)

        val displayName : String? = intent.getStringExtra("groupId")
        var eventInfo : String = "notinit"
        var payingMember: String
        var amount: Double = 0.00
        var date : String

        memberArray = arrayListOf<Member>()
        memberHistoryArray = arrayListOf<MemberHistory>()

        binding = ActivitySplitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addInformation.setOnClickListener {
             eventInfo = binding.reasonSplit.text.toString()
             payingMember = binding.payingMember.text.toString()
             amount = binding.amountValue.text.toString().toDoubleOrNull()!!


            getMemberData(displayName)


            initMembersHistory(payingMember, eventInfo)
        }
        binding.splitByAmt.setOnClickListener {
            eventInfo = binding.reasonSplit.text.toString()
            payingMember = binding.payingMember.text.toString()
            amount = binding.amountValue.text.toString().toDoubleOrNull()!!
            date = binding.Date.text.toString()
            var length : Int = 0

            for(mem in memberHistoryArray){
                length +=1
            }

            var payAmount : Double = amount/length

            for(mem in memberHistoryArray){
                if(mem.payeeMember != payingMember){
                    mem.share = (((payAmount*1000).toInt()).toDouble()/1000).toString()
                }else{
                    mem.share = "0"
                }
                mem.date = date
            }
            //splitByAmt(payAmount)
            dbref = FirebaseDatabase.getInstance().getReference("History")
            for (mem in memberHistoryArray){
            dbref.child(displayName.toString()).child(mem.payeeMember.toString()).setValue(mem).addOnSuccessListener {
                Toast.makeText(this, "Amount Split Successful", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Amount Split Failed", Toast.LENGTH_SHORT).show()

            }
            }
            binding.reasonSplit.text.clear()
            binding.payingMember.text.clear()
            binding.amountValue.text.clear()
            binding.Date.text.clear()

        }
        binding.addShare.setOnClickListener {
            var payeeName = binding.payeeMember.text.toString()
            var payeeShare = binding.payeeShare.text.toString()
            for(mem in memberHistoryArray){
                if(mem.payeeMember == payeeName){
                    mem.share = payeeShare
                }
            }
            binding.payeeMember.text.clear()
            binding.payeeShare.text.clear()
        }
        binding.splitByShare.setOnClickListener {
            eventInfo = binding.reasonSplit.text.toString()
            payingMember = binding.payingMember.text.toString()
            amount = binding.amountValue.text.toString().toDoubleOrNull()!!
            date = binding.Date.text.toString()

            var sum : Double = 0.00
            for(mem in memberHistoryArray){
                sum += (mem.share.toString().toInt()).toDouble()
            }
            for(mem in memberHistoryArray){
                var shareAmt : Double = (mem.share.toString().toDouble()*amount)/sum
                mem.share = (((shareAmt*1000).toInt()).toDouble()/1000).toString()
                if(mem.payeeMember == payingMember){
                    mem.share = "0"
                }
                mem.date = date
            }
            dbref1 = FirebaseDatabase.getInstance().getReference("History")
            for (mem in memberHistoryArray){
                dbref1.child(displayName.toString()).child(mem.payeeMember.toString()).setValue(mem).addOnSuccessListener {
                    Toast.makeText(this, "Amount Split Successful", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Amount Split Failed", Toast.LENGTH_SHORT).show()
                }
            }
            binding.reasonSplit.text.clear()
            binding.payingMember.text.clear()
            binding.amountValue.text.clear()
            binding.Date.text.clear()
        }
    }

    //private fun splitByAmt(payAmount: Double) {
      //  for (memHistory in memberHistoryArray){
    //        if(memHistory.payingMember != memHistory.payeeMember){
      //          memHistory.share = (payAmount).toString()
    //        }
     //   }

   // }

    private fun initMembersHistory(payingMember: String, eventInfo: String) {
        for (member in memberArray){
            if(member.memberName != payingMember){
                val memberHis = MemberHistory(member.memberName, "1", payingMember, eventInfo)
                memberHistoryArray.add(memberHis)
            }else{
                val memberHis = MemberHistory(member.memberName, "0", payingMember, eventInfo)
                memberHistoryArray.add(memberHis)
            }
        }

    }


    private fun getMemberData(displayName: String?) {
        database = FirebaseDatabase.getInstance().getReference("Members")

        if (displayName != null) {
            database.child(displayName).addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()){

                        for (userSnapshot in snapshot.children){


                            val member = userSnapshot.getValue(Member::class.java)


                            memberArray.add(member!!)

                        }

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@SplitActivity, "Its a toast!", Toast.LENGTH_SHORT).show()
                }


            })
        }


    }
}