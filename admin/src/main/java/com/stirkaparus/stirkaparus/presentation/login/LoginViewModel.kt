package com.stirkaparus.stirkaparus.presentation.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.stirkaparus.domain.repository.AuthRepository
import com.stirkaparus.stirkaparus.domain.repository.SignInResponse
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: AuthRepository,
) : ViewModel() {
    var signInResponse by mutableStateOf<SignInResponse>(Response.Success(false))
        private set

    fun signInWithEmailAndPassword(email: String, password: String, context: Context) =
        viewModelScope.launch {
            signInResponse = Response.Loading
            signInResponse = repo.firebaseSignInWithEmailAndPassword(email, password, context)
         }

}
