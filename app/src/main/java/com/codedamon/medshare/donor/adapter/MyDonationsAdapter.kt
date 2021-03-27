package com.codedamon.medshare.donor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.model.MyDonation

class MyDonationsAdapter(val list: ArrayList<MyDonation>)
    :RecyclerView.Adapter<MyDonationsAdapter.MyDonationsViewHolder>(){

    inner class MyDonationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.findViewById<TextView>(R.id.chemist_name)
        val date = view.findViewById<TextView>(R.id.date)
        val heartCount = view.findViewById<TextView>(R.id.hearts_count)
        val pointCount = view.findViewById<TextView>(R.id.points_count)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDonationsViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_donations_rv, parent, false)
        return MyDonationsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyDonationsViewHolder, position: Int) {

        holder.name.text = list[position].toName
        holder.date.text = list[position].date
        holder.heartCount.text = list[position].hearts
        holder.pointCount.text = list[position].points

    }

    override fun getItemCount() = list.size
}