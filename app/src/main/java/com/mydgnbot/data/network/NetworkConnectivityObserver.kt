package com.mydgnbot.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch


class NetworkConnectivityObserver(

    context: Context

) : ConnectivityObserver {


    private val connectivityManager =

        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager



    override val isOnline: Flow<Boolean> =

        callbackFlow {


            fun checkConnection() {


                val network =

                    connectivityManager.activeNetwork



                val capabilities =

                    connectivityManager.getNetworkCapabilities(
                        network
                    )



                val connected =

                    capabilities?.hasCapability(
                        NetworkCapabilities.NET_CAPABILITY_INTERNET
                    ) == true



                trySend(connected)

            }



            checkConnection()



            val callback =

                object : ConnectivityManager.NetworkCallback() {


                    override fun onAvailable(
                        network: android.net.Network
                    ) {

                        launch {

                            checkConnection()

                        }

                    }



                    override fun onLost(
                        network: android.net.Network
                    ) {

                        launch {

                            checkConnection()

                        }

                    }

                }



            connectivityManager.registerDefaultNetworkCallback(
                callback
            )



            awaitClose {


                connectivityManager.unregisterNetworkCallback(
                    callback
                )


            }


        }

}
