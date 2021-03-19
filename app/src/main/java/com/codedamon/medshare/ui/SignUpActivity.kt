package com.codedamon.medshare.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.codedamon.medshare.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout

    companion object {
        private const val TAG = "EmailPassword"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        email = findViewById(R.id.email_et)
        password = findViewById(R.id.password_et)

        auth = Firebase.auth

        findViewById<TextView>(R.id.sign_in_link).setOnClickListener {
            finish()
        }
        findViewById<Button>(R.id.sign_up_button).setOnClickListener {
            signUpUsingEmail()
        }

    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if(currentUser != null){
            //reload();
        }
    }

    fun signUpUsingEmail(){

        email.error = null
        password.error = null

        if(email.editText?.text.toString().isEmpty()){
            email.error = "Please enter your email-id"
            return;
        }

        if(password.editText?.text.toString().isEmpty()){
            password.error = "Please enter password"
            return;
        }else if(password.editText?.text.toString().length <6 ){
            password.error = "Minimum password length should be 6 characters"
            return;
        }
        createAccount(email = email.editText?.text.toString(),
            password = password.editText?.text.toString())
    }

    private fun createAccount(email: String, password: String) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "Account Created",
                        Toast.LENGTH_SHORT).show()

                    val intent=Intent(this,DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Something went wrong",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
        // [END create_user_with_email]
    }
}