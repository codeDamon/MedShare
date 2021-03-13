package com.codedamon.medshare.ui.boxDisplayPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codedamon.medshare.R
import com.codedamon.medshare.adapter.MedicineRvAdapter
import com.codedamon.medshare.model.Medicine
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class BoxDisplayFragment : Fragment(), MedicineRvAdapter.MedBoxInterface {

    companion object {
        fun newInstance() = BoxDisplayFragment()
    }
    private lateinit var viewModel: MedicineViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.box_display_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MedicineViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerView = view.findViewById(R.id.med_rv)
        val medList = ArrayList<Medicine>()
        val demoItem = Medicine("Paracetamol", 20.00, 2, "12/01/2022")
        medList.add(demoItem)
        medList.add(demoItem)
        medList.add(demoItem)
        medList.add(demoItem)

        val adapter = MedicineRvAdapter(requireContext(),medList, this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.hasFixedSize()
        adapter.notifyDataSetChanged()

        viewModel=ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MedicineViewModel::class.java)


        viewModel.allMedicines.observe(viewLifecycleOwner, Observer {list->
            list?.let{
                adapter.updateList(it)
            }

        })

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

    override fun onExpandClicked() {

    }
}