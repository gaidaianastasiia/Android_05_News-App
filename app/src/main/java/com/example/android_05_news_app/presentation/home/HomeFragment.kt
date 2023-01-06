package com.example.android_05_news_app.presentation.home

import android.os.Bundle
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.android_05_news_app.R
import com.example.android_05_news_app.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

const val POST_ARGS_KEY = "postArgsKey"

@AndroidEntryPoint
class HomeFragment : BaseFragment<
        HomeEvent,
        HomeState,
        HomeIntent,
        HomeViewModel>() {
    override val viewModel: HomeViewModel by viewModels()

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ShowError -> {
                Toast.makeText(
                    requireContext(), event.errorMessage, Toast.LENGTH_LONG)
                    .show()
            }
            is HomeEvent.NavigateToPostDetail -> {
                val bundle = Bundle()
                bundle.putParcelable(POST_ARGS_KEY, event.post)
                findNavController().navigate(R.id.viewPost, bundle)
            }
        }
    }

    @Composable
    override fun HandleState(state: HomeState) {
        HomeScreen(
            state = state,
            onSearchInputChanged = { searchInput ->
                viewModel.handleIntent(
                    HomeIntent.OnSearchInputChanged(searchInput)
                )
            },
            onExecuteSearch = {
                viewModel.handleIntent(
                    HomeIntent.OnExecuteSearch
                )
            },
            onSelectedCategoryChanged = { selectedCategory ->
                viewModel.handleIntent(
                    HomeIntent.OnSelectedCategoryChanged(selectedCategory)
                )
            },
            onPostsListScrolled = { index ->
                viewModel.handleIntent(
                    HomeIntent.OnPostsListScrolled(index)
                )
            },
            onPostsListItemClick = { post ->
                viewModel.handleIntent(
                    HomeIntent.OnNavigateToPostDetail(post)
                )
            }
        )
    }
}


