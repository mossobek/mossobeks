package com.stirkaparus.stirkaparus.presentation.order_details

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.domain.repository.OrderDetailsRepository
import com.stirkaparus.stirkaparus.domain.repository.OrderResponse
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val repo: OrderDetailsRepository
) : ViewModel() {
    private val db = Firebase.firestore
//
//    private var _orderStatus: MutableLiveData<String> = MutableLiveData()
//    val orderStatus: LiveData<String> = _orderStatus

    var orderDetailsResponse by mutableStateOf<OrderResponse>(Response.Loading)
        private set

    fun getOrder(
        id: String
    ) = viewModelScope.launch {
        repo.getOrderFromFirestore(id).collect { response ->
            orderDetailsResponse = response
        }
    }

//    callbackFlow
//    {
//        val productIdRef = db.collection(Constants.ORDERS).document(productId)
//        val snapshotListener = productIdRef.addSnapshotListener { snapshot, e ->
//            val productResponse = if (snapshot != null) {
//                val product = snapshot.toObject(Order::class.java)
//                _orderStatus.value = product?.status.toString()
//
//                Log.e(TAG, "getProductFromFirestore: $product")
//
//                Resource.success(product)
//
//
//            } else {
//
//                Resource.error(e?.message.toString(), null)
//
//            }
//            trySend(productResponse)
//        }
//        awaitClose {
//            snapshotListener.remove()
//        }
//    }

    fun deleteOrder(
        id: String?, success: () -> Unit, failure: () -> Unit
    ) {
        db.collection(Constants.ORDERS).document(id.toString()).set(
            hashMapOf(
                "status" to "deleted", "delete_time" to FieldValue.serverTimestamp()
            ), SetOptions.merge()
        ).addOnSuccessListener { success() }.addOnFailureListener { failure() }
    }

    fun changeStatus(
        id: String?, status: String, success: () -> Unit, failure: () -> Unit
    ) {
        Log.e(TAG, "OrderDetailsScreen:$id + $status")
        db.collection(Constants.ORDERS).document(id.toString()).set(
            hashMapOf(
                "status" to status, status + "_time" to FieldValue.serverTimestamp()
            ), SetOptions.merge()
        ).addOnSuccessListener { success() }.addOnFailureListener { failure() }
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