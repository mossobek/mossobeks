package com.stirkaparus.driver.presentation.finishedOrderDetails

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.driver.presentation.createdOrderDetails.Resource
import com.stirkaparus.model.Order
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class FinishedOrderDetailsScreenViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    fun getOrder(
        productId: String
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






