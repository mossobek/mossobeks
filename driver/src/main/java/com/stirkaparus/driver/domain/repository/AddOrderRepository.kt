package com.stirkaparus.driver.domain.repository

import com.stirkaparus.model.Order
import com.stirkaparus.model.Response


typealias AddOrderResponse = Response<Boolean>

interface AddOrderRepository {

    suspend fun addOrderToFireStore(order: Order): AddOrderResponse

}