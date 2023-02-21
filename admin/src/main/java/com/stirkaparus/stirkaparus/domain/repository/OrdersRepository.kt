package com.stirkaparus.stirkaparus.domain.repository


import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.flow.Flow
typealias ReportsItems = List<Order>

typealias Orders = List<Order>
typealias OrdersResponse = Response<Orders>
typealias AddOrderInFirestoreResponse = Response<Boolean>
typealias ReportsOrdersResponse = Response<Orders>

interface OrdersRepository {

    fun getCreatedOrders(): Flow<OrdersResponse>

    suspend fun addOrderInFirestore(
        order: Order
    ): AddOrderInFirestoreResponse

    suspend fun getReportsOrdersFromFirestore(company_id:String): ReportsOrdersResponse
}


