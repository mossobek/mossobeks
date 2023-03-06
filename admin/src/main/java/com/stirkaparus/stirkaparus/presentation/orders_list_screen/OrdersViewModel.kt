package com.stirkaparus.stirkaparus.presentation.orders_list_screen

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.domain.repository.OrdersRepository
import com.stirkaparus.stirkaparus.domain.repository.OrdersResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val repo: OrdersRepository,
    private val prefs: SharedPreferences
) : ViewModel() {


    companion object {
        const val TAG = "OrdersViewModel"
    }

    var orderListResponse by mutableStateOf<OrdersResponse>(Response.Loading)
        private set
    var sortSectionOpen by mutableStateOf(false)


    private val db = Firebase.firestore

    fun getOrders() = viewModelScope.launch {
        repo.getOrdersFromFireStore().collect { response ->
            orderListResponse = response
            Log.e(TAG, "getOrders:$response ")
        }
    }

    fun sortSectionOpen() {
        sortSectionOpen = true
    }
}




