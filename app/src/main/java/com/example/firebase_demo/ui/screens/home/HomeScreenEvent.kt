package com.example.firebase_demo.ui.screens.home

sealed class HomeScreenEvent {
    object saveData : HomeScreenEvent()
    data class SetName(val name: String) : HomeScreenEvent()
    data class SetImageUrl(val image: String) : HomeScreenEvent()
    data class SetQty(val qty: String) : HomeScreenEvent()
    data class SetPrice(val price: String) : HomeScreenEvent()
}