package com.codedamon.medshare.donor.ui.myDonationsPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.adapter.MyDonationsAdapter
import com.codedamon.medshare.donor.model.MyDonation

class MyDonationsFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyDonationsAdapter
    var list = ArrayList<MyDonation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_donations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        initRecycleView()

    }

    private fun initRecycleView(){
        list.add(MyDonation("Chemist 1",20, 2 ))
        list.add(MyDonation("Chemist 2",30, 3 ))
        list.add(MyDonation("Chemist 3",40, 4 ))

        recyclerView.setHasFixedSize(true)
        adapter = MyDonationsAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}