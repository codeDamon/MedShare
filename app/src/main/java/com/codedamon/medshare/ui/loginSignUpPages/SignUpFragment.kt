package com.codedamon.medshare.ui.loginSignUpPages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.codedamon.medshare.R
import com.codedamon.medshare.ui.MainActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {


    private lateinit var auth: FirebaseAuth
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout
    private var currentUser : FirebaseUser? = null

    private val db = Firebase.firestore

    companion object {
        private const val TAG = "EmailPassword"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        currentUser = auth.currentUser
        if(currentUser != null){
            //reload();
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.email_et)
        password = view.findViewById(R.id.password_et)

        auth = Firebase.auth

        view.findViewById<TextView>(R.id.sign_in_link).setOnClickListener {
            //TODO
        }
        view.findViewById<Button>(R.id.sign_up_button).setOnClickListener {
            signUpUsingEmail(view)
        }
    }

    private fun signUpUsingEmail(view: View){

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
            password = password.editText?.text.toString(),view)
    }

    private fun isDonorSignIn(view : View):Boolean{
        if(view.findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId == R.id.rad_donor)
            return true
        return false
    }

    private fun createAccount(email: String, password: String, view: View) {
        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(context, "Account Created",
                        Toast.LENGTH_SHORT).show()

                    val userEnteredValue = hashMapOf<String, String>(
                        "type" to when(isDonorSignIn(view)){
                            true -> "donor"
                            false -> "chemist"
                        }
                    )

                    db.collection("users").document(this.email.editText?.text.toString())
                        .set(userEnteredValue)
                        .addOnSuccessListener {
                            Log.d(TAG, "User Created")
                            /*val intent= Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()*/
                        }
                        .addOnFailureListener {
                                e -> Log.w(TAG, "Error writing document", e)
                            Toast.makeText(context, "Something went wrong",
                                Toast.LENGTH_SHORT).show()
                        }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Something went wrong",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

}