package com.stirkaparus.stirkaparus.presentation.profile

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.common.Constants.CITY
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_NAME
import com.stirkaparus.stirkaparus.common.Constants.EMAIL
import com.stirkaparus.stirkaparus.common.Constants.ID
import com.stirkaparus.stirkaparus.common.Constants.NAME
import com.stirkaparus.stirkaparus.common.Constants.PHONE
import com.stirkaparus.stirkaparus.common.Constants.ROLE
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.domain.repository.*

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: AuthRepository,
    private val prefs: SharedPreferences,
    private val userRepo: UserRepository,


    ) : ViewModel() {
    var revokeAccessResponse by mutableStateOf<RevokeAccessResponse>(Response.Success(false))
        private set
    var reloadUserResponse by mutableStateOf<ReloadUserResponse>(Response.Success(false))
        private set
    var deleteUserResponse by mutableStateOf<DeleteUserResponse>(Response.Success(false))
        private set
    var usersListResponse by mutableStateOf<UsersListResponse>(Response.Loading)
        private set

    fun deleteUser(id: String) = viewModelScope.launch {
        deleteUserResponse = Response.Loading
        deleteUserResponse = userRepo.deleteUser(id, prefs.getString(COMPANY_ID, "").toString())
    }


    fun getUsersInCompany() = viewModelScope.launch {
        userRepo.getUserListInCompany(prefs.getString(COMPANY_ID, "").toString())
            .collect { response ->
                usersListResponse = response
            }
    }

    fun reloadUser() = viewModelScope.launch {
        reloadUserResponse = Response.Loading
        reloadUserResponse = repo.reloadFirebaseUser()
    }

    fun user(): User {

        val user: User = User()
        user.id = prefs.getString(ID, "")
        user.name = prefs.getString(NAME, "")
        user.phone = prefs.getString(PHONE, "")
        user.email = prefs.getString(EMAIL, "")
        user.role = prefs.getString(ROLE, "")
        user.city = prefs.getString(CITY, "")
        user.company_id = prefs.getString(COMPANY_ID, "")
        user.company_name = prefs.getString(COMPANY_NAME, "")

        return user

    }

    val isEmailVerified get() = repo.currentUser?.isEmailVerified ?: false

    fun signOut() = repo.signOut()

    fun revokeAccess() = viewModelScope.launch {
        revokeAccessResponse = Response.Loading
        revokeAccessResponse = repo.revokeAccess()
    }


}