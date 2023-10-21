package com.example.firebase_demo.service

import android.content.Context
import android.widget.Toast
import com.example.firebase_demo.data.Electronic
import com.example.firebase_demo.ui.screens.home.HomeUiState
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.StateFlow

class AppFireStoreClient(
    val context: Context,
    val state: StateFlow<HomeUiState>
) {
    val db = Firebase.firestore

    fun addElectronic(item: Electronic) {
        db.collection(COLL_ELECTRONICS)
            .add(item)
            .addOnFailureListener { exception ->
                val msg = exception.message
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener {
                Toast.makeText(context, "Item added", Toast.LENGTH_SHORT).show()
            }
    }

    fun listElectronics() {
        val electronics = mutableListOf<Electronic>()
        db.collection(COLL_ELECTRONICS)
            .get()
            .addOnFailureListener {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
            .addOnCanceledListener {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
                    state.value.electronic = null
                } else {
                    for (document in result) {
                        val item = document.toObject(Electronic::class.java) // conversion
                        electronics.add(item)
                    }
                    state.value.electronic = electronics
                }
            }
    }

    companion object {
        const val COLL_ELECTRONICS = "electronics"
    }
}