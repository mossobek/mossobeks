package com.stirkaparus.driver.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.driver.common.Constants.COMPANIES
import com.stirkaparus.driver.common.Constants.COMPANY_ID
import com.stirkaparus.driver.common.Constants.ORDERS
import com.stirkaparus.driver.domain.repository.OrderDetailsRepository
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow


class OrderDetailsRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val prefs: SharedPreferences
) : OrderDetailsRepository {
    private val companyID = prefs.getString(COMPANY_ID, "").toString()
    private val companyRef = firebaseFirestore.collection(COMPANIES)

    companion object {
        const val TAG = "OrderDetailsRepositoryImpl"
    }

    override fun getOrderFromFirestore(id: String) = callbackFlow {
        Log.e(TAG, "getOrderFromFirestore: ")


        val orderRef = companyRef
            .document(companyID)
            .collection(ORDERS)
            .document(id)
        val snapshotListener = orderRef.addSnapshotListener { snapshot, e ->

            Log.e(TAG, "getOrderFromFirestore: $snapshot")


            val orderResponse = if (snapshot != null) {
                val order = snapshot.toObject(Order::class.java)

                Log.e(TAG, "getOrderFromFirestore: $order")

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