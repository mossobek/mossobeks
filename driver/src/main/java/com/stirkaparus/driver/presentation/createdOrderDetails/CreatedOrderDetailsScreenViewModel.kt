package com.stirkaparus.driver.presentation.createdOrderDetails

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.driver.common.Constants
import com.stirkaparus.model.Order
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


class OrderDetailsScreenViewModel() : ViewModel() {
    private val db = Firebase.firestore

    private var _orderStatus: MutableLiveData<String> = MutableLiveData()
    val orderStatus: LiveData<String> = _orderStatus


    fun getOrder(
        productId: String
    ) = callbackFlow {
        val productIdRef = db.collection("orders").document(productId)
        val snapshotListener = productIdRef.addSnapshotListener { snapshot, e ->
            val productResponse = if (snapshot != null) {
                val product = snapshot.toObject(Order::class.java)
                _orderStatus.value = product?.status.toString()

                Log.e(ContentValues.TAG, "getProductFromFirestore: $product")

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

    fun addCount(id: String, count: Int, success: () -> Unit, failure: () -> Unit) {
        db.collection(Constants.ORDERS)
            .document(id)
            .set(
                hashMapOf(
                    Constants.COUNT to count,
                    Constants.TAKEN_TIME to FieldValue.serverTimestamp(),
                    Constants.STATUS to Constants.TAKEN

                ), SetOptions.merge()
            )
            .addOnSuccessListener { success() }
            .addOnFailureListener { failure() }
    }

    fun cancelOrder(id: String, success: () -> Unit, failure: () -> Unit) {
        db.collection(Constants.ORDERS)
            .document(id)
            .set(
                hashMapOf(
                    Constants.CANCELED_TIME to FieldValue.serverTimestamp(),
                    Constants.STATUS to Constants.CANCELED

                ), SetOptions.merge()
            )
            .addOnSuccessListener { success() }
            .addOnFailureListener { failure() }
    }

    fun updateOrder(id: String, success: () -> Boolean, failure: () -> Unit) {
        db.collection(Constants.ORDERS)
            .document(id)
            .update(
                mapOf(
                    Constants.DELIVERED_TIME to FieldValue.serverTimestamp(),
                    Constants.STATUS to Constants.DELIVERED

                )
            )
            .addOnSuccessListener { success() }
            .addOnFailureListener { failure() }
    }





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

enum class Status {
    SUCCESS, ERROR, LOADING
}


