package com.stirkaparus.stirkaparus.data.repository

import android.util.Log
import com.google.firebase.firestore.Query
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.common.Constants.USER
import com.stirkaparus.stirkaparus.domain.repository.ReportsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportsRepositoryImpl @Inject constructor(
    private var ordersQuery: Query
) : ReportsRepository {
    override fun getReportsOrdersFromFirestore(userId: String) = callbackFlow {

        if (userId.isNotEmpty()) {
            ordersQuery = ordersQuery.whereEqualTo(USER, userId)
        }

        val snapshotListener = ordersQuery.addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {

                val orderList = snapshot.toObjects(Order::class.java)
                Log.e(TAG, "getReportsOrdersFromFirestore: $orderList")
                Response.Success(orderList)
            } else {
                Response.Failure(e)
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }

    }

}