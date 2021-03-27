package com.codedamon.medshare.ui.OnBoardingPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.codedamon.medshare.R

class OnBoardingScreenFragment(private val title: String, private val description:String, img: Int, pos : Int) : Fragment() {

    private val position = pos
    private val image : Int  = img
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_on_boarding_screen, container, false)

        view.findViewById<TextView>(R.id.description_tv).text = description
        view.findViewById<TextView>(R.id.onBoardingScreenItemTitle).text = title
        view.findViewById<ImageView>(R.id.image).setImageDrawable(ContextCompat.getDrawable(requireContext(), image))



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





    }
}