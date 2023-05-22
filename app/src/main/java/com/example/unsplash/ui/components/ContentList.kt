package com.example.unsplash.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.unsplash.BuildConfig
import com.example.unsplash.models.Image

@Composable
fun ContentList(items: LazyPagingItems<Image>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(count = items.itemCount) { index ->
            val item = items[index]
        }
    }
}

@Composable
fun ImageItem(image: Image) {
    val context = LocalContext.current
    val painter = AsyncImage(
        model = ImageRequest.Builder(context = context)
            .data(image.urls.regular)
            .crossfade(durationMillis = 1000)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    Box(
        modifier = Modifier.clickable {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(BuildConfig.BASE_URL)
            )
        }
    ) {

    }
}