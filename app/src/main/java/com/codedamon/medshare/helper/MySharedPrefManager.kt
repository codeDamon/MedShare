package com.codedamon.medshare.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object MySharedPrefManager{

    init {

    }

    private val isDonor = "DONOR_USER"
    private var sharedPref: SharedPreferences? = null

    fun initializeSharedPref(activity: Activity){
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    }

    fun clearPref(){
        sharedPref = null
    }

    fun setUserType(isGetDonor: Boolean): Int{
        if(sharedPref==null) return -1
            with (sharedPref!!.edit()) {
                putBoolean(isDonor, isGetDonor )
                apply()
            }
        return 1
    }

    fun getIsDonor():Boolean{
        if(sharedPref==null) return true

        return sharedPref!!.getBoolean(isDonor, true)
    }
}