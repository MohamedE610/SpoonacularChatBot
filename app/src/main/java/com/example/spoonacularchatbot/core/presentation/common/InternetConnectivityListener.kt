package com.example.spoonacularchatbot.core.presentation.common

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import com.example.spoonacularchatbot.core.presentation.modelwraper.SingleLiveEvent
import javax.inject.Inject

class InternetConnectivityListener @Inject constructor(private val applicationContext: Context) {

    private val internetBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i("isNetworkConnected", "called")
            if (!isInitialStickyBroadcast) {
                internetConnectivityLiveData.value = isNetworkConnected()
                Log.i("isNetworkConnected", isNetworkConnected().toString())
            }
        }
    }

    var internetConnectivityLiveData = SingleLiveEvent<Boolean>()

    // call it in onCreate()
    fun registerInternetReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION)
        applicationContext.registerReceiver(internetBroadcastReceiver, intentFilter)
    }

    // call it in onDestroy()
    fun unregisterInternetReceiver() {
        try {
            applicationContext.unregisterReceiver(internetBroadcastReceiver)
        } catch (ex: Exception) {
        }
    }

    //internet permission added in both mobile and tv
    @SuppressLint("MissingPermission")
    private fun isNetworkConnected(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}