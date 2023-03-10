package com.stirkaparus.driver.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.driver.common.Constants.COMPANIES
import com.stirkaparus.driver.common.Constants.COMPANY_ID
import com.stirkaparus.driver.common.Constants.CREATED
import com.stirkaparus.driver.common.Constants.FINISHED
import com.stirkaparus.driver.common.Constants.ID
import com.stirkaparus.driver.common.Constants.ORDERS
import com.stirkaparus.driver.common.Constants.STATUS
import com.stirkaparus.driver.domain.repository.AddOrderInFirestoreResponse
import com.stirkaparus.driver.domain.repository.OrdersRepository
import com.stirkaparus.model.Order

import com.stirkaparus.model.Response.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class OrdersRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val firebaseFirestore: FirebaseFirestore
) : OrdersRepository {

    private val companyId = prefs.getString(COMPANY_ID, "")
    private val userId = prefs.getString(ID, "")
    private val ordersRef = firebaseFirestore
        .collection(COMPANIES)
        .document(companyId.toString())
        .collection(ORDERS)


    companion object {
        const val TAG = "OrdersRepositoryImpl"
    }


    override fun getOrdersFromFireStore(

    ) = callbackFlow {
        val snapshotListener = ordersRef
            .whereEqualTo(COMPANY_ID, companyId)
            .whereIn(STATUS, listOf(CREATED, FINISHED))
            .addSnapshotListener { snapshot, e ->
                val orderResponse = if (snapshot != null) {
                    val orders = snapshot.toObjects(Order::class.java)
                    Log.e(TAG, "getOrdersFromFireStore: $orders ")
                    Success(orders)
                } else {
                    Failure(e)
                }
                trySend(orderResponse)

            }
        awaitClose {
            snapshotListener.remove()
        }

    }

    override suspend fun addOrderInFirestore(
        order: Order
    ): AddOrderInFirestoreResponse {
        return try {
            val id = ordersRef.document().id
            order.id = id

            ordersRef.document(id)
                .set(order)
                .await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

}

