package com.example.android_05_news_app.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.android_05_news_app.R
import dagger.hilt.android.AndroidEntryPoint

const val POST_ARGS_KEY = "postArgsKey"

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val localContext = LocalContext.current

                LaunchedEffect(Unit) {
                    viewModel.event.collect {
                        when (it) {
                            is HomeEvent.ShowError -> {
                                Toast.makeText(
                                    localContext, it.errorMessage, Toast.LENGTH_LONG)
                                    .show()
                            }
                            is HomeEvent.NavigateToPostDetail -> {
                                val bundle = Bundle()
                                bundle.putParcelable(POST_ARGS_KEY, it.post)
                                findNavController().navigate(R.id.viewPost, bundle)
                            }
                        }
                    }
                }

                val state by viewModel.state.collectAsState()
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
                    OnScrollPostsListListener = { index ->
                        viewModel.handleIntent(
                            HomeIntent.OnScrollPostsListListener(index)
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
    }
}


