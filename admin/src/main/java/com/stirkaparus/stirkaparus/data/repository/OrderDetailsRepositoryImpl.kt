package com.stirkaparus.stirkaparus.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.ORDERS
import com.stirkaparus.stirkaparus.domain.repository.OrderDetailsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class OrderDetailsRepositoryImpl(
    firebaseFirestore: FirebaseFirestore,
    auth: FirebaseAuth
) : OrderDetailsRepository {
    private val orderRef = firebaseFirestore.collection(ORDERS)

    override fun getOrderFromFirestore(id: String) = callbackFlow {
        val orderIdRef = orderRef.document(id)
        val snapshotListener = orderIdRef.addSnapshotListener { snapshot, e ->
            val orderResponse = if (snapshot != null) {
                val order = snapshot.toObject(Order::class.java)
                Response.Success(order)
            } else {
                Response.Failure(e)
            }
            trySend(orderResponse)

        }
        awaitClose {
            snapshotListener.remove()
        }
    }
}