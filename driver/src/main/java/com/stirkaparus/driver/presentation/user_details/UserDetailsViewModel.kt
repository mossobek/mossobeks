package com.stirkaparus.driver.presentation.user_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.stirkaparus.driver.domain.repository.UserRepository
import com.stirkaparus.driver.domain.repository.UserResponse
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {

    var userDetailsResponse by mutableStateOf<UserResponse>(Response.Loading)



}