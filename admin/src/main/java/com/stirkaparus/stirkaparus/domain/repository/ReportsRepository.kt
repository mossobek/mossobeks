package com.stirkaparus.stirkaparus.domain.repository


import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.flow.Flow
typealias ReportOrderList = List<Order>
typealias ReportOrderListResponse = Response<ReportOrderList>
interface ReportsRepository {

 fun getReportsOrdersFromFirestore(userId: String):Flow<ReportOrderListResponse>
}


