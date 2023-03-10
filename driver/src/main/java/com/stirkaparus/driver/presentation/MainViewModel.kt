package com.stirkaparus.driver.presentation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.driver.common.Constants.CITY
import com.stirkaparus.driver.common.Constants.COMPANY_ID
import com.stirkaparus.driver.common.Constants.COMPANY_NAME
import com.stirkaparus.driver.common.Constants.DRIVER
import com.stirkaparus.driver.common.Constants.EMAIL
import com.stirkaparus.driver.common.Constants.ID
import com.stirkaparus.driver.common.Constants.NAME
import com.stirkaparus.driver.common.Constants.PHONE
import com.stirkaparus.driver.common.Constants.ROLE
import com.stirkaparus.driver.common.Constants.USERS
import com.stirkaparus.driver.domain.repository.AuthRepository
import com.stirkaparus.driver.domain.repository.UserRepository
import com.stirkaparus.driver.domain.repository.VersionControlResponse
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val userRepo: UserRepository,
    private val prefs: SharedPreferences,
) : ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    init {
        getAuthState()
        versionControl()

    }

    var versionControlResponse by mutableStateOf<VersionControlResponse>(
        Response
        .Loading)
        private set


    private fun versionControl() = viewModelScope.launch {
        authRepo.versionControl().collect { response ->
            versionControlResponse = response
        }
    }
    fun getAuthState() = authRepo.getAuthState(viewModelScope)
    val isEmailVerified get() = authRepo.currentUser?.isEmailVerified ?: false


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




    fun tryGetUserDataFromFireStore(
        success: () -> Unit,
        failure: () -> Unit
    ) {
        Log.d(TAG, "tryGetUserDataFromFireStore: ")
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            FirebaseFirestore.getInstance().collection(USERS).document(uid).get()
                .addOnSuccessListener {
                    if (it.data != null) {
                        setDataInSP(it.toObject(User::class.java)!!)
                        success()
                    } else {
                        failure()
                    }
                }
        } else {
            failure()
        }
    }
}
