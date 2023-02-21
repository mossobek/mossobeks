package com.stirkaparus.driver.data.repository

import com.google.firebase.firestore.CollectionReference
import com.stirkaparus.driver.common.Constants.COMPANY_ID
import com.stirkaparus.driver.common.Constants.CREATED
import com.stirkaparus.driver.common.Constants.FINISHED
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
    private val ordersRef: CollectionReference
) : OrdersRepository {


    override fun getCreatedOrders(
         companyId: String
    ) = callbackFlow {
        val snapshotListener = ordersRef
            .whereEqualTo(COMPANY_ID, companyId)
            .whereIn(STATUS, listOf(CREATED, FINISHED))
            .addSnapshotListener { snapshot, e ->
                val orderResponse = if (snapshot != null) {
                    val orders = snapshot.toObjects(Order::class.java)
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
            ordersRef.document()
                .set(order)
                .await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

}

