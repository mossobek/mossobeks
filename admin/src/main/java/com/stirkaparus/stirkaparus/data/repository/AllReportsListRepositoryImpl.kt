package com.stirkaparus.stirkaparus.data.repository

import android.content.SharedPreferences
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.model.Report
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.common.Constants.REPORTS
import com.stirkaparus.stirkaparus.domain.repository.AllReportsListRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AllReportsListRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val prefs: SharedPreferences
) : AllReportsListRepository {

    override fun getAllReportsListFromFirestore() = callbackFlow {
        val companyId = prefs.getString(Constants.COMPANY_ID, "")
        val reportsRef = firebaseFirestore.collection(Constants.COMPANIES)
            .document(companyId.toString())
            .collection(REPORTS)

        val snapshotListener = reportsRef.addSnapshotListener { snapshot, e ->
            val response = if (snapshot != null) {
                val reportsList = snapshot.toObjects(Report::class.java)
                Response.Success(reportsList)
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