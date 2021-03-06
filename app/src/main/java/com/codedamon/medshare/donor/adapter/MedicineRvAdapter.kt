package com.codedamon.medshare.donor.adapter

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
import kotlin.math.roundToInt

class MedicineRvAdapter(
    val context: Context, private val mInterface: MedBoxInterface)
    : RecyclerView.Adapter<MedicineRvAdapter.MedBoxViewHolder>(){

    private val allMedicine = ArrayList<Medicine>()

    interface MedBoxInterface{
        fun onExpandClicked();
        fun onDeleteClicked(position: Int);
    }


    inner class MedBoxViewHolder(view: View) : RecyclerView.ViewHolder(view){

        val detailsLayout : LinearLayout = view.findViewById(R.id.details_layout)
        val downArrow: ImageView = view.findViewById(R.id.down_arrow)
        val delete: ImageView = view.findViewById(R.id.delete_med)
        val price:TextView=itemView.findViewById(R.id.tv_perMedCost)
        val quantity:TextView=itemView.findViewById(R.id.tv_quantity)
        val name:TextView=itemView.findViewById(R.id.med_name_tv)
        val expiryDate:TextView=itemView.findViewById(R.id.expiry_date_tv)
        val earnTv : TextView = itemView.findViewById(R.id.earn_tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedBoxViewHolder {
        val itemView=LayoutInflater.from(parent.context)
            .inflate(R.layout.item_med_box_layout, parent, false)
        return MedBoxViewHolder(itemView)
    }

    override fun getItemCount(): Int = allMedicine.size

    override fun onBindViewHolder(holder: MedBoxViewHolder, position: Int) {
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
        holder.delete.setOnClickListener {
            mInterface.onDeleteClicked(position)
        }

        holder.earnTv.text = "Earn "+calculateHearts(position)
    }

    private fun calculateHearts(position: Int): String {
        val value = allMedicine[position].price * allMedicine[position].quantity
        val hearts = (value * 0.2).roundToInt()
        return hearts.toString()
    }

    fun updateList(newList: List<Medicine>){
        allMedicine.clear()
        allMedicine.addAll(newList)
        notifyDataSetChanged()
    }
}
