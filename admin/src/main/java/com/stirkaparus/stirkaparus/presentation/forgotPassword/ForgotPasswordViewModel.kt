package com.stirkaparus.stirkaparus.presentation.forgotPassword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.stirkaparus.stirkaparus.domain.repository.AuthRepository
import com.stirkaparus.stirkaparus.domain.repository.SendPasswordResetEmailResponse
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {

    var sendPasswordResetEmailResponse by mutableStateOf<SendPasswordResetEmailResponse>(
        Response.Success(
            false
        )
    )

    fun sendPasswordResetEmail(email:String) = viewModelScope.launch {
        sendPasswordResetEmailResponse = Response.Loading
        sendPasswordResetEmailResponse = repo.sendPasswordResetEmail(email)
    }
}