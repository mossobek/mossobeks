package com.stirkaparus.driver.presentation.report

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.driver.common.Constants
import com.stirkaparus.driver.domain.repository.ReportListResponse
import com.stirkaparus.driver.domain.repository.ReportsRepository
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val prefs: SharedPreferences,
    private val repo: ReportsRepository
) : ViewModel() {

    init {
        getReportOrders()

    }

    var reportListResponse by mutableStateOf<ReportListResponse>(Response.Loading)
        private set

    private fun getReportOrders() = viewModelScope.launch {
        val companyId = prefs.getString(Constants.COMPANY_ID, "")
        val userId = prefs.getString(Constants.ID, "")
        repo.getReportOrdersFromFireStore(userId, companyId).collect { response ->
            reportListResponse = response
        }
    }


}
