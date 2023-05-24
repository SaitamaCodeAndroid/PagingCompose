package com.example.unsplash.ui.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.splash.R
import com.example.unsplash.models.Image
import com.example.unsplash.models.Urls
import com.example.unsplash.models.User
import com.example.unsplash.models.UserLinks

@Composable
fun ContentList(
    modifier: Modifier,
    items: LazyPagingItems<Image>,
) {
    //Log.d("Error")
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(count = items.itemCount) { index ->
            ImageItem(image = items[index]!!)
        }
    }
}

@Composable
fun ImageItem(image: Image) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .clickable {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://unsplash.com/@${image.user.username}?utm_source=DemoApp&utm_medium=referral")
                )
                ContextCompat.startActivity(
                    context,
                    browserIntent,
                    null
                )
            }
            .height(300.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context = context)
                .data(image.urls.regular)
                .crossfade(durationMillis = 1000)
                .error(R.drawable.ic_placeholder)
                .placeholder(R.drawable.ic_placeholder)
                .build(),
            contentDescription = "Unsplash Image",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
@Preview
fun ImageItemPreview() {
    ImageItem(
        image = Image(
            id = "1",
            urls = Urls(regular = ""),
            likes = 100,
            user = User(
                username = "Unsplash User",
                userLinks = UserLinks(html = ""),
            ),
        )
    )
}