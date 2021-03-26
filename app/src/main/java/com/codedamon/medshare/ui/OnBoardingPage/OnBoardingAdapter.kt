package com.codedamon.medshare.ui.OnBoardingPage

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingAdapter(list: ArrayList<OnBoardingScreenFragment>,fragManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragManager,lifecycle) {

    private val fragmentList = list

    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int): OnBoardingScreenFragment {
        return fragmentList[position]
    }
}