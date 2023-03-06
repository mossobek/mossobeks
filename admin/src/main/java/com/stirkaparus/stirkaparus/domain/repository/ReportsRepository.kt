package com.stirkaparus.stirkaparus.domain.repository


import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import kotlinx.coroutines.flow.Flow

typealias DoReportResponse = Response<Boolean>
typealias ReportUserOrderList = List<Order>
typealias ReportOrderList = List<User>
typealias ReportOrderListResponse = Response<ReportOrderList>
typealias ReportUserOrderListResponse = Response<ReportUserOrderList>

interface ReportsRepository {

    fun getReportsOrdersFromFirestore(): Flow<ReportOrderListResponse>
    fun getReportsUserOrdersListFromFirestore(id: String): Flow<ReportUserOrderListResponse>
    suspend fun doReport(id: String): DoReportResponse

}


