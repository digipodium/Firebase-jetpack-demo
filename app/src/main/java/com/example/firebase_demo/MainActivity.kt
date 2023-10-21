package com.example.firebase_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.firebase_demo.service.GoogleAuthUiClient
import com.example.firebase_demo.ui.Navigation
import com.example.firebase_demo.ui.theme.FirebasedemoTheme
import com.google.android.gms.auth.api.identity.Identity

class MainActivity : ComponentActivity() {
    private val googleAuthClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            signInClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebasedemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        context = applicationContext,
                        authClient = googleAuthClient
                    )
                }
            }
        }
    }
}
