package com.stirkaparus.stirkaparus.data.repository

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.model.Carpet
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.common.Constants.CARPETS
import com.stirkaparus.stirkaparus.common.Constants.ORDERS
import com.stirkaparus.stirkaparus.common.Constants.STATUS
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.common.Constants.TAKEN
import com.stirkaparus.stirkaparus.common.Constants.TOTAL
import com.stirkaparus.stirkaparus.common.Constants.WASHED
import com.stirkaparus.stirkaparus.common.Constants.WASHED_COUNT
import com.stirkaparus.stirkaparus.common.Constants.WASHED_TIME
import com.stirkaparus.stirkaparus.domain.repository.AddCarpetResponse
import com.stirkaparus.stirkaparus.domain.repository.CarpetsRepository
import kotlinx.coroutines.tasks.await

class CarpetsRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore,
) : CarpetsRepository {

     private val ordersRef = firebaseFirestore.collection(ORDERS)

    override suspend fun addCarpetInFirestore(carpet: Carpet): AddCarpetResponse {
        return try {
            Log.e( TAG, "addCarpet: $carpet", )

            firebaseFirestore.runTransaction { trans ->
                val orderRef = ordersRef.document(carpet.orderId.toString())
                val id = ordersRef.document(carpet.orderId.toString()).collection(CARPETS).document().id
                val carpetRef = ordersRef.document(carpet.orderId.toString()).collection(CARPETS)
                    .document(id)
                carpet.id = id
                val order = trans.get(orderRef).toObject(Order::class.java)

                Log.e( TAG, "addCarpet: $carpet", )
                Log.e( TAG, "addCarpet: $order", )
                if (order?.status != TAKEN ||
                    order.washed_count!!.toInt() >= order.count!!.toInt()
                ) return@runTransaction



                val lastCarpet: Boolean =
                    (order.washed_count!!.toInt() + 1 == order.count!!.toInt())

                if (lastCarpet) {
                    trans.update(
                        orderRef, mapOf(
                            STATUS to WASHED,
                            WASHED_TIME to FieldValue.serverTimestamp(),
                            TOTAL to order.total!!.toInt() + carpet.sum!!.toInt(),
                            WASHED_COUNT to order.washed_count!!.toInt()+1
                        )
                    )
                }else{
                    trans.update(
                        orderRef, mapOf(
                             TOTAL to order.total!!.toInt() + carpet.sum!!.toInt(),
                            WASHED_COUNT to order.washed_count!!.toInt()+1
                        )
                    )
                }
                     carpet.status = WASHED
                    carpet.washed_time = FieldValue.serverTimestamp()

                    trans.set(carpetRef, carpet)


            }
                .await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}