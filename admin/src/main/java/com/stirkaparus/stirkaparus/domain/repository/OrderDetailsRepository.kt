package com.stirkaparus.stirkaparus.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.flow.Flow


typealias OrderResponse = Response<Order>
typealias ChangeStatusResponse = Response<Boolean>
typealias OrderTakenResponse = Response<Boolean>
typealias EditOrderResponse = Response<Boolean>
typealias DeleteOrderResponse = Response<Boolean>
typealias OrderDeliveredResponse = Response<Boolean>


interface OrderDetailsRepository {
    val currentUser: FirebaseUser?
    fun getOrderFromFirestore(id: String): Flow<OrderResponse>
    suspend fun changeStatusInFireStore(
        id: String,
        status: String,
        statusTime: String
    ): ChangeStatusResponse

    suspend fun orderTaken(count: Int, id: String): OrderTakenResponse
    suspend fun orderDelivered(id: String): OrderDeliveredResponse
    suspend fun editOrder(
        id: String,
        phone: String,
        address: String,
        comment: String,
        count: Int,
        total: Int
    ): EditOrderResponse

    suspend fun deleteOrder(id: String): DeleteOrderResponse
}