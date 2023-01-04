package com.example.android_05_news_app.presentation.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_05_news_app.presentation.home.POST_ARGS_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _event = MutableSharedFlow<PostEvent>()
    val event = _event.asSharedFlow()

    private val _state = MutableStateFlow(
        PostState(
            post = savedStateHandle[POST_ARGS_KEY]
                ?: throw  IllegalArgumentException("Post can't be null")
        )
    )
    val state = _state.asStateFlow()

    fun handleIntent(postIntent: PostIntent) {
        when (postIntent) {
            is PostIntent.OnBackButtonClick -> {
                viewModelScope.launch {
                    _event.emit(PostEvent.NavigateToHome)
                }
            }
            is PostIntent.OnGoToPostSourceClick -> {
                viewModelScope.launch {
                    _event.emit(PostEvent.NavigateToPostSource(postIntent.url))
                }
            }
        }
    }
}