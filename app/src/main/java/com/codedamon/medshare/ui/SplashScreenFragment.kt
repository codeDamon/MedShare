package com.codedamon.medshare.ui

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.medshare.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashScreenFragment : Fragment() {

    private val splashTimeOut: Long = 1500 // 1 sec
    private lateinit var mAuth: FirebaseAuth
    private var user: FirebaseUser? = null
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bottomNavigationView =
            activity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView?.visibility = View.GONE

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splach_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI(view)
        user = mAuth.currentUser

        Handler().postDelayed({
            if (user != null) {
                navController.navigate(R.id.action_splashScreenFragment_to_homeFragment)
            } else {
                navController.navigate(R.id.action_splashScreenFragment_to_onBoardingFragment)
            }
        }, splashTimeOut)
    }

    private fun initUI(view: View) {
        navController = Navigation.findNavController(view)

        val l1: LinearLayout = view.findViewById<LinearLayout>(R.id.l1)
        l1.animation = AnimationUtils.loadAnimation(context, R.anim.up_to_down_anim)
    }
}