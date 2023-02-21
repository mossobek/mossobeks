package com.stirkaparus.stirkaparus

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.common.Constants.ADMIN
import com.stirkaparus.stirkaparus.common.Constants.CITY
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_NAME
import com.stirkaparus.stirkaparus.common.Constants.EMAIL
import com.stirkaparus.stirkaparus.common.Constants.ID
import com.stirkaparus.stirkaparus.common.Constants.NAME
import com.stirkaparus.stirkaparus.common.Constants.PHONE
import com.stirkaparus.stirkaparus.common.Constants.ROLE
import com.stirkaparus.stirkaparus.common.Constants.USERS
import com.stirkaparus.stirkaparus.domain.repository.AuthRepository
import com.stirkaparus.stirkaparus.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val userRepo: UserRepository,
    private val prefs: SharedPreferences
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

    fun getUserDataFromDataStore(): Boolean {
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

        return !user.id.isNullOrBlank() &&
                !user.email.isNullOrBlank() &&
                !user.role.isNullOrBlank() &&
                !user.city.isNullOrBlank() &&
                !user.phone.isNullOrBlank() &&
                !user.name.isNullOrBlank() &&
                !user.company_id.isNullOrBlank() &&
                !user.company_name.isNullOrBlank() &&
                user.role == ADMIN

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
                    Log.e(TAG, "tryGetUserDataFromFireStore: $it", )
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