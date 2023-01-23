package com.stirkaparus.stirkaparus.screens.add_screen

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.model.Order

class AddOrderScreenViewModel : ViewModel() {
val db = FirebaseFirestore.getInstance()
    fun addOrder(data: Order,
                 success:()->Unit,
                 failure:()->Unit,) {
        Log.e(TAG, "addOrder: $data", )
        db.collection(Constants.ORDERS)
            .add(data)
            .addOnSuccessListener { success() }
            .addOnFailureListener { failure() }


    }
}