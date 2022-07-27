package com.example.okhttpkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AccountAdapter (val mList : List<MainActivity.Account>) :
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val txtAccountNo : TextView = itemView.findViewById(R.id.account)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_account,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemAcc = mList[position]
        holder.txtAccountNo.text = itemAcc.accountNo + "-" +itemAcc.currentBalance + " " + itemAcc.currency

    }

    override fun getItemCount(): Int {
        return mList.size
    }
}