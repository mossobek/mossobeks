package com.stirkaparus.stirkaparus.presentation.reports

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.stirkaparus.common.Constants.USERS
import com.stirkaparus.stirkaparus.domain.repository.OrdersRepository
import com.stirkaparus.stirkaparus.domain.repository.ReportsOrdersResponse
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val ordersRepo: OrdersRepository,
    private val prefs: SharedPreferences

) : ViewModel() {
    companion object {
        const val TAG = "ReportsViewModel"
    }
    var selectedSpecimen by mutableStateOf(User())
    var drivers: MutableLiveData<List<User>> = MutableLiveData<List<User>>()

    var reportsOrdersResponse by mutableStateOf<ReportsOrdersResponse>(Response.Loading)
        private set


    fun getReportsOrders() = viewModelScope.launch {
        val companyId = prefs.getString(COMPANY_ID, null)
        reportsOrdersResponse = ordersRepo.getReportsOrdersFromFirestore(companyId.toString())

    }

    fun getMutableDriversList() {
        val companyId = prefs.getString(
            COMPANY_ID, null
        )
        FirebaseFirestore.getInstance().collection(USERS).whereEqualTo(
            COMPANY_ID, companyId
        ).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.e(TAG, "getMutableDriversList: $e")
                return@addSnapshotListener
            }
            snapshot?.let {
                val allDrivers = ArrayList<User>()
                allDrivers.add(User(name = "ALL ORDERS"))
                val documents = snapshot.documents
                documents.forEach {
                    val driver = it.toObject(User::class.java)
                    driver?.let {
                        allDrivers.add(it)
                    }
                }
                drivers.value = allDrivers
            }
        }
    }




}