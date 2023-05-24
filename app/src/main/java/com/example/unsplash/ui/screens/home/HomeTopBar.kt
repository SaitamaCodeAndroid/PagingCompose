package com.example.unsplash.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.splash.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    onSearchClicked: () -> Unit,
) {
    val context = LocalContext.current
    TopAppBar(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        title = {
            Text(
                text = context.getString(R.string.app_name),
                color = Color.White
            )
        },
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            }
        }
    )
}

@Composable
@Preview
fun HomeTopBarPreview() {
    HomeTopBar {}
}