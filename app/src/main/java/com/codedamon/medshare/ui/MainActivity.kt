package com.codedamon.medshare.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.codedamon.medshare.R
import com.codedamon.medshare.donor.ui.boxDisplayPage.BoxDisplayViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: BoxDisplayViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth= FirebaseAuth.getInstance()
        val currentUser=mAuth.currentUser
        /* user details obtained from google sign in can be used
        currentUser?.uid
        currentUser?.displayName
        currentUser?.email
        Glide.with(this)
            .load(currentUser?.photoUrl)
            .placeholder(R.drawable.ic_round_cloud_download_24)
            .error(R.drawable.ic_round_broken_image_24)
            .fallback(R.drawable.ic_round_image_24)
            .centerCrop()
            .into(imageViewID)
        */

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navController = findNavController(R.id.fragment)

        //TODO : After adding top app bar
        /*val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.loginFragment,
                            R.id.signUpFragment,R.id.boxDisplayFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)*/
        bottomNavigationView.setupWithNavController(navController)
    }
}