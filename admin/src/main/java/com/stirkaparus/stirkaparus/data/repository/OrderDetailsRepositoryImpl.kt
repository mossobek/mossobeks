package com.stirkaparus.stirkaparus.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.ADDRESS
import com.stirkaparus.stirkaparus.common.Constants.COMMENT
import com.stirkaparus.stirkaparus.common.Constants.COMPANIES
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.stirkaparus.common.Constants.COUNT
import com.stirkaparus.stirkaparus.common.Constants.DELETED
import com.stirkaparus.stirkaparus.common.Constants.DELETED_TIME
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED_TIME
import com.stirkaparus.stirkaparus.common.Constants.ID
import com.stirkaparus.stirkaparus.common.Constants.ORDERS
import com.stirkaparus.stirkaparus.common.Constants.PHONE
import com.stirkaparus.stirkaparus.common.Constants.STATUS
import com.stirkaparus.stirkaparus.common.Constants.TAKEN
import com.stirkaparus.stirkaparus.common.Constants.TAKEN_TIME
import com.stirkaparus.stirkaparus.common.Constants.TOTAL
import com.stirkaparus.stirkaparus.common.Constants.USERS
import com.stirkaparus.stirkaparus.domain.repository.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class OrderDetailsRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val prefs: SharedPreferences
) : OrderDetailsRepository {
    companion object {
        const val TAG = "OrderDetailsRepositoryImpl"
    }

    private val companyID = prefs.getString(COMPANY_ID, "")
    private val orderRef = firebaseFirestore.collection(ORDERS)
    private val companyRef = firebaseFirestore.collection(COMPANIES)
    override val currentUser: FirebaseUser? get() = auth.currentUser
    override fun getOrderFromFirestore(id: String) = callbackFlow {
        val orderIdRef = orderRef.document(id)
        val snapshotListener = orderIdRef.addSnapshotListener { snapshot, e ->
            val orderResponse = if (snapshot != null) {
                val order = snapshot.toObject(Order::class.java)
                Response.Success(order)
            } else {
                Response.Failure(e)
            }
            trySend(orderResponse)

        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun changeStatusInFireStore(
        id: String, status: String, statusTime: String
    ): ChangeStatusResponse {
        return try {
            orderRef.document(id).update(
                mapOf(
                    STATUS to status, statusTime to FieldValue.serverTimestamp()
                )
            )
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }

    }

    override suspend fun orderTaken(
        count: Int, id: String
    ): OrderTakenResponse {
        return try {
            orderRef.document(id).update(
                mapOf(
                    STATUS to TAKEN, TAKEN_TIME to FieldValue.serverTimestamp(), COUNT to count
                )
            )
            Response.Success(true)

        } catch (e: Exception) {
            Response.Failure(e)
        }


    }

    override suspend fun orderDelivered(
        id: String
    ): OrderDeliveredResponse {
        return try {
            Log.e(TAG, "orderDelivered: ")
            firebaseFirestore.runTransaction { trans ->
                val order = trans.get(orderRef.document(id)).toObject(Order::class.java)
                order!!.status = DELIVERED
                order.delivered_time = FieldValue.serverTimestamp()
                order.delivered = true

                trans.update(
                    orderRef.document(id), mapOf(
                        STATUS to DELIVERED,
                        DELIVERED_TIME to FieldValue.serverTimestamp(),
                        DELIVERED to true,
                    )
                )
                if (auth.currentUser != null && !companyID.isNullOrEmpty()) {
                    val uid = auth.currentUser!!.uid

                    val userIdRef = companyRef
                        .document(companyID)
                        .collection(USERS)
                        .document(uid)
                        .collection(ORDERS)
                        .document(order.id.toString())
                    trans.set(userIdRef, order)
                } else {
                    return@runTransaction
                }

            }.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun editOrder(
        id: String,
        phone: String,
        address: String,
        comment: String,
        count: Int,
        total: Int
    ): EditOrderResponse {
        return try {
            orderRef.document(id).update(
                mapOf(
                    ID to id,
                    PHONE to phone,
                    ADDRESS to address,
                    COMMENT to comment,
                    COUNT to count,
                    TOTAL to total
                )
            )
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun deleteOrder(id: String): DeleteOrderResponse {
        return try {
            orderRef.document(id).update(
                mapOf(
                    ID to id,
                    STATUS to DELETED,
                    DELETED_TIME to FieldValue.serverTimestamp()
                )
            )
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}