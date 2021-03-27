package com.codedamon.medshare.ui.loginSignUpPages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.medshare.R
import com.codedamon.medshare.helper.MySharedPrefManager
import com.codedamon.medshare.ui.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {

    companion object {
        private const val RC_SIGN_IN = 128
        private const val TAG = "EmailPassword"

    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var email: TextInputLayout
    private lateinit var password: TextInputLayout
    private lateinit var progressBar: ProgressBar
    private var isDonorAccount: Boolean = true

    private lateinit var navController: NavController


    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MySharedPrefManager.initializeSharedPref(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = view.findViewById(R.id.email_et)
        password = view.findViewById(R.id.password_et)
        navController = Navigation.findNavController(view)
        progressBar = view.findViewById(R.id.progress_bar)


        view.findViewById<TextView>(R.id.sign_up_link).setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        configureGoogleSignIn()
        view.findViewById<SignInButton>(R.id.google_sign_in_btn).setOnClickListener {
            signInUsingGoogle()
        }

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            progressBar.visibility = View.VISIBLE
            signInUsingEmail(view.findViewById(R.id.radio_group))
        }
    }

    private fun userSignIn() {

        db.collection("users").document(email.editText?.text.toString())
            .get()
            .addOnSuccessListener {
                if (it != null) {
                    if (it["type"] != null) {
                        if (it["type"] == "donor" && isDonorAccount || it["type"] == "chemist" && !isDonorAccount) {
                            MySharedPrefManager.setUserType(isDonorAccount)
                            signIn(
                                email = email.editText?.text.toString(),
                                password = password.editText?.text.toString()
                            )
                        } else {
                            Toast.makeText(
                                context,
                                "Incorrect type selected! Switch between donor and chemist",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            progressBar.visibility = View.GONE

                        }
                    } else {
                        Toast.makeText(
                            context,
                            "No account found! Register Now",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        Log.d(TAG, "No type field in users collection")
                        progressBar.visibility = View.GONE
                    }
                } else {
                    Log.d(TAG, "No account found")
                    progressBar.visibility = View.GONE
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed with error $it")
            }
    }

    private fun getCurrentUserType(radioGroup: RadioGroup) {
        when (radioGroup.checkedRadioButtonId) {
            R.id.rad_donor -> {
                isDonorAccount = true
            }
            R.id.rad_chemist -> {
                isDonorAccount = false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            //reload();
        }
    }

    private fun signIn(email: String, password: String) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser
                    //updateUI(user)
                    Toast.makeText(
                        context, "Welcome to MedShare",
                        Toast.LENGTH_SHORT
                    ).show()

                    progressBar.visibility = View.GONE


                    if(isDonorAccount)
                        navController.navigate(R.id.action_signInFragment_to_homeFragment)
                    else {
                        navController.navigate(R.id.action_signInFragment_to_homeChemistFragment)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                    progressBar.visibility = View.GONE

                }
            })
        // [END sign_in_with_email]
    }

    private fun signInUsingEmail(radioGroup: RadioGroup) {

        if (email.editText?.text.toString().isEmpty()) {
            email.error = "Please enter your email-id"
            progressBar.visibility = View.GONE
            return
        }
        if (password.editText?.text.toString().isEmpty()) {
            password.error = "Please enter password"
            progressBar.visibility = View.GONE
            return
        }
        getCurrentUserType(radioGroup)

        userSignIn()

    }


    private fun configureGoogleSignIn() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        //Firebase Auth Instance
        mAuth = FirebaseAuth.getInstance()
    }

    private fun signInUsingGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if (task.isSuccessful) {
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity(), OnCompleteListener<AuthResult>() { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")
                    Toast.makeText(
                        context, "Welcome to MedShare",
                        Toast.LENGTH_SHORT
                    ).show()

                    //TODO
                    /*val intent=Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()*/
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)

                }
            })
    }


}