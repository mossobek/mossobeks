package com.stirkaparus.stirkaparus.presentation.order_edit_screen

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.presentation.order_details_screen.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class OrderEditViewModel : ViewModel() {
    private val db = Firebase.firestore

    fun changeOrder(
        productId: String,
        data: HashMap<String, Any>,
        success:()->Unit,
        failure:()->Unit,
    ) {
        db.collection("orders")
            .document(productId)
            .set(
                data,
                SetOptions.merge()
            )
            .addOnSuccessListener { success() }
            .addOnFailureListener { failure() }
    }

    fun changeOrder1(
        productId: String,
        data: HashMap<String, Any>
    ) = callbackFlow {
        val productIdRef = db.collection("orders").document(productId)
        val snapshotListener = productIdRef.addSnapshotListener { snapshot, e ->
            val productResponse = if (snapshot != null) {
                val product = snapshot.toObject(Order::class.java)

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
}