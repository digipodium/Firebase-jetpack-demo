package com.example.firebase_demo.service

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.firebase_demo.R
import com.example.firebase_demo.data.SignInResult
import com.example.firebase_demo.data.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

/**
 * The class GoogleAuthUiClient takes a context and a sign in client as parameters.
 * It has a property auth, which is an instance of the Firebase authentication service.
 */
class GoogleAuthUiClient(
    private val context: Context, private val signInClient: SignInClient
) {
    private val auth = Firebase.auth

    /**
     * The function buildSignInRequest does not take any parameters and returns a BeginSignInRequest object.
     * It builds a sign in request with the Google sign in client.
     * It sets the server client ID, which is the client ID of your server,
     * not the client ID of your Android app. It also sets the filterByAuthorizedAccounts
     * parameter to true, which means that only accounts that have been previously used to
     * sign in will be shown. It sets the autoSelectEnabled parameter to true, which means
     * that if there is only one account that has been previously used to sign in,
     * it will be automatically selected.
     */
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                // Your server's client ID, not your Android client ID.
                .setServerClientId(context.getString(R.string.your_web_client_id))
                // Only show accounts previously used to sign in.
                .setFilterByAuthorizedAccounts(false).build()
        ).setAutoSelectEnabled(true).build()
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid, username = displayName, profilePictureUrl = photoUrl?.toString()
        )
    }

    /**
     * The function signIn does not take any parameters and returns an IntentSender object.
     * It tries to sign in the user with Google credentials using the Google sign in client.
     * If the sign in is successful, it returns an intent sender, otherwise it returns null.
     * It catches any exceptions that may occur and prints them to the console.
     */
    suspend fun signIn(): IntentSender? {
        val result = try {
            signInClient.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    /**
     * The function signInWithIntent takes an intent as a parameter and returns a SignInResult
     * object. It tries to sign in the user with Google credentials using the intent and the
     * Firebase authentication service. If the sign in is successful, it returns the user data,
     * otherwise it returns an error message. The function signOut does not take any parameters
     * and does not return anything. It tries to sign out the user from both the
     * Google sign in client and the Firebase authentication service.
     * It catches any exceptions that may occur and prints them to the console.
     */
    suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = signInClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                }, errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null, errorMessage = e.message
            )
        }
    }

    /**
     * The function signOut does not take any parameters and does not return anything.
     * It tries to sign out the user from both the Google sign in client and the
     * Firebase authentication service. It catches any exceptions that may occur and prints
     * them to the console.
     */
    suspend fun signOut() {
        try {
            signInClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }
}
