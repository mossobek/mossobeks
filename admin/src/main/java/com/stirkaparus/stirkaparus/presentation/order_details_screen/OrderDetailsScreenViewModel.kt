package com.stirkaparus.stirkaparus.presentation.order_details_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.Status
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class OrderDetailsScreenViewModel() : ViewModel() {
    private val db = Firebase.firestore

    private var _orderStatus: MutableLiveData<String> = MutableLiveData()
    val orderStatus: LiveData<String> = _orderStatus


    fun getOrder(
        productId: String
    ) = callbackFlow {
        val productIdRef = db.collection(Constants.ORDERS).document(productId)
        val snapshotListener = productIdRef.addSnapshotListener { snapshot, e ->
            val productResponse = if (snapshot != null) {
                val product = snapshot.toObject(Order::class.java)
                     _orderStatus.value = product?.status.toString()

                Log.e(TAG, "getProductFromFirestore: $product")

                Resource.success(product)


            } else {

                Resource.error(e?.message.toString(), null)

            }
            trySend(productResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

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