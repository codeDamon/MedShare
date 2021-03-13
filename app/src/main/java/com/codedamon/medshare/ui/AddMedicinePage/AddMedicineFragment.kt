package com.codedamon.medshare.ui.AddMedicinePage

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codedamon.medshare.R
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class AddMedicineFragment : Fragment() {

    companion object {
        fun newInstance() = AddMedicineFragment()
    }

    private lateinit var viewModel: AddMedicineViewModel
    private lateinit var calenderIcon : ImageView
    private lateinit var expireDate: TextInputLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_medicine_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddMedicineViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expireDate=view.findViewById(R.id.expiry_date_et)
        calenderIcon = view.findViewById(R.id.calender_icon)
        calenderIcon.setOnClickListener {
            calenderPickerPrompt()
        }
    }

    private fun calenderPickerPrompt(){
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

        val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{
                                view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->

            expireDate.editText.let {
                it?.setText("$dayOfMonth/${month+1}/$year")
            }

        }, year, month, dayOfMonth)
        datePicker.show()
    }
}