package com.stirkaparus.stirkaparus.presentation.order_details

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.CANCELED
import com.stirkaparus.stirkaparus.common.Constants.CANCELED_TIME
import com.stirkaparus.stirkaparus.common.Constants.CREATED
import com.stirkaparus.stirkaparus.common.Constants.CREATED_TIME
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED_TIME
import com.stirkaparus.stirkaparus.common.Constants.FINISHED
import com.stirkaparus.stirkaparus.common.Constants.FINISHED_RU
import com.stirkaparus.stirkaparus.common.Constants.TAKEN
import com.stirkaparus.stirkaparus.common.Constants.TAKEN_TIME
import com.stirkaparus.stirkaparus.common.Constants.WASHED
import com.stirkaparus.stirkaparus.common.Constants.WASHED_TIME
import com.stirkaparus.stirkaparus.domain.repository.*
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val repo: OrderDetailsRepository,
) : ViewModel() {
    private val db = Firebase.firestore

    private val _order = MutableLiveData(Order())
    var order = _order

    var successStatusChangeToTaken by mutableStateOf(false)
        private set
    var deliveredDialog by mutableStateOf(false)
        private set

    var orderDeliveredResponse by mutableStateOf<OrderDeliveredResponse>(
        Response.Success(
            false
        )
    )
    var orderTakenResponse by mutableStateOf<OrderTakenResponse>(
        Response.Success(
            false
        )
    )
        private set
    var orderDetailsResponse by mutableStateOf<OrderResponse>(Response.Loading)
        private set
    var changeStatusResponse by mutableStateOf<ChangeStatusResponse>(Response.Loading)
        private set

    fun getOrder(
        id: String
    ) = viewModelScope.launch {
        repo.getOrderFromFirestore(id).collect { response ->
            orderDetailsResponse = response
        }
    }

    fun orderTaken(count: Int, id: String) = viewModelScope.launch {
        orderTakenResponse = Response.Loading
        orderTakenResponse = repo.orderTaken(count, id)
        Log.e(TAG, "orderTaken: $orderTakenResponse")
    }



    fun changeStatus(
        id: String, status: String
    ) = viewModelScope.launch {
        val statusTime = when (status) {
            CREATED -> CREATED_TIME
            TAKEN -> TAKEN_TIME
            WASHED -> WASHED_TIME
            FINISHED -> FINISHED_RU
            DELIVERED -> DELIVERED_TIME
            CANCELED -> CANCELED_TIME
            else -> {
                CREATED_TIME
            }
        }



        Log.e(TAG, "OrderDetailsScreen:$id + $status")
        changeStatusResponse = Response.Loading
        changeStatusResponse = repo.changeStatusInFireStore(id, status, statusTime)

    }

    fun successStatusChangeToTaken() {
        successStatusChangeToTaken = true
    }

    fun openDeliveredDialog() {
        deliveredDialog = true
    }

    fun closeDeliveredDialog() {
        deliveredDialog = false
    }

    fun orderDeliveredStatus(id: String) = viewModelScope.launch {
        orderDeliveredResponse = Response.Loading
        orderDeliveredResponse = repo.orderDelivered(id)
    }

}

enum class Status {
    SUCCESS, ERROR, LOADING
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}