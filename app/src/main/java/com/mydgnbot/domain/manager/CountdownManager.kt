package com.mydgnbot.domain.manager

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CountdownManager {

    private val _remaining = MutableStateFlow(0L)
    val remaining: StateFlow<Long> = _remaining

    suspend fun start(durationMillis: Long) {

        val endTime = System.currentTimeMillis() + durationMillis

        while (true) {

            val left = endTime - System.currentTimeMillis()

            if (left <= 0L) {
                _remaining.value = 0L
                break
            }

            _remaining.value = left

            delay(1000)
        }
    }

    fun stop() {
        _remaining.value = 0L
    }
}