package com.kotlin.kotlinflowexample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    //Producer
    val countDown = flow {

        val startValue = 5
        var currentValue = startValue
        emit(startValue)

        while (currentValue > 0) {
            delay(1000)
            currentValue--
            emit(currentValue)
        }
    }

    init {
        collectRoutine()
    }

    private fun collectFlow() {

        //Consumer
        viewModelScope.launch {
            val result = countDown
                .filter { time ->
                    time % 2 == 0
                }
                .map { time ->
                    time * time
                }.count()
            println(result)
        }

        viewModelScope.launch {
            val result = countDown.reduce { accumulator, value ->
                accumulator + value
            }
            println(result)
        }

        viewModelScope.launch {
            val result = countDown.fold(20) { accumulator, value ->
                accumulator + value
            }
            println(result)
        }
    }

    private fun collectRoutine() {

        val flow = flow {
            delay(1000)
            emit("Eat")

            delay(1000)
            emit("work")

            delay(1000)
            emit("gym")

        }

        //buffer, conflate, collectLatest
        viewModelScope.launch {
            flow.onEach {
                println("Flow: $it time")
            }.buffer()
                .collect {
                    delay(1500)
                    println("Flow: $it done")
                }
        }

    }
}