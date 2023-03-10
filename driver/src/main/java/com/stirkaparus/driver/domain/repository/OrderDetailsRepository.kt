package com.stirkaparus.driver.domain.repository

import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.flow.Flow


typealias DOrderDetailsResponse = Response<Order>

interface OrderDetailsRepository {

    fun getOrderFromFirestore(id:String): Flow<DOrderDetailsResponse>


}


