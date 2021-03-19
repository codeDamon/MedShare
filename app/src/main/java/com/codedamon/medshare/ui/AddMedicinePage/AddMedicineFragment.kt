package com.codedamon.medshare.ui.AddMedicinePage
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.medshare.R
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import com.codedamon.medshare.model.Medicine
import java.text.ParseException
import java.text.SimpleDateFormat


/**                       We can use DATA BINDING

import androidx.databinding.DataBindingUtil
import com.codedamon.medshare.databinding.FragmentAddMedicineBinding
*/
class AddMedicineFragment : androidx.fragment.app.Fragment() {

    companion object {
        fun newInstance() = AddMedicineFragment()
    }

    private lateinit var navController: NavController
    private lateinit var viewModel: AddMedicineViewModel
    private lateinit var calenderIcon : ImageView
    private lateinit var expiry: TextInputLayout
    private lateinit var name:TextInputLayout
    private lateinit var price:TextInputLayout
    private lateinit var quantity:TextInputLayout
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
        name=view.findViewById(R.id.med_name_et)
        price=view.findViewById(R.id.med_cost_et)
        quantity=view.findViewById(R.id.med_quantity_et)

        calenderIcon = view.findViewById(R.id.calender_icon)
        calenderIcon.setOnClickListener {
            calenderPickerPrompt()
        }

        activity?.let {
            viewModel=ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(it.application))
                .get(AddMedicineViewModel::class.java)
        }
        navController = Navigation.findNavController(view)

        val addMedBtn: Button = view.findViewById(R.id.addMedBtn)
        addMedBtn.setOnClickListener {
            if(validateMedInfo()){
                val medicine = Medicine(
                    name.editText?.text.toString(),price.editText?.text.toString().toDouble(),quantity.editText?.text.toString().toInt(),
                    expiry.editText?.text.toString()
                )
                viewModel.addMedicine(medicine)
//                view : View ->
                navController.navigate(R.id.action_addMedicineFragment_to_boxDisplayFragment)

            }

            // Hide the keyboard.
            val inputMethodManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

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

    private fun validateMedInfo():Boolean{

        name.error= null
        expiry.error = null
        price.error = null
        quantity.error = null

        var valid = true

        if(name.editText?.text.toString().isEmpty()){
            name.error = "Can't be empty"
            valid = false
        }

        if(expiry.editText?.text.toString().isEmpty()){
            expiry.error = "Can't be empty"
            valid = false
        }else if(!validDate(expiry.editText?.text.toString())){
            expiry.error = "Invalid date"
            valid = false
        }

        if(price.editText?.text.toString().isEmpty()){
            price.error = "Can't be empty"
            valid = false
        } else if(price.editText?.text.toString().toDouble() <= 0){
            price.error = "Price can't be zero or less"
            valid = false
        }

        if(quantity.editText?.text.toString().isEmpty()){
            quantity.error = "Can't be empty"
            valid = false
        } else if(quantity.editText?.text.toString().toLong() <= 0){
            quantity.error = "Quantity can't be zero or less"
            valid = false
        }
        return valid
    }

    private fun validDate(date: String): Boolean{
        val dateFormat = "yyyy/mm/dd"
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        return try {
            simpleDateFormat.parse(date)
            true
        }catch (e:ParseException){
            false
        }
    }
}