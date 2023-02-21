package com.stirkaparus.stirkaparus.presentation.carpets

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.model.Carpet
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.presentation.order_details_screen.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class CarpetsViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()

    private var _orderStatus: MutableLiveData<String> = MutableLiveData()
    val orderStatus: LiveData<String> = _orderStatus


    fun fetchCarpets(id: String) = callbackFlow {
        val collection = db.collection(Constants.ORDERS).document(id).collection(Constants.CARPETS)

        val snapshotListener = collection.addSnapshotListener { value, error ->
            val response = if (error == null && value != null) {
                val data = value.documents.map { doc ->
                    doc.toObject<Carpet>().also {
                        if (it != null) {
                            it.id = doc.id
                        }
                    }
                }
                Resource.success(data)
            } else {
                Resource.error(error.toString(), null)
            }
            this.trySend(response).isSuccess
        }
        awaitClose { snapshotListener.remove() }

    }

    fun addCarpet(carpet: Carpet, success: () -> Unit, failure: () -> Unit) {
        val carpetsRef = db.collection(Constants.ORDERS).document(carpet.orderId.toString())
            .collection(Constants.CARPETS).document()

        val orderRef = db.collection(Constants.ORDERS).document(carpet.orderId.toString())


        db.runTransaction { trans ->
            Log.e(TAG, "addCarpet: run trans")

            // get washed carpet count
            val orderDocRef = trans.get(orderRef)
            val order: Order? = orderDocRef.toObject<Order>()
            Log.e(TAG, "addCarpet: $order")

            val washedCount: Int = order!!.washed_count as Int
            val count: Int = order.count as Int

            Log.e(TAG, "addCarpet: $washedCount")
            Log.e(TAG, "addCarpet: $count")
            // if washed count < count in order
            if (washedCount + 1 == count) {
                // set new last  carpet
                Log.e(TAG, "addCarpet: // set new carpet and change washed count to +1")


                trans.set(carpetsRef, carpet)
                trans.set(
                    orderRef, hashMapOf(
                        Constants.WASHED_COUNT to washedCount + 1,
                        Constants.TOTAL to (order.total!!.toInt()) + carpet.sum!!.toInt(),
                        Constants.STATUS to Constants.WASHED,
                        Constants.WASHED_TIME to FieldValue.serverTimestamp()

                    ), SetOptions.merge()
                )


            } else if (washedCount < count) {
                // if washed count = count in order +1
                Log.e(TAG, "addCarpet:// if washed count = count in order +1")

                trans.set(carpetsRef, carpet)
                trans.set(
                    orderRef, hashMapOf(
                        Constants.WASHED_COUNT to washedCount + 1,
                        Constants.TOTAL to (order.total!!.toInt()) + carpet.sum!!.toInt(),

                        ), SetOptions.merge()
                )


            } else {
                Log.e(TAG, "addCarpet: else")
                return@runTransaction
            }

        }.addOnSuccessListener { success() }.addOnFailureListener { failure() }
    }

    fun deleteCarpet(
        orderId: String?, carpetId: String?, success: () -> Unit, failure: () -> Unit
    ) {
        db.collection(Constants.ORDERS).document(orderId.toString()).collection(Constants.CARPETS)
            .document(carpetId.toString()).delete().addOnSuccessListener { success() }
            .addOnFailureListener { failure() }
    }

}