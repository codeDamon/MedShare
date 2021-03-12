package com.codedamon.medshare.ui.AddMedicinePage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codedamon.medshare.R

class AddMedicineFragment : Fragment() {

    companion object {
        fun newInstance() = AddMedicineFragment()
    }

    private lateinit var viewModel: AddMedicineViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_medicine_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddMedicineViewModel::class.java)

    }

}