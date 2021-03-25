package com.codedamon.medshare.donor.ui.myRewardsPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.adapter.LeaderboardRvAdapter
import com.codedamon.medshare.donor.adapter.MedicineRvAdapter
import com.codedamon.medshare.donor.model.DonorLeader

class MyRewardsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: LeaderboardRvAdapter
    var list = ArrayList<DonorLeader>()

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
        initRecycleView()



    }

    private fun initRecycleView(){
        list.add(DonorLeader("Ash",20))
        list.add(DonorLeader("Ash2",10))
        list.add(DonorLeader("Ash3",80))

        recyclerView.setHasFixedSize(true)
        adapter = LeaderboardRvAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}