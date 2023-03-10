package com.stirkaparus.stirkaparus.domain.repository

import com.stirkaparus.model.Report
import com.stirkaparus.model.Response
import kotlinx.coroutines.flow.Flow

typealias ReportList = List<Report>
typealias AllReportsListResponse = Response<ReportList>

interface AllReportsListRepository {
    fun getAllReportsListFromFirestore(): Flow<AllReportsListResponse>
}