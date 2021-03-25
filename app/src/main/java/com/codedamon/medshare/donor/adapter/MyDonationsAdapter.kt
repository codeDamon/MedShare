package com.codedamon.medshare.donor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.model.MyDonation

class MyDonationsAdapter(val list: ArrayList<MyDonation>)
    :RecyclerView.Adapter<MyDonationsAdapter.MyDonationsViewHolder>(){

    inner class MyDonationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyDonationsViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(R.layout.item_my_donations_rv, parent, false)
        return MyDonationsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyDonationsViewHolder, position: Int) {
    }

    override fun getItemCount() = list.size
}