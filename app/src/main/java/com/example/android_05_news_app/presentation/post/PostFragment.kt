package com.example.android_05_news_app.presentation.post

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AndroidUriHandler
import androidx.fragment.app.viewModels
import com.example.android_05_news_app.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController

@AndroidEntryPoint
class PostFragment : BaseFragment<
        PostEvent,
        PostState,
        PostIntent,
        PostViewModel
        >() {
    override val viewModel: PostViewModel by viewModels()
    private val uriHandler by lazy { AndroidUriHandler(requireContext()) }

    override fun handleEvent(event: PostEvent) {
        when (event) {
            is PostEvent.NavigateToHome -> {
                findNavController().popBackStack()
            }
            is PostEvent.NavigateToPostSource -> {
                uriHandler.openUri(event.url)
            }
        }
    }

    @Composable
    override fun HandleState(state: PostState) {
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