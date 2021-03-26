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
import androidx.viewpager2.widget.ViewPager2
import com.codedamon.medshare.R

class OnBoardingScreenFragment(private val description:String, img: Int, pos : Int) : Fragment() {

    private val position = pos
    private val image : Int  = img

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_on_boarding_screen, container, false)

        val viewPager2 = activity?.findViewById<ViewPager2>(R.id.view_pager)
        view.findViewById<TextView>(R.id.tv).text = description
        view.findViewById<ImageView>(R.id.image).setImageDrawable(ContextCompat.getDrawable(requireContext(), image))

        view.findViewById<Button>(R.id.next).setOnClickListener{
            if(position < 3)
                viewPager2?.currentItem  = position + 1
        }

        return view
    }
}