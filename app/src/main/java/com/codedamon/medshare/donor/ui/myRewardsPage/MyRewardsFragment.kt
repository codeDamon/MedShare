package com.codedamon.medshare.donor.ui.myRewardsPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.adapter.LeaderboardRvAdapter
import com.codedamon.medshare.donor.adapter.MedicineRvAdapter
import com.codedamon.medshare.donor.model.DonorLeader
import com.codedamon.medshare.donor.model.MyDonation
import com.google.firebase.database.*

class MyRewardsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LeaderboardRvAdapter
    lateinit var heartsCountTv : TextView
    lateinit var pointsCountTv : TextView
    lateinit var progressBar: ProgressBar


    var list = ArrayList<DonorLeader>()

    var pointsCount : Int = 0
    var heartsCount : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_rewards, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        heartsCountTv = view.findViewById(R.id.hearts_count)
        pointsCountTv = view.findViewById(R.id.points_count)
        progressBar = view.findViewById(R.id.progress_bar)

        initRecycleView()

        getDonations("Apurv")

    }

    private fun initRecycleView(){
        list.add(DonorLeader("Apurv",600))


        recyclerView.setHasFixedSize(true)
        adapter = LeaderboardRvAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun getDonations(user:String){
        val ref: DatabaseReference = FirebaseDatabase.getInstance().reference.child("user_transactions").child("Apurv")
        val check: Query = ref.orderByKey()

        check.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                pointsCount = 0
                heartsCount = 0

                Log.d("POST", snapshot.value.toString())
                list.clear()
                for (child in snapshot.children) {

                    Log.d("POST1",child.child("chemist").value.toString())
                    pointsCount+=child.child("points").value.toString().toInt()
                    heartsCount+=child.child("hearts").value.toString().toInt()

                }
                //adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
                pointsCountTv.text = pointsCount.toString()
                heartsCountTv.text = heartsCount.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("POST", "No Data")
                Toast.makeText(context,"No donations found", Toast.LENGTH_SHORT).show()
                //progressBar.visibility = View.GONE
            }

        })
    }
}