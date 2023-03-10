package com.stirkaparus.stirkaparus.data.repository

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.model.Carpet
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.CARPETS
import com.stirkaparus.stirkaparus.common.Constants.ORDERS
import com.stirkaparus.stirkaparus.common.Constants.SIDE_A
import com.stirkaparus.stirkaparus.common.Constants.SIDE_B
import com.stirkaparus.stirkaparus.common.Constants.SQUARE
import com.stirkaparus.stirkaparus.common.Constants.STATUS
import com.stirkaparus.stirkaparus.common.Constants.SUM
 import com.stirkaparus.stirkaparus.common.Constants.TAKEN
import com.stirkaparus.stirkaparus.common.Constants.TOTAL
import com.stirkaparus.stirkaparus.common.Constants.WASHED
import com.stirkaparus.stirkaparus.common.Constants.WASHED_COUNT
import com.stirkaparus.stirkaparus.common.Constants.WASHED_TIME
import com.stirkaparus.stirkaparus.domain.repository.AddCarpetResponse
import com.stirkaparus.stirkaparus.domain.repository.CarpetsRepository
import com.stirkaparus.stirkaparus.domain.repository.UpdateCarpetResponse
import kotlinx.coroutines.tasks.await

class CarpetsRepositoryImpl(
    private val firebaseFirestore: FirebaseFirestore,
) : CarpetsRepository {
companion object{
    const val TAG = "CarpetsRepositoryImpl"
}
    private val ordersRef = firebaseFirestore.collection(ORDERS)

    override suspend fun addCarpetInFirestore(carpet: Carpet): AddCarpetResponse {
        return try {
            Log.e(TAG, "addCarpet: $carpet")

            firebaseFirestore.runTransaction { trans ->
                val orderRef = ordersRef.document(carpet.orderId.toString())
                val id =
                    ordersRef.document(carpet.orderId.toString()).collection(CARPETS).document().id
                val carpetRef =
                    ordersRef.document(carpet.orderId.toString()).collection(CARPETS).document(id)
                carpet.id = id
                val order = trans.get(orderRef).toObject(Order::class.java)

                Log.e(TAG, "addCarpet: $carpet")
                Log.e(TAG, "addCarpet: $order")
                if (order?.status != TAKEN || order.washed_count!!.toInt() >= order.count!!.toInt()) return@runTransaction


                val lastCarpet: Boolean =
                    (order.washed_count!!.toInt() + 1 == order.count!!.toInt())

                if (lastCarpet) {
                    trans.update(
                        orderRef, mapOf(
                            STATUS to WASHED,
                            WASHED_TIME to FieldValue.serverTimestamp(),
                            TOTAL to order.total!!.toInt() + carpet.sum!!.toInt(),
                            WASHED_COUNT to order.washed_count!!.toInt() + 1
                        )
                    )
                } else {
                    trans.update(
                        orderRef, mapOf(
                            TOTAL to order.total!!.toInt() + carpet.sum!!.toInt(),
                            WASHED_COUNT to order.washed_count!!.toInt() + 1
                        )
                    )
                }
                carpet.status = WASHED
                carpet.washed_time = FieldValue.serverTimestamp()

                trans.set(carpetRef, carpet)


            }.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun updateCarpetInFirestore(
        oldCarpet: Carpet, carpet: Carpet
    ): UpdateCarpetResponse {
        return try {
            Log.e(TAG, "updateCarpetInFirestore:   ", )

            val carpetRef = firebaseFirestore
                .collection(ORDERS)
                .document(oldCarpet.orderId.toString())
                .collection(CARPETS)
                .document(oldCarpet.id.toString())

            val carpetsInOrder = firebaseFirestore
                .collection(ORDERS)
                .document(oldCarpet.orderId.toString())
                .collection(CARPETS)
                .get()
                .await()
            Log.e(TAG, "updateCarpetInFirestore: $carpetsInOrder", )

            var totalSumInOrder = 0

            for (i in carpetsInOrder) {
                val carpetInOrder = i.toObject(Carpet::class.java)
                if (carpetInOrder.id != oldCarpet.id) {
                    totalSumInOrder += carpetInOrder.sum!!
                    Log.e(TAG, "updateCarpetInFirestore: $totalSumInOrder", )
                }
            }

            firebaseFirestore.runTransaction { trans ->
                trans.update(
                    firebaseFirestore.collection(ORDERS).document(oldCarpet.orderId.toString()),
                    mapOf(
                        TOTAL to (totalSumInOrder + carpet.sum!!)
                    )
                )
                trans.update(
                    carpetRef, mapOf(
                        SIDE_A to carpet.sideA,
                        SIDE_B to carpet.sideB,
                        SQUARE to carpet.square,
                        SUM to carpet.sum,
                    )
                )
            }.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}