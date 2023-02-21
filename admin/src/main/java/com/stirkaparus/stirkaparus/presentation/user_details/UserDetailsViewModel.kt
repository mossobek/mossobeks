package com.stirkaparus.stirkaparus.presentation.user_details

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.stirkaparus.domain.repository.UserAndCompanyDetailsResponse
import com.stirkaparus.stirkaparus.domain.repository.UserRepository
import com.stirkaparus.stirkaparus.domain.repository.UserResponse
import com.stirkaparus.model.Response
import com.stirkaparus.model.Response.Loading
import com.stirkaparus.stirkaparus.presentation.user_details.components.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    var userDetailsResponse by mutableStateOf<UserResponse>(Loading)
    var userAndCompanyDetailsResponse by mutableStateOf<UserAndCompanyDetailsResponse>(
        Response.Success(
            false
        )
    )




}