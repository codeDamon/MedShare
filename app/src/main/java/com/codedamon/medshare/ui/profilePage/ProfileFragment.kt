package com.codedamon.medshare.ui.profilePage

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.medshare.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth
    lateinit var profileImg: ImageView
    lateinit var name: TextView
    lateinit var email: TextView
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImg = view.findViewById(R.id.iv_profile)
        name = view.findViewById(R.id.tv_Name)
        email = view.findViewById(R.id.tv_Email)

        view.findViewById<LinearLayout>(R.id.sign_out_lay).setOnClickListener(this)
        navController = Navigation.findNavController(view)
        view.findViewById<LinearLayout>(R.id.my_donations_lay).setOnClickListener(this)
        view.findViewById<LinearLayout>(R.id.my_rewards_lay).setOnClickListener(this)


        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        email.text = currentUser?.email
        /*if(currentUser.displayName != null)
            name.text = currentUser?.displayName
//        currentUser?.uid
        Glide.with(this)
            .load(currentUser?.photoUrl)
            .placeholder(R.drawable.ic_round_cloud_download_24)
            .error(R.drawable.ic_round_broken_image_24)
            .fallback(R.drawable.ic_round_image_24)
            .centerCrop()
            .into(profileImg)*/

    }

    override fun onClick(v: View?) {
        if (v == null) return
        when (v.id) {
            R.id.sign_out_lay -> {

                context?.let {
                    MaterialAlertDialogBuilder(it)
                        .setTitle("Logging out")
                        .setMessage("Are you sure you want to logout?")
                        .setNegativeButton("Cancel") { dialog, which ->
                            // Respond to negative button press
                            dialog.dismiss()
                        }
                        .setPositiveButton("Logout") { dialog, which ->
                            // Respond to positive button press
                            mAuth.signOut()
                            dialog.dismiss()

                            /*val intent = Intent(context, SignInActivity::class.java)
                            startActivity(intent)
                            activity?.finish()*/
                            navController.navigate(R.id.action_profileFragment_to_signInFragment2)
                        }
                        .show()
                }
            }

            R.id.my_donations_lay -> {
                navController.navigate(R.id.action_profileFragment_to_myDonationsFragment)
            }
            R.id.my_rewards_lay -> {
                navController.navigate(R.id.action_profileFragment_to_myRewardsFragment)
            }
        }
    }
}


