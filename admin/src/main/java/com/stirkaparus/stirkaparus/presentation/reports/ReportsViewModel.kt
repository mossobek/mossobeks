package com.stirkaparus.stirkaparus.presentation.reports

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.domain.repository.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val repo: ReportsRepository,
    private val prefs: SharedPreferences

) : ViewModel() {
    companion object {
        const val TAG = "ReportsViewModel"
    }

    var userReportDialog by mutableStateOf(false)

    var reportsOrdersResponse by mutableStateOf<ReportsOrdersResponse>(Response.Loading)
        private set
    var doReportResponse by mutableStateOf<DoReportResponse>(Response.Loading)
        private set

    var reportOrderListResponse by mutableStateOf<ReportOrderListResponse>(Response.Loading)
        private set
    var reportUserOrdersListResponse by mutableStateOf<ReportUserOrderListResponse>(Response.Loading)
        private set

    fun getUserList() = viewModelScope.launch {
        repo.getReportsOrdersFromFirestore().collect { response ->
            reportOrderListResponse = response
        }
    }


    fun reportUserOrdersList(id: String) = viewModelScope.launch {
        repo.getReportsUserOrdersListFromFirestore(id).collect { response ->
            reportUserOrdersListResponse = response
        }
    }

    fun openUserReportDialog() {
        userReportDialog = true
    }

    fun closeUserReportDialog() {
        userReportDialog = false
    }

    fun doReport(id: String)  = viewModelScope.launch{
        doReportResponse = Response.Loading
        doReportResponse = repo.doReport(id)
    }


}