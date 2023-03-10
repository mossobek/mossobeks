package com.stirkaparus.driver.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.driver.common.Constants.COMPANIES
import com.stirkaparus.driver.common.Constants.ORDERS
import com.stirkaparus.driver.common.Constants.USERS
import com.stirkaparus.driver.domain.repository.ReportsRepository
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ReportsRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : ReportsRepository {
    override fun getReportOrdersFromFireStore(
        userId: String?,
        companyId: String?
    ) = callbackFlow {
        if (!companyId.isNullOrBlank() && !userId.isNullOrBlank()) {
            val snapshotListener = firebaseFirestore
                .collection(COMPANIES)
                .document(companyId)
                .collection(USERS)
                .document(userId)
                .collection(ORDERS)
                .addSnapshotListener { snapshot, e ->
                    val reportOrders = if (snapshot != null) {
                        val orders = snapshot.toObjects(Order::class.java)
                        Response.Success(orders)
                    } else {
                        Response.Failure(e)
                    }
                    trySend(reportOrders)
                }
            awaitClose {
                snapshotListener.remove()
            }
        }
    }
}