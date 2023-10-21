@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.firebase_demo.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.firebase_demo.R
import com.example.firebase_demo.data.UserData
import com.example.firebase_demo.ui.theme.FirebasedemoTheme

@Composable
fun HomeScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Home") },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    }
                },
                actions = {
                    AsyncImage(
                        model = userData?.profilePictureUrl ?: R.drawable.ic_launcher_foreground,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                    IconButton(onClick = onSignOut) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                    }
                },
                scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
    Column(

    ) {

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    FirebasedemoTheme {
        HomeScreen(
            userData = UserData(
                userId = "123",
                username = "John Doe",
                profilePictureUrl = "https://miro.medium.com/v2/resize:fill:88:88/1*ZD360DI0Ba9_guEt5FKpBw.jpeg"
            )
        ) {

        }
    }
}