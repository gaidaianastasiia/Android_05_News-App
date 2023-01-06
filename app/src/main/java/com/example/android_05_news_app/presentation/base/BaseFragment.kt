package com.example.android_05_news_app.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

abstract class BaseFragment<
        E : UiEvent,
        S : UiState,
        I : UiIntent,
        VM : BaseViewModel<E, S, I>,
        > : Fragment() {
    protected abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LaunchedEffect(Unit) {
                    viewModel.event.collect { event ->
                        handleEvent(event)
                    }
                }

                val state by viewModel.state.collectAsState()
                HandleState(state)
            }
        }
    }

    protected abstract fun handleEvent(event: E)

    @Composable
    protected abstract fun HandleState(state: S)
}