package com.codedamon.medshare.donor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.model.DonorLeader

class LeaderboardRvAdapter(val list: ArrayList<DonorLeader>)
    : RecyclerView.Adapter<LeaderboardRvAdapter.LeaderboardViewHolder>() {

    inner class LeaderboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.findViewById<TextView>(R.id.name)
        val pos = view.findViewById<TextView>(R.id.position)
        val points = view.findViewById<TextView>(R.id.points_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(R.layout.item_leaderboard_rv, parent, false)

        return LeaderboardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int) {

        holder.name.text = list[position].name
        holder.pos.text = (position+1).toString()
        holder.points.text = list[position].points.toString()

    }

    override fun getItemCount(): Int = list.size
}
