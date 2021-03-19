package com.codedamon.medshare.ui.homePage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.medshare.R
import com.codedamon.medshare.ui.loginSignUpPages.SignInActivity
import com.google.firebase.auth.FirebaseAuth


class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var navController: NavController
    private lateinit var mAuth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth= FirebaseAuth.getInstance()


        navController = Navigation.findNavController(view)

        val logoutFragBtn : Button = view.findViewById(R.id.logout_button)
        logoutFragBtn.setOnClickListener {
//            navController.navigate(R.id.action_homeFragment_to_loginFragment)
            mAuth.signOut()
            val intent=Intent(context, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


        view.findViewById<Button>(R.id.chemist_button).setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_homeChemistFragment)
        }
    }
}