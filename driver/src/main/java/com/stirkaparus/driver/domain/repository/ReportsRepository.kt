package com.stirkaparus.driver.domain.repository

import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.flow.Flow


typealias ReportsOrders = List<Order>
typealias ReportListResponse = Response<ReportsOrders>

interface ReportsRepository {
    fun getReportOrdersFromFireStore(userId: String?, companyId: String?): Flow<ReportListResponse>
}