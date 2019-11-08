package com.netguru.sampleapp

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@InternalCoroutinesApi
class MainViewModel(
    private val state: MutableLiveData<State> = MutableLiveData(),
    private var count: Int = 0,
    private var count2: Int = 0,
    private var count3: Int = 0
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                state.postValue(State.Hello(count++))
                delay(1000)
                state.postValue(State.Loading)
                delay(1000)
            }
        }
    }

    fun state(): LiveData<State> = state

    fun state2() = state2

    val state3: Flow<State> = flow {
        while (isActive) {
            emit(State.Hello(count3++))
            delay(1000)
            emit(State.Loading)
            delay(1000)
        }
    }

    private val state2: LiveData<State> = liveData<State>(Dispatchers.IO) {
        doThings()
    }

    private suspend fun LiveDataScope<State>.doThings() {
        while (isActive) {
            emit(State.Hello(count2++))
            delay(1000)
            emit(State.Loading)
            delay(1000)
        }
    }
}

sealed class State {
    object Loading : State()
    class Hello(val count: Int) : State()
}