package com.codedamon.medshare.donor.ui.boxDisplayPage

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.Lottie
import com.airbnb.lottie.LottieAnimationView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.adapter.MedicineRvAdapter
import com.codedamon.medshare.donor.ui.addMedicinePage.AddMedicineViewModel
import com.codedamon.medshare.model.medicine.Medicine
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.JsonArray


class BoxDisplayFragment : Fragment(), MedicineRvAdapter.MedBoxInterface {

    private lateinit var viewModel: BoxDisplayViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var adapter: MedicineRvAdapter
    private lateinit var noDataAnim: LottieAnimationView

    private var isFloatingButtonExpanded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.box_display_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BoxDisplayViewModel::class.java)


        viewModel.allMedicines.observe(viewLifecycleOwner, Observer { list ->
            noDataAnim.visibility = if (list.isEmpty())
                View.VISIBLE
            else
                View.GONE

            list?.let {
                adapter.updateList(it)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        isFloatingButtonExpanded = false

        recyclerView = view.findViewById(R.id.med_rv)
        noDataAnim = view.findViewById(R.id.animationView)

        var floatingExpand = view.findViewById<FloatingActionButton>(R.id.expand_fab)
        val floatingAddMed = view.findViewById<FloatingActionButton>(R.id.add_med_fab)
        val floatingGenQr = view.findViewById<FloatingActionButton>(R.id.generate_qr_fab)

        val floatingGenQrTv = view.findViewById<TextView>(R.id.scan_qr_action_text)
        val floatingAddMedTv = view.findViewById<TextView>(R.id.add_med_action_text)

        floatingAddMed.visibility = View.INVISIBLE
        floatingGenQr.visibility = View.INVISIBLE
        floatingAddMedTv.visibility = View.GONE
        floatingGenQrTv.visibility = View.GONE

        floatingExpand.setOnClickListener{

            if(isFloatingButtonExpanded){
                floatingGenQr.hide()
                floatingAddMed.hide()
                floatingAddMedTv.visibility = View.GONE
                floatingGenQrTv.visibility = View.GONE
                isFloatingButtonExpanded = false
            } else{
                floatingGenQr.show()
                floatingAddMed.show()
                floatingAddMedTv.visibility = View.VISIBLE
                floatingGenQrTv.visibility = View.VISIBLE
                isFloatingButtonExpanded = true
            }
        }

        initRecycleView()

        activity?.let {
            viewModel = ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(it.application)
            )
                .get(BoxDisplayViewModel::class.java)
        }

        navController = Navigation.findNavController(view)
        floatingGenQr.setOnClickListener {

            val action = BoxDisplayFragmentDirections.actionBoxDisplayFragmentToQrPageFragment(
                parseListToString()
            )
            navController.navigate(action)
        }

        floatingAddMed.setOnClickListener {
            navController.navigate(R.id.action_boxDisplayFragment_to_addMedicineFragment)
        }
    }

    override fun onExpandClicked() {

    }

    override fun onDeleteClicked(position:Int) {
        viewModel.allMedicines.value?.get(position)?.let { viewModel.deleteMedicine(it) }
    }

    private fun parseListToString(): String {

        val jsonArray = JsonArray()
        for (i in viewModel.allMedicines.value!!) {
            val gson = Gson()
            val json: String = gson.toJson(i)
            jsonArray.add(json)
        }
        val gson = Gson()
        val json: String = gson.toJson(jsonArray)


        Log.d("Parse", json)
        return json

    }

    private fun initRecycleView() {
        recyclerView.setHasFixedSize(true)
        adapter = MedicineRvAdapter(requireContext(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}
