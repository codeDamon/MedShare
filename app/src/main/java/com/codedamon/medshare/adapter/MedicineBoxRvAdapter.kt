package com.codedamon.medshare.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.model.MedicineBox

class MedicineBoxRvAdapter(val context: Context,
                           private val list: ArrayList<MedicineBox>,
                           private val mInterface: MedBoxInterface)
    : RecyclerView.Adapter<MedicineBoxRvAdapter.MedBoxViewHolder>(){

    interface MedBoxInterface{
        fun onExpandClicked();
    }

    inner class MedBoxViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val detailsLayout : LinearLayout = view.findViewById(R.id.details_layout)
        val downArrow: ImageView = view.findViewById(R.id.down_arrow)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedBoxViewHolder {
        return MedBoxViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_med_box_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MedBoxViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            mInterface.onExpandClicked()
            if(holder.detailsLayout.visibility==View.VISIBLE) {
                holder.detailsLayout.visibility = View.GONE
                holder.downArrow.rotation = 0F
            }
            else {
                holder.detailsLayout.visibility = View.VISIBLE
                holder.downArrow.rotation = 180F
            }
        }
    }

    override fun getItemCount(): Int = list.size
}