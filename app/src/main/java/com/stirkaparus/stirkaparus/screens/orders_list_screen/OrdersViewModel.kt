package com.stirkaparus.stirkaparus.screens.orders_list_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.stirkaparus.stirkaparus.model.Order
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


class OrdersViewModel(
) : ViewModel() {


    private val _state = mutableStateOf(Boolean)
    val state = _state

    private val db = Firebase.firestore

    fun fetchPosts() = callbackFlow {
        val collection = db.collection("orders")

        val snapshotListener = collection
            //.orderBy("phone", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                val response = if (error == null && value != null) {
                    val data = value.documents.map { doc ->

                            doc.toObject<Order>().also {
                                if (it != null) {
                                    it.id = doc.id
                                }
                            }

                    }
                    Resource.success(data)

                } else {
                    Resource.error(error.toString(), null)
                }
                this.trySend(response).isSuccess
            }
        awaitClose {
            snapshotListener.remove()
        }
    }


    fun getAgoTime(ts: Timestamp?): String {
        val time = (Timestamp.now().seconds).minus(ts!!.seconds)

        return getShortDateAgo(time)

    }

    private fun getShortDateAgo(ts: Long?): String {
        if (ts == null) return ""

        return when {

            ts < 60 -> {
                (ts / 60).toString() + " с."

            }
            ts in 61..3599 -> {
                (ts / 60).toString() + " м."
            }
            ts in 3600..86400 -> {
                (ts / 3600).toString() + " ч."
            }
            ts > 86401 -> {
                (ts / 86400).toString() + " д."
            }

            else -> ""
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



