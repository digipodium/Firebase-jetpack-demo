@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.firebase_demo.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
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
import com.example.firebase_demo.data.Electronic
import com.example.firebase_demo.data.UserData
import com.example.firebase_demo.ui.theme.FirebasedemoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userData: UserData?,
    state: HomeUiState,
    onSignOut: () -> Unit
) {
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
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.electronic ?: emptyList()) { item ->
                    ElectronicCard(item)
                }
            }
        }
    }
}

@Composable
fun ElectronicCard(item: Electronic) {
    ElevatedCard {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = item.imgUrl,
                contentDescription = "Electronic image",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Crop,
            )
            Text(text = item.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = item.price)
            Text(text = item.qty.toString())
        }
    }
}

@Preview
@Composable
fun ElectronicCardPreview() {
    FirebasedemoTheme {
        ElectronicCard(
            item = Electronic(
                name = "iPhone 12",
                imgUrl = "https://www.gizmochina.com/wp-content/uploads/2020/10/Apple-iPhone-12-Pro-Max-500x500.jpg",
                price = "1000",
                qty = 10
            )
        )
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
            ),
            state = HomeUiState(),
        ) {

        }
    }
}