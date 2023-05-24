package com.example.unsplash.ui.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.unsplash.navigation.Screen
import com.example.unsplash.ui.components.ContentList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val allImages = homeViewModel.allImages.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            HomeTopBar { navController.navigate(Screen.Search.route) }
        },
    ) {
        ContentList(
            modifier = Modifier.padding(paddingValues = it),
            items = allImages,
        )
    }
}