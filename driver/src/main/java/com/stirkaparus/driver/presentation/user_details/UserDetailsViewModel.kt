package com.stirkaparus.driver.presentation.user_details

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.driver.common.Constants
import com.stirkaparus.driver.domain.repository.AuthRepository
import com.stirkaparus.driver.domain.repository.CheckUserResponse
import com.stirkaparus.driver.domain.repository.UserRepository
import com.stirkaparus.driver.domain.repository.UserResponse
import com.stirkaparus.driver.presentation.MainViewModel
import com.stirkaparus.driver.presentation.TAG
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val repo: UserRepository,
    private val prefs: SharedPreferences,

    ) : ViewModel() {


    var userDetailsResponse by mutableStateOf<UserResponse>(Response.Loading)
        private set
    var checkUserResponse by mutableStateOf<CheckUserResponse>(Response.Loading)
        private set

    fun checkUser() = viewModelScope.launch {
        val id = authRepo.currentUser?.uid
        if (!id.isNullOrBlank()) {
            repo.checkUser(id).collect { response ->
                checkUserResponse = response
            }
        }
    }

    fun getUserDataFromDataStore(): Boolean {
        val user = User()

        user.id = prefs.getString(Constants.ID, null)
        user.email = prefs.getString(Constants.EMAIL, null)
        user.role = prefs.getString(Constants.ROLE, null)
        user.city = prefs.getString(Constants.CITY, null)
        user.phone = prefs.getString(Constants.PHONE, null)
        user.name = prefs.getString(Constants.NAME, null)
        user.company_id = prefs.getString(Constants.COMPANY_ID, null)
        user.company_name = prefs.getString(Constants.COMPANY_NAME, null)
        Log.e(MainViewModel.TAG, "getUserDataFromDataStore: ${user.role}")

        return !user.id.isNullOrBlank() &&
                !user.email.isNullOrBlank() &&
                !user.role.isNullOrBlank() &&
                !user.city.isNullOrBlank() &&
                !user.phone.isNullOrBlank() &&
                !user.name.isNullOrBlank() &&
                !user.company_id.isNullOrBlank() &&
                !user.company_name.isNullOrBlank() &&
                user.role == Constants.DRIVER

    }

    fun getUserData(): User {
        val user = User()

        user.id = prefs.getString(Constants.ID, null)
        user.email = prefs.getString(Constants.EMAIL, null)
        user.role = prefs.getString(Constants.ROLE, null)
        user.city = prefs.getString(Constants.CITY, null)
        user.phone = prefs.getString(Constants.PHONE, null)
        user.name = prefs.getString(Constants.NAME, null)
        user.company_id = prefs.getString(Constants.COMPANY_ID, null)
        user.company_name = prefs.getString(Constants.COMPANY_NAME, null)
        Log.e(MainViewModel.TAG, "getUserDataFromDataStore: ${user.role}")

        return user

    }


    fun tryGetUserDataFromFireStore() = viewModelScope.launch {
        Log.d(TAG, "tryGetUserDataFromFireStore: ")
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            repo.getUserDetails().collect { response ->
                userDetailsResponse = response
            }
        }
    }

    fun setDataInSP(user: User) {
        val editor = prefs.edit()
        editor.putString("company_id", user.company_id)
        editor.putString("company_name", user.company_name)
        editor.putString("email", authRepo.currentUser?.email)
        editor.putString("name", user.name)
        editor.putString("id", user.id)
        editor.putString("phone", user.phone)
        editor.putString("role", user.role)
        editor.putString("city", user.city)
        editor.commit()
    }

    fun signOut() {
        prefs.edit().clear().commit()
        authRepo.signOut()
    }
}