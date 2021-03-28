package com.codedamon.medshare.donor.ui.myRewardsPage

import android.R.attr.data
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.adapter.LeaderboardRvAdapter
import com.codedamon.medshare.donor.model.DonorLeader
import com.codedamon.medshare.helper.MySharedPrefManager
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MyRewardsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LeaderboardRvAdapter
    lateinit var heartsCountTv : TextView
    lateinit var pointsCountTv : TextView
    lateinit var progressBar: ProgressBar

    var username : String? = null


    var list = ArrayList<DonorLeader>()

    var pointsCount : Int = 0
    var heartsCount : Int = 0
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MySharedPrefManager.initializeSharedPref(requireActivity())
        username = MySharedPrefManager.getUserName()

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

        getDonations(username!!)

    }

    private fun initRecycleView(){

        updateLeaderBoard()
        recyclerView.setHasFixedSize(true)

        adapter = LeaderboardRvAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun updateLeaderBoard(){
        db.collection("donors")
            .get()
            .addOnSuccessListener { documents ->
                for (donor in documents) {
                    Log.d("TAG", "${donor.id} => ${donor.data}")

                    list.add(DonorLeader(donor.id, donor["points"].toString().toLong()))

                }
                //list.sortedWith(compareByDescending { it.points })
                /*val sortedList:ArrayList<DonorLeader> = list
                list.clear()

                sortedList.sortedBy { newL ->
                    adapter.notifyDataSetChanged()
                    list.add(newL)

                }*/

                adapter.notifyDataSetChanged()

            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents: ", exception)
            }
    }

    private fun getDonations(user: String){
        val ref: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("user_transactions")
            .child(user)
        val check: Query = ref.orderByKey()

        check.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                pointsCount = 0
                heartsCount = 0

                Log.d("POST", snapshot.value.toString())
                list.clear()
                for (child in snapshot.children) {

                    Log.d("POST1", child.child("chemist").value.toString())
                    pointsCount += child.child("points").value.toString().toInt()
                    heartsCount += child.child("hearts").value.toString().toInt()

                }
                //adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
                pointsCountTv.text = pointsCount.toString()
                heartsCountTv.text = heartsCount.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("POST", "No Data")
                Toast.makeText(context, "No donations found", Toast.LENGTH_SHORT).show()
                //progressBar.visibility = View.GONE
            }

        })
    }
}