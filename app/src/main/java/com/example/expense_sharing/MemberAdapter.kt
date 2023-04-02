package com.example.expense_sharing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(private val memberList: ArrayList<Member>) : RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.member_item,
            parent,false)
        return MemberViewHolder(itemView)

    }
    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {

            val currentitem = memberList[position]

            holder.memberName.text = currentitem.memberName.toString();
            holder.pendingDebts.text = currentitem.pendingDebts.toString()

    }

    override fun getItemCount(): Int {

        return memberList.size
    }


    class MemberViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val memberName : TextView = itemView.findViewById(R.id.tvmemberName)
        val pendingDebts: TextView = itemView.findViewById(R.id.tvpendingDebts)

    }


}