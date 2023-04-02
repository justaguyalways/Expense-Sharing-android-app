package com.example.expense_sharing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList: ArrayList<User>, private val onUserClickListener: UserlistActivity) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
            parent,false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentitem = userList[position]

        holder.groupName.text = currentitem.groupName.toString();
        holder.groupPurpose.text = currentitem.groupPurpose.toString()

        holder.itemView.setOnClickListener {
            onUserClickListener.OnUserItemClicked(currentitem.groupName.toString())
        }
    }

    override fun getItemCount(): Int {

        return userList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val groupName : TextView = itemView.findViewById(R.id.tvgroupName)
        val groupPurpose: TextView = itemView.findViewById(R.id.tvgroupPurpose)

    }

}