package com.stirkaparus.stirkaparus.screens.order_details_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.screens.orders_list_screen.Status
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class OrderDetailsScreenViewModel() : ViewModel() {
    private val db = Firebase.firestore

    fun getOrder(
        productId: String
    )  = callbackFlow {
        val productIdRef = db.collection("orders").document(productId)
        val snapshotListener = productIdRef.addSnapshotListener { snapshot, e ->
            val productResponse = if (snapshot != null) {
                val product = snapshot.toObject(Order::class.java)

                Log.e(TAG, "getProductFromFirestore: $product", )

                Resource.success(product)


            } else {

                Resource.error(e?.message.toString(),null)

            }
            trySend(productResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
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