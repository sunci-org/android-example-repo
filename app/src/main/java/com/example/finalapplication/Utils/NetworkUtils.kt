package com.example.finalapplication.Utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetworkUtils {
    fun isInternetAvailable(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities =
                connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
            return false
        } else {
            val wifiNetwork = connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            val mobileNetwork = connectivityManager?.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            val isWifiConnected = wifiNetwork?.isConnected
            val isMobileConnected = mobileNetwork?.isConnected
            return isWifiConnected == true || isMobileConnected == true
        }
    }
}
