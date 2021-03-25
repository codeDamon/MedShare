package com.codedamon.medshare.donor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.model.DonorLeader

class LeaderboardRvAdapter(val list: ArrayList<DonorLeader>)
    : RecyclerView.Adapter<LeaderboardRvAdapter.LeaderboardViewHolder>() {

    inner class LeaderboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard_rv, parent, false)
        return LeaderboardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = list.size
}
