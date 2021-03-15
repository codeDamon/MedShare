package com.codedamon.medshare.ui.profilePage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codedamon.medshare.R
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    lateinit var profileImg:ImageView
    lateinit var name:TextView
    lateinit var email:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileImg=view.findViewById(R.id.iv_profile)
        name=view.findViewById(R.id.tv_Name)
        email=view.findViewById(R.id.tv_Email)



        mAuth= FirebaseAuth.getInstance()
        val currentUser=mAuth.currentUser

        email.text = "Email: "+currentUser?.email
        name.text = "Name: "+currentUser?.displayName
//        currentUser?.uid
        Glide.with(this)
            .load(currentUser?.photoUrl)
            .placeholder(R.drawable.ic_round_cloud_download_24)
            .error(R.drawable.ic_round_broken_image_24)
            .fallback(R.drawable.ic_round_image_24)
            .centerCrop()
            .into(profileImg)

    }
}


