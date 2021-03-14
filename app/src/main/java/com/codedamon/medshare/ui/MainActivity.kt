package com.codedamon.medshare.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.codedamon.medshare.R
import com.codedamon.medshare.ui.boxDisplayPage.BoxDisplayViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: BoxDisplayViewModel
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        bottomNavigationView = findViewById(R.id.bottom_navigation)
        navController = findNavController(R.id.fragment)

        //TODO : After adding top app bar
        /*val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.loginFragment,
                            R.id.signUpFragment,R.id.boxDisplayFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)*/
        bottomNavigationView.setupWithNavController(navController)
    }
}