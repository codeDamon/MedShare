package com.codedamon.medshare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager

import com.codedamon.medshare.R
import com.codedamon.medshare.ui.HomePage.HomeFragment
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long=3000 // 3 sec

//    lateinit var viewModel: BoxDisplayViewModel
//    private lateinit var bottomNavigationView: BottomNavigationView
//    private lateinit var navController: NavController

    private lateinit var mAuth:FirebaseAuth
    lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        mAuth= FirebaseAuth.getInstance()
        val user=mAuth.currentUser

        /** If user is not authenticated, send him to SignInActivity to authenticate first
         *  else send him to dashboardActivity*/
        Handler().postDelayed({
            if(user!=null){
                val dashboardIntent=Intent(this,HomeFragment::class.java)
                startActivity(dashboardIntent)
                finish()
            }else{
                //signup fragment hai
                val signInIntent=Intent(this,SignInActivity::class.java)
                startActivity(signInIntent)
                finish()
            }
        },SPLASH_TIME_OUT)


//        bottomNavigationView = findViewById(R.id.bottom_navigation)
//        navController = findNavController(R.id.fragment)

        //TODO : After adding top app bar
        /*val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.loginFragment,
                            R.id.signUpFragment,R.id.boxDisplayFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)*/
//        bottomNavigationView.setupWithNavController(navController)
    }

    /*
    companion object {

        private const val RC_SIGN_IN = 123
    }
    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
        // [END auth_fui_create_intent]
    }
    // [START auth_fui_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
    // [END auth_fui_result]

    */
}

