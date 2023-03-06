package com.stirkaparus.stirkaparus.data.repository

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.remember
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.stirkaparus.model.Order
import com.stirkaparus.model.Report
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.common.Constants.CARPETS
import com.stirkaparus.stirkaparus.common.Constants.COMPANIES
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED
import com.stirkaparus.stirkaparus.common.Constants.ID
import com.stirkaparus.stirkaparus.common.Constants.ORDERS
import com.stirkaparus.stirkaparus.common.Constants.REPORTS
import com.stirkaparus.stirkaparus.common.Constants.REPORT_ID
import com.stirkaparus.stirkaparus.common.Constants.STATUS
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.common.Constants.USER
import com.stirkaparus.stirkaparus.common.Constants.USERS
import com.stirkaparus.stirkaparus.common.Constants.USER_ID
import com.stirkaparus.stirkaparus.domain.repository.DoReportResponse
import com.stirkaparus.stirkaparus.domain.repository.ReportUserOrderListResponse
import com.stirkaparus.stirkaparus.domain.repository.ReportsRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.log

@Singleton
class ReportsRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val prefs: SharedPreferences
) : ReportsRepository {

    private val ordersRef = firebaseFirestore.collection(ORDERS)
    private val compOrdersRef = firebaseFirestore.collection(COMPANIES)
    private val companyId = prefs.getString(COMPANY_ID, "")
    private val userId = prefs.getString(USER_ID, "")

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

    override suspend fun doReport(id: String): DoReportResponse {


        Log.e(TAG, "doRepocvgrt: $id`")
        Log.e(TAG, "doRepocvgrt: ${companyId.toString()}")




        return try {
            val deliveredOrders =
                compOrdersRef
                    .document(companyId.toString())
                    .collection(USERS)
                    .document(id)
                    .collection(ORDERS)
                    .whereEqualTo(COMPANY_ID, companyId)
                    .whereEqualTo(USER, id)
                    .whereEqualTo(STATUS, DELIVERED)
                    .whereEqualTo(DELIVERED, true)
                    .get().await().toObjects(Order::class.java)


            firebaseFirestore.runTransaction { trans ->
                val userOrders = firebaseFirestore.collection(ORDERS)
                val reportId = firebaseFirestore
                    .collection(COMPANIES)
                    .document(companyId.toString())
                    .collection(REPORTS)
                    .document().id
                var total = 0
                for (i in deliveredOrders) {
                    total += i.total!!
                    trans.update(
                        userOrders.document(i.id.toString()), mapOf(
                            DELIVERED to true,
                            REPORT_ID to reportId
                        )
                    )
                    trans.delete(
                        firebaseFirestore
                            .collection(COMPANIES)
                            .document(companyId.toString())
                            .collection(USERS)
                            .document(id)
                            .collection(ORDERS)
                            .document(i.id.toString())
                    )
                }


                val report = Report()
                report.total = total
                report.id = reportId
                report.companyId = companyId.toString()
                report.reported_time = FieldValue.serverTimestamp()

                trans.set(
                    firebaseFirestore
                        .collection(COMPANIES)
                        .document(companyId.toString())
                        .collection(REPORTS)
                        .document(reportId),
                    report
                )
            }

            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }

    }
}