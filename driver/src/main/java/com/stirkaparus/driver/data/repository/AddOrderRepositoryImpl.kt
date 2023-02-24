package com.stirkaparus.driver.data.repository

import com.google.firebase.firestore.CollectionReference
import com.stirkaparus.driver.domain.repository.AddOrderRepository
import com.stirkaparus.driver.domain.repository.AddOrderResponse
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddOrderRepositoryImpl @Inject constructor(
    private val ordersRef: CollectionReference
) : AddOrderRepository {

    override suspend fun addOrderToFireStore(order: Order): AddOrderResponse {
        return try {
            val id = ordersRef.document().id
            ordersRef.document(id).set(order).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }


}