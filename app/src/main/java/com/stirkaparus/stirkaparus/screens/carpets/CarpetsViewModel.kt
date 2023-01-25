package com.stirkaparus.stirkaparus.screens.carpets

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.model.Carpet

class CarpetsViewModel : ViewModel() {
    val db = FirebaseFirestore.getInstance()
    fun addCarpet(carpet:Carpet, success: () -> Unit, failure: () -> Unit) {

        db.collection(Constants.ORDERS)
            .document(carpet.orderId.toString())
            .collection(Constants.CARPETS)
            .add(
                carpet
            )
    }

}