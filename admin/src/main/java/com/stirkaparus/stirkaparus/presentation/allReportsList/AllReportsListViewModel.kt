package com.stirkaparus.stirkaparus.presentation.allReportsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.domain.repository.AllReportsListRepository
import com.stirkaparus.stirkaparus.domain.repository.AllReportsListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllReportsListViewModel @Inject constructor(
    private val repo: AllReportsListRepository
) : ViewModel() {

    var allReportsListResponse by mutableStateOf<AllReportsListResponse>(Response.Loading)
        private set

    fun getAllReportsList() = viewModelScope.launch {
        repo.getAllReportsListFromFirestore().collect { response ->
            allReportsListResponse = response
        }
    }

}