package com.stirkaparus.stirkaparus.data.repository

import android.content.SharedPreferences
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.stirkaparus.common.Constants.CREATED
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED
import com.stirkaparus.stirkaparus.common.Constants.FALSE
import com.stirkaparus.stirkaparus.common.Constants.FINISHED
import com.stirkaparus.stirkaparus.common.Constants.ORDERS
import com.stirkaparus.stirkaparus.common.Constants.REPORTED
import com.stirkaparus.stirkaparus.common.Constants.STATUS
import com.stirkaparus.stirkaparus.domain.repository.AddOrderInFirestoreResponse
import com.stirkaparus.stirkaparus.domain.repository.OrdersRepository
import com.stirkaparus.stirkaparus.domain.repository.ReportsOrdersResponse
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.COMPANIES
import com.stirkaparus.stirkaparus.domain.repository.ReportUserOrderListResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class OrdersRepositoryImpl @Inject constructor(
    private val ordersRef: CollectionReference,
    private val db: FirebaseFirestore,
 ) : OrdersRepository {
    companion object {
        const val TAG = "OrdersRepositoryImpl"
    }

    private val reportsOrdersRef = db.collection(ORDERS)



    override fun getCreatedOrders() = callbackFlow {
        val snapshotListener = ordersRef
            .whereIn(STATUS, listOf(FINISHED, CREATED))
            .addSnapshotListener { snapshot, e ->
                val orderResponse = if (snapshot != null) {
                    val orders = snapshot.toObjects(Order::class.java)
                    Response.Success(orders)
                } else {
                    Response.Failure(e)
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
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


    override suspend fun getReportsOrdersFromFirestore(company_id: String): ReportsOrdersResponse {
        return try {
            val orders = reportsOrdersRef
                .whereEqualTo(COMPANY_ID, company_id)
                .whereEqualTo(STATUS, DELIVERED)
                .whereEqualTo(REPORTED, FALSE)
                .get().await().toObjects(Order::class.java)
            Response.Success(orders)
        } catch (e: Exception) {
            Response.Failure(e)
        }

    }



}

