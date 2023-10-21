package com.example.firebase_demo.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.firebase_demo.data.Electronic
import com.example.firebase_demo.service.AppFireStoreClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private val fireClient = AppFireStoreClient(context, state)

    init {
        listData()
    }

    fun onEvent(event: HomeScreenEvent){
        when (event){
            is HomeScreenEvent.SetImageUrl -> TODO()
            is HomeScreenEvent.SetName -> TODO()
            is HomeScreenEvent.SetPrice -> TODO()
            is HomeScreenEvent.SetQty -> TODO()
            HomeScreenEvent.saveData -> TODO()
        }
    }

    fun saveData(electronic: Electronic) {
        fireClient.addElectronic(electronic)
    }

    fun listData() {
        fireClient.listElectronics()
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(context) as T
    }
}



