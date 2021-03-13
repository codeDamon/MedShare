package com.codedamon.medshare.ui.boxDisplayPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.adapter.MedicineBoxRvAdapter
import com.codedamon.medshare.model.MedicineBox
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BoxDisplayFragment : Fragment(), MedicineBoxRvAdapter.MedBoxInterface {

    companion object {
        fun newInstance() = BoxDisplayFragment()
    }

    private lateinit var viewModel: BoxDisplayViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.box_display_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BoxDisplayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.med_rv)
        initRecyclerView()

        navController = Navigation.findNavController(view)
        val generateQrBtn : ExtendedFloatingActionButton = view.findViewById(R.id.qr_button)
        generateQrBtn.setOnClickListener {
            navController.navigate(R.id.action_boxDisplayFragment_to_qrPageFragment)
        }

        val addMedBtn: Button = view.findViewById(R.id.add_med_button)
        addMedBtn.setOnClickListener {
            navController.navigate(R.id.action_boxDisplayFragment_to_addMedicineFragment)
        }
    }

    private fun initRecyclerView(){
        val medList = ArrayList<MedicineBox>()
        val demoItem = MedicineBox("Paracetamol", 20.00, 2, "12/01/2022")
        medList.add(demoItem)
        medList.add(demoItem)
        medList.add(demoItem)
        medList.add(demoItem)

        val adapter = MedicineBoxRvAdapter(requireContext(),medList, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        adapter.notifyDataSetChanged()
    }

    override fun onExpandClicked() {

    }
}