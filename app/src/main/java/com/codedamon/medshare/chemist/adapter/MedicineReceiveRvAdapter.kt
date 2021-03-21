package com.codedamon.medshare.chemist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.model.medicine.Medicine

class MedicineReceiveRvAdapter(val context: Context,
                               private val allMedicine: ArrayList<Medicine>,
                               private val mInterface: MedReceiveBoxInterface
                               ) : RecyclerView.Adapter<MedicineReceiveRvAdapter.MedReceiveViewHolder>(){

    interface MedReceiveBoxInterface{
        fun onExpandClicked();
    }


    inner class MedReceiveViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val detailsLayout : LinearLayout = view.findViewById(R.id.details_layout)
        val downArrow: ImageView = view.findViewById(R.id.down_arrow)
        val price: TextView =itemView.findViewById(R.id.tv_perMedCost)
        val quantity: TextView =itemView.findViewById(R.id.tv_quantity)
        val name: TextView =itemView.findViewById(R.id.med_name_tv)
        val expiryDate: TextView =itemView.findViewById(R.id.expiry_date_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedReceiveViewHolder {
        val itemView= LayoutInflater.from(parent.context)
            .inflate(R.layout.item_med_box_layout, parent, false)
        return MedReceiveViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MedReceiveViewHolder, position: Int) {

        val currentMedicine = allMedicine[position]
        holder.name.text=currentMedicine.name
        holder.expiryDate.text=currentMedicine.expiryDate.toString()
        holder.price.text="\u20B9"+currentMedicine.price.toString()
        holder.quantity.text=currentMedicine.quantity.toString()

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

    override fun getItemCount() = allMedicine.size
}