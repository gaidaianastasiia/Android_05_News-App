package com.example.android_05_news_app.presentation.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalUriHandler
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val uriHandler = LocalUriHandler.current

                LaunchedEffect(Unit) {
                    viewModel.event.collect {
                        when (it) {
                            is PostEvent.NavigateToHome -> {
                                findNavController().popBackStack()
                            }
                            is PostEvent.NavigateToPostSource -> {
                                uriHandler.openUri(it.url)
                            }
                        }
                    }
                }

                val state by viewModel.state.collectAsState()
                PostScreen(
                    state = state,
                    onBack = {
                        viewModel.handleIntent(
                            PostIntent.OnBackButtonClick
                        )
                    },
                    onGoToPostSourceClick = { url ->
                        uriHandler.openUri(url)
                    }
                )
            }
        }
    }
}