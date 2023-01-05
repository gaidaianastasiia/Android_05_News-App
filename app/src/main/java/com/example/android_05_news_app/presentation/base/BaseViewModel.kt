package com.example.android_05_news_app.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class BaseViewModel<E, S, in I>(initialState: S) : ViewModel() {
    private val stateMutex: Mutex = Mutex()

    protected val _event: MutableSharedFlow<E> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    protected val _state: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    abstract fun handleIntent(intent: I)

    protected fun changeState(block: S.() -> S) {
        viewModelScope.launch {
            stateMutex.withLock {
                _state.emit(block(_state.value))
            }
        }
    }
}