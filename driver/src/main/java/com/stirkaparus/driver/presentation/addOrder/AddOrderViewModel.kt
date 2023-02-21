package com.stirkaparus.driver.presentation.addOrder

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.driver.domain.repository.AddOrderInFirestoreResponse
import com.stirkaparus.driver.domain.repository.OrdersRepository
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddOrderViewModel @Inject constructor(
    private val repo: OrdersRepository
) : ViewModel() {

    var addOrderInFirestoreResponse by mutableStateOf<AddOrderInFirestoreResponse>(
        Response.Success(
            false
        )
    )

    private val db = FirebaseFirestore.getInstance()

    fun addOrderInFirestore(
        order: Order
    ) = viewModelScope.launch {
        addOrderInFirestoreResponse = Response.Loading
        addOrderInFirestoreResponse = repo.addOrderInFirestore(
            order = order
        )
    }


}