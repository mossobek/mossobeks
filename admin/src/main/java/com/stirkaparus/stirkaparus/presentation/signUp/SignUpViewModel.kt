package com.stirkaparus.stirkaparus.presentation.signUp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.stirkaparus.domain.repository.AuthRepository
import com.stirkaparus.stirkaparus.domain.repository.SendEmailVerificationResponse
import com.stirkaparus.stirkaparus.domain.repository.SignUpResponse
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {


    var signUpResponse by mutableStateOf<SignUpResponse>(Response.Success(false))
        private set
    var sendEmailVerificationResponse by mutableStateOf<SendEmailVerificationResponse>(
        Response.Success(
            false
        )
    )
        private set

    fun signUpWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        signUpResponse = Response.Loading
        signUpResponse = repo.firebaseSignUpWithEmailAndPassword(email, password)
    }

    fun sendEmailVerification() = viewModelScope.launch {
        sendEmailVerificationResponse = Response.Loading
        sendEmailVerificationResponse = repo.sendEmailVerification()
    }
}