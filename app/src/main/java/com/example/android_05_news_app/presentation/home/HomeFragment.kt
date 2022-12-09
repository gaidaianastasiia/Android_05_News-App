package com.example.android_05_news_app.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.android_05_news_app.domain.model.Post
import com.example.android_05_news_app.domain.model.NewsCategories
import com.example.android_05_news_app.presentation.components.CircularProgress
import com.example.android_05_news_app.presentation.components.PostCards
import com.example.android_05_news_app.presentation.components.SearchAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

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
                    state,
                    ) { selectedCategory ->
                    viewModel.handleIntent(
                        HomeIntent.OnSelectedCategoryChanged(selectedCategory)
                    )

                }
            }
        }
    }
}

