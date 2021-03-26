package com.codedamon.medshare.ui.OnBoardingPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.codedamon.medshare.R

class OnBoardingFragment : Fragment(){

    private lateinit var navController: NavController
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        val fragmentList = arrayListOf<OnBoardingScreenFragment>(
            OnBoardingScreenFragment("Welcome0", R.drawable.donation, 0),
            OnBoardingScreenFragment("Welcome1", R.drawable.people_with_med, 1),
            OnBoardingScreenFragment("Welcome2", R.drawable.chemist_logo, 2),
            OnBoardingScreenFragment("Welcome3", R.drawable.collect_hearts, 3),
            OnBoardingScreenFragment("Welcome3", R.drawable.save_earth, 4)

        )

        val adapter = OnBoardingAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle )

        viewPager2 = view.findViewById(R.id.view_pager)
        viewPager2.adapter = adapter

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    view.findViewById<Button>(R.id.back).visibility = View.GONE
                }else
                    view.findViewById<Button>(R.id.back).visibility = View.VISIBLE

                if (position == 4) {
                    view.findViewById<Button>(R.id.next).text = "Begin"
                }else{
                    view.findViewById<Button>(R.id.next).text = "Next"
                }

                super.onPageSelected(position)
            }
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        val next = view.findViewById<Button>(R.id.next)
        val back = view.findViewById<Button>(R.id.back)
        val skip = view.findViewById<Button>(R.id.skip)

        skip.setOnClickListener {
            navController.navigate(R.id.action_onBoardingFragment_to_signInFragment)
        }

        next.setOnClickListener{
            if(viewPager2.currentItem < 4) {
                viewPager2.currentItem = viewPager2.currentItem + 1
            }
            else if(viewPager2.currentItem == 4){
                navController.navigate(R.id.action_onBoardingFragment_to_signInFragment)
            }
        }
        back.setOnClickListener{
            if(viewPager2.currentItem in 1..4){
                viewPager2.currentItem = viewPager2.currentItem - 1
            }
        }
    }
}