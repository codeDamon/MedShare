package com.codedamon.medshare.ui.LoginPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.medshare.R

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val addMedFragBtn : Button = view.findViewById(R.id.add_med_button)
        addMedFragBtn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_addMedicineFragment)
        }
        val homeBtn : Button = view.findViewById(R.id.home_button)
        homeBtn.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }



}