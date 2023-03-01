package com.stirkaparus.stirkaparus.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.common.Constants.COMPANIES
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.stirkaparus.common.Constants.ORDERS
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.common.Constants.USER
import com.stirkaparus.stirkaparus.common.Constants.USERS
import com.stirkaparus.stirkaparus.domain.repository.ReportUserOrderListResponse
import com.stirkaparus.stirkaparus.domain.repository.ReportsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportsRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val prefs: SharedPreferences
) : ReportsRepository {
    override fun getReportsOrdersFromFirestore() = callbackFlow {
        val companyId = prefs.getString(COMPANY_ID, "")
        val usersRef = firebaseFirestore.collection(COMPANIES)
            .document(companyId.toString())
            .collection(USERS)

        val snapshotListener = usersRef.addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val orderList = snapshot.toObjects(User::class.java)
                Log.e(TAG, "getReportsOrdersFromFirestore: $orderList")
                Response.Success(orderList)
            } else {
                Response.Failure(e)
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }

    }

    override fun getReportsUserOrdersListFromFirestore(id: String) = callbackFlow {

        val companyId = prefs.getString(COMPANY_ID, "")
        val ordersRef = firebaseFirestore
            .collection(COMPANIES)
            .document(companyId.toString())
            .collection(USERS)
            .document(id)
            .collection(ORDERS)

        val snapshotListener = ordersRef.addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val orderList = snapshot.toObjects(Order::class.java)
                Response.Success(orderList)
            } else {
                Response.Failure(e)
            }
            trySend(response).isSuccess
        }
        awaitClose {
            snapshotListener.remove()
        }
    }
}