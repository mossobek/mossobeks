package com.stirkaparus.stirkaparus.presentation.add_new_user

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.CITY
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_NAME
import com.stirkaparus.stirkaparus.common.Constants.EMAIL
import com.stirkaparus.stirkaparus.domain.repository.AddUserResponse
import com.stirkaparus.stirkaparus.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewUsrViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val prefs: SharedPreferences

) : ViewModel() {

    var addUserResponse by mutableStateOf<AddUserResponse>(Response.Success(false))

    fun addNewUser(name: String, phone: String, id: String, selected: String) =
        viewModelScope.launch {
            addUserResponse = Response.Loading
            addUserResponse =
                userRepo.addUser(
                    name = name,
                    phone = phone,
                    id = id,
                    selected = selected,
                    city = prefs.getString(CITY, "").toString(),
                    email = prefs.getString(EMAIL, "").toString(),
                    company_id = prefs.getString(COMPANY_ID, "").toString(),
                    company_name = prefs.getString(COMPANY_NAME, "").toString(),

                    )

        }


}