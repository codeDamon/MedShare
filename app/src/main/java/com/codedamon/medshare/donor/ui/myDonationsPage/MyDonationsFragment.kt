package com.codedamon.medshare.donor.ui.myDonationsPage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.adapter.MyDonationsAdapter
import com.codedamon.medshare.donor.model.MyDonation
import com.codedamon.medshare.helper.MySharedPrefManager
import com.google.firebase.database.*
import com.google.gson.Gson

class MyDonationsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyDonationsAdapter
    var list = ArrayList<MyDonation>()
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        MySharedPrefManager.initializeSharedPref(requireActivity())
        val username = MySharedPrefManager.getUserName()

            getDonations(username!!)

        return inflater.inflate(R.layout.fragment_my_donations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)


        initRecycleView()

    }

    private fun getDonations(user:String){
        val ref: DatabaseReference = FirebaseDatabase.getInstance().reference
            .child("user_transactions")
            .child(user)
        val check: Query = ref.orderByKey()

        check.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.d("POST", snapshot.value.toString())
                list.clear()
                for (child in snapshot.children) {

                    Log.d("POST", child.value.toString())


                    Log.d("POST1",child.child("chemist").value.toString())

                    val donation = MyDonation(
                        child.child("chemist").value.toString(),
                        child.child("points").value.toString(),
                        child.child("hearts").value.toString(),
                        child.child("date").value.toString()
                    )
                    list.add(donation)

                }
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("POST", "No Data")
                Toast.makeText(context,"No donations found",Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }

        })
    }


    private fun initRecycleView(){

        recyclerView.setHasFixedSize(true)
        adapter = MyDonationsAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}