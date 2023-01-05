package com.example.android_05_news_app.presentation.post

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.android_05_news_app.presentation.base.BaseViewModel
import com.example.android_05_news_app.presentation.home.POST_ARGS_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<PostEvent, PostState, PostIntent>(
    PostState(
        post = savedStateHandle[POST_ARGS_KEY]
            ?: throw  IllegalArgumentException("Post can't be null")
    )
) {
    override fun handleIntent(intent: PostIntent) {
        when (intent) {
            is PostIntent.OnBackButtonClick -> {
                viewModelScope.launch {
                    _event.emit(PostEvent.NavigateToHome)
                }
            }
            is PostIntent.OnGoToPostSourceClick -> {
                viewModelScope.launch {
                    _event.emit(PostEvent.NavigateToPostSource(intent.url))
                }
            }
        }
    }
}