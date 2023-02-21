package com.stirkaparus.stirkaparus.presentation.orders_list_screen

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val prefs: SharedPreferences
) : ViewModel() {


    private val _state = mutableStateOf(Boolean)
    val state = _state

    private val db = Firebase.firestore

    fun fetchPosts() = callbackFlow {
        val companyId = prefs.getString(COMPANY_ID,null)

        val collection = db.collection(Constants.ORDERS)
            .whereEqualTo(Constants.COMPANY_ID, companyId)

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



