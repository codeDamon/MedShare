package com.codedamon.medshare.ui.OnBoardingPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.codedamon.medshare.R

class OnBoardingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        val fragmentList = arrayListOf<Fragment>(
            OnBoardingScreenFragment("Welcome0", R.drawable.donation, 0),
            OnBoardingScreenFragment("Welcome1", R.drawable.people_with_med, 1),
            OnBoardingScreenFragment("Welcome2", R.drawable.chemist_logo, 2),
            OnBoardingScreenFragment("Welcome3", R.drawable.collect_hearts, 3)
        )

        val adapter = OnBoardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle )

        view.findViewById<ViewPager2>(R.id.view_pager).adapter = adapter


        return view
    }

}