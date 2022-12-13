package com.example.android_05_news_app.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

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
                LaunchedEffect(Unit) {
                    viewModel.event.collect {

                        when (it) {
                            is HomeEvent.ShowError -> {
                                //show toast
                            }
                        }
                    }
                }

                val state by viewModel.state.collectAsState()
                HomeScreenContent(
                    state = state,
                    onSearchInputChanged = { searchInput ->
                        viewModel.handleIntent(
                            HomeIntent.OnSearchInputChanged(searchInput)
                        )
                    },
                    onExecuteSearch = {
                        viewModel.handleIntent(
                            HomeIntent.OnExecuteSearch()
                        )
                    },
                    onSelectedCategoryChanged = { selectedCategory ->
                        viewModel.handleIntent(
                            HomeIntent.OnSelectedCategoryChanged(selectedCategory)
                        )
                    },
                )
            }
        }
    }
}


