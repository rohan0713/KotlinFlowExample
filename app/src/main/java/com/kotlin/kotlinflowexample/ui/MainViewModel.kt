package com.kotlin.kotlinflowexample.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {

    val countDown = flow<Int> {

        val startValue = 10
        var currentValue = startValue
        emit(startValue)

        while (currentValue > 0){
            delay(1000)
            currentValue--
            emit(currentValue)
        }
    }
}