package com.codedamon.medshare.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

object MySharedPrefManager{

    init {

    }

    private val isDonor = "DONOR_USER"
    private val user = "USER_NAME"
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

    fun setUserName(username:String):Int{
        if(sharedPref==null) return -1
        with (sharedPref!!.edit()) {
            putString(user, username )
            apply()
        }
        return 1
    }

    fun getUserName():String?{
        if(sharedPref==null) return null

        return sharedPref!!.getString(user, null)
    }
}