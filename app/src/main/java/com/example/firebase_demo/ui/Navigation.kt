package com.example.firebase_demo.ui

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.firebase_demo.service.GoogleAuthUiClient
import com.example.firebase_demo.ui.screens.sign_in.*
import com.example.firebase_demo.ui.screens.home.HomeScreen
import kotlinx.coroutines.launch

enum class Screen {
    SignIn, Home
}

@Composable
fun Navigation(
    context: Context, authClient: GoogleAuthUiClient
) {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    NavHost(navController = navController, startDestination = Screen.SignIn.name) {
        composable(Screen.SignIn.name) {
            val viewModel: SignInViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            LaunchedEffect(key1 = Unit) {
                if (authClient.getSignedInUser() != null) {
                    navController.navigate(Screen.Home.name)
                }
            }

            val launcher =
                rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        when (result.resultCode) {
                            RESULT_OK -> {
                                coroutineScope.launch {
                                    val signInResult = authClient.signInWithIntent(
                                        intent = result.data ?: return@launch
                                    )
                                    viewModel.onSignInResult(signInResult)
                                }
                            }

                            RESULT_CANCELED -> {
                                Toast.makeText(
                                    context,
                                    "Sign in cancelled",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    context,
                                    "Sign in failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    })
            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    navController.navigate(Screen.Home.name)
                    viewModel.resetState()
                }
            }
            SignInScreen(state = state, onSignInClick = {
                coroutineScope.launch {
                    Toast.makeText(context, "Signing in", Toast.LENGTH_SHORT).show()
                    val signInIntentSender = authClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(signInIntentSender ?: return@launch).build()
                    )
                }
            })
        }
        composable(
            Screen.Home.name,
        ) {
            HomeScreen(
                userData = authClient.getSignedInUser(),
                onSignOut = {
                    coroutineScope.launch {
                        authClient.signOut()
                        Toast.makeText(
                            context,
                            "Signed out",
                            Toast.LENGTH_LONG
                        ).show()
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}