package com.codedamon.medshare.ui.AddMedicinePage

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codedamon.medshare.R
import com.codedamon.medshare.ui.boxDisplayPage.BoxDisplayViewModel
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import com.codedamon.medshare.model.Medicine
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**                       We can use DATA BINDING

import androidx.databinding.DataBindingUtil
import com.codedamon.medshare.databinding.FragmentAddMedicineBinding
*/
class AddMedicineFragment : Fragment() {

    companion object {
        fun newInstance() = AddMedicineFragment()
    }


    private lateinit var viewModel: AddMedicineViewModel
    private lateinit var calenderIcon : ImageView
    private lateinit var expiry: TextInputLayout
    private lateinit var name:EditText
    private lateinit var price:EditText
    private lateinit var quantity:EditText
    /** private lateinit var binding: FragmentAddMedicineBinding
    private val medicineObj: Medicine = Medicine("MedicineXYZ",0.0,0,"1/1/2021")
    */
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

        /**
        binding = DataBindingUtil.setContentView(this, R.layout.add_medicine_fragment)
        binding.medicine1=Medicine()
        */
        expiry=view.findViewById(R.id.expiry_date_et)
        name=view.findViewById(R.id.et_medName)
        price=view.findViewById(R.id.et_pricePerPiece)
        quantity=view.findViewById(R.id.et_quantity)

        calenderIcon = view.findViewById(R.id.calender_icon)
        calenderIcon.setOnClickListener {
            calenderPickerPrompt()
        }

        activity?.let {
            viewModel=ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(it.application))
                .get(AddMedicineViewModel::class.java)
        }


        val addMedBtn: Button = view.findViewById(R.id.addMedBtn)
        addMedBtn.setOnClickListener {
            /** **************************************************/
            name.text.isEmpty().apply {
                //print toast - error message
            }
            expiry.text.isEmpty().apply {
                //print toast
            }
            expiry.text.isNotEmpty().apply {
                //process string to date format
            }
            price.text.isEmpty().apply {
                //print toast
            }
            quantity.text.isEmpty().apply {
                //print toast
                Toast.makeText(this,"Message",Toast.LENGTH_SHORT).show()
            }
            val medicine = Medicine(
                name.text.toString(),price.text.toString().toDouble(),quantity.text.toString().toInt(),LocalDate.parse(expiry.text.toString())
            )

            /** *************************************************/
            viewModel.addMedicine(medicine)
        }



    }

    private fun calenderPickerPrompt(){
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

        val datePicker = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{
                                view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->

            expiry.editText.let {
                it?.setText("$year/${month+1}/$dayOfMonth")
            }

        }, year, month, dayOfMonth)
        datePicker.show()
    }

}