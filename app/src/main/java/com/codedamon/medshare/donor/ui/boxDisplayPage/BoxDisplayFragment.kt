package com.codedamon.medshare.donor.ui.boxDisplayPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.adapter.MedicineRvAdapter
import com.codedamon.medshare.model.medicine.Medicine
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.gson.Gson
import com.google.gson.JsonArray


class BoxDisplayFragment : Fragment(), MedicineRvAdapter.MedBoxInterface {

    private lateinit var viewModel: BoxDisplayViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var adapter : MedicineRvAdapter

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
            list?.let {
                adapter.updateList(it)
            }

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.med_rv)
        initRecycleView()

        activity?.let {
            viewModel=ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(it.application)
            )
                .get(BoxDisplayViewModel::class.java)
        }

        navController = Navigation.findNavController(view)
        val generateQrBtn : ExtendedFloatingActionButton = view.findViewById(R.id.qr_button)
        generateQrBtn.setOnClickListener {
            parseListToString()
            navController.navigate(R.id.action_boxDisplayFragment_to_qrPageFragment)
        }

        val addMedBtn: Button = view.findViewById(R.id.add_med_button)
        addMedBtn.setOnClickListener {
            navController.navigate(R.id.action_boxDisplayFragment_to_addMedicineFragment)
        }


    }

    override fun onExpandClicked() {

    }

    private fun parseListToString(){

        val jsonArray: JsonArray = JsonArray()
        for (i in viewModel.allMedicines.value!!){
            val gson = Gson()
            val json : String = gson.toJson(i)
            jsonArray.add(json)
        }
        val gson = Gson()
        val json = gson.toJson(jsonArray)
        Log.d("Parse", json)

        extractStringToJson(json)
    }

    private fun extractStringToJson(s: String){

        val convertedJsonArray: JsonArray = Gson().fromJson(s, JsonArray::class.java)

        for(jsonObject in convertedJsonArray){
            val obj = jsonObject.asString
            val convertedObject: Medicine = Gson().fromJson(obj, Medicine::class.java)
            Log.d("Parse",convertedObject.name)
        }
    }

    private fun initRecycleView(){
        recyclerView.setHasFixedSize(true)
        adapter = MedicineRvAdapter(requireContext(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}
