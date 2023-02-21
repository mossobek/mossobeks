package com.stirkaparus.driver.presentation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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
import com.stirkaparus.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
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


    fun getUserDataFromDataStore(context: Context): Boolean {
        val user = User()

        user.id = prefs.getString(ID, null)
        user.email = prefs.getString(EMAIL, null)
        user.role = prefs.getString(ROLE, null)
        user.city = prefs.getString(CITY, null)
        user.phone = prefs.getString(PHONE, null)
        user.name = prefs.getString(NAME, null)
        user.company_id = prefs.getString(COMPANY_ID, null)
        user.company_name = prefs.getString(COMPANY_NAME, null)
        Log.e(TAG, "getUserDataFromDataStore: ${user.role}")

        return             user.role == DRIVER

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
