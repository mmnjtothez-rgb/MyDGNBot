package com.mydgnbot.data.network

import kotlinx.coroutines.flow.Flow


interface ConnectivityObserver {

    val isOnline: Flow<Boolean>

}
