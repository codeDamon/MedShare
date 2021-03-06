package com.codedamon.medshare.donor.ui.homePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.medshare.R
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.VISIBLE

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

        view.findViewById<CardView>(R.id.help_box).setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_boxDisplayFragment)
        }
        view.findViewById<CardView>(R.id.profile).setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_profileFragment)
        }
        view.findViewById<CardView>(R.id.rewards).setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_myRewardsFragment)
        }
        view.findViewById<CardView>(R.id.donation).setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_myDonationsFragment)
        }
    }
}