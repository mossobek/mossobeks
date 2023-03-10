package com.stirkaparus.driver.presentation.add_order

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldValue
import com.stirkaparus.driver.common.Constants.COMPANY_ID
import com.stirkaparus.driver.common.Constants.CREATED
import com.stirkaparus.driver.common.Constants.ID
import com.stirkaparus.driver.common.Constants.TAKEN
import com.stirkaparus.driver.domain.repository.AddOrderInFirestoreResponse
import com.stirkaparus.driver.domain.repository.OrdersRepository
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddOrderViewModel @Inject constructor(
    private val repo: OrdersRepository,
    private val prefs:SharedPreferences
) : ViewModel() {

    var addOrderInFirestoreResponse by mutableStateOf<AddOrderInFirestoreResponse>(
        Response.Success(
            false
        )
    )


    fun addOrderInFirestore(
        order: Order

    ) = viewModelScope.launch {
        val companyId = prefs.getString(COMPANY_ID, "")
        val userId = prefs.getString(ID, "")
        if (!companyId.isNullOrBlank() || !userId.isNullOrBlank()) {

            if (order.count!! > 0) {
                order.taken_user = userId
                order.status = TAKEN
                order.taken_time = FieldValue.serverTimestamp()
            } else {
                order.status = CREATED
                order.taken_time = null
                order.taken_user = ""
            }

            order.created_time = FieldValue.serverTimestamp()
            order.company_id = companyId
            order.created_user = userId

            addOrderInFirestoreResponse = Response.Loading
            addOrderInFirestoreResponse = repo.addOrderInFirestore(
                order = order
            )
        }
    }

}