package com.codedamon.medshare.ui.HomePage

import android.R.attr.bitmap
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codedamon.medshare.R
import com.codedamon.medshare.ui.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.WriterException


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

        navController = Navigation.findNavController(view)

        val logoutFragBtn : Button = view.findViewById(R.id.logout_button)
        logoutFragBtn.setOnClickListener {
//            navController.navigate(R.id.action_homeFragment_to_loginFragment)
            mAuth.signOut()
            val intent=Intent(context,SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}