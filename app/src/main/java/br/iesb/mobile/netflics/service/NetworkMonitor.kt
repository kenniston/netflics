package br.iesb.mobile.netflics.service

import android.annotation.TargetApi
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData

@TargetApi(24)
class NetworkMonitor(private val context: Context): LiveData<Boolean>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun onActive() {
        super.onActive()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            postValue(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }

}