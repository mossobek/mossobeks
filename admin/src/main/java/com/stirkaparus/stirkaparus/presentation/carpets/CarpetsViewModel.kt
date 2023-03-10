package com.stirkaparus.stirkaparus.presentation.carpets

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.model.Carpet
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.CARPETS
import com.stirkaparus.stirkaparus.domain.repository.AddCarpetResponse
import com.stirkaparus.stirkaparus.domain.repository.CarpetsRepository
import com.stirkaparus.stirkaparus.domain.repository.OrdersRepository
import com.stirkaparus.stirkaparus.domain.repository.UpdateCarpetResponse
import com.stirkaparus.stirkaparus.presentation.order_details.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class CarpetsViewModel @Inject constructor(
    private val repo: CarpetsRepository,
    private val prefs: SharedPreferences, private val db: FirebaseFirestore
) : ViewModel() {

    var editCarpetDialog by mutableStateOf(false)
    var carpetDialog by mutableStateOf(Carpet())
    var deleteCarpetDialog by mutableStateOf(false)

    var addCarpetResponse by mutableStateOf<AddCarpetResponse>(
        Response.Success(
            false
        )
    )
    var updateCarpetResponse by mutableStateOf<UpdateCarpetResponse>(
        Response.Success(
            false
        )
    )


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

    fun addCarpet(carpet: Carpet) = viewModelScope.launch {
        val companyId = prefs.getString(Constants.COMPANY_ID, "")
        val userId = prefs.getString(Constants.ID, "")
        carpet.user = userId
        carpet.company_id = companyId

        addCarpetResponse = Response.Loading
        addCarpetResponse = repo.addCarpetInFirestore(carpet)
    }

    fun updateCarpet(oldCarpet: Carpet, carpet: Carpet) = viewModelScope.launch {
        Log.e(TAG, "updateCarpet oldCarpet: $oldCarpet")
        Log.e(TAG, "updateCarpet carpet: $carpet")
        updateCarpetResponse = Response.Loading
        updateCarpetResponse = repo.updateCarpetInFirestore(oldCarpet = oldCarpet, carpet = carpet)
    }

    fun deleteCarpet(
        orderId: String?, carpetId: String?, success: () -> Unit, failure: () -> Unit
    ) {
        db.collection(Constants.ORDERS).document(orderId.toString())
            .collection(Constants.CARPETS).document(carpetId.toString()).delete()
            .addOnSuccessListener { success() }.addOnFailureListener { failure() }
    }

    fun openEditCarpetDialog(carpet: Carpet) {
        carpetDialog = carpet

        editCarpetDialog = true
    }

    fun closeEditCarpetDialog() {
        editCarpetDialog = false
    }

    fun openDeleteCarpetDialog(carpet: Carpet) {
        deleteCarpetDialog = true
    }

    fun closeDeleteCarpetDialog() {
        deleteCarpetDialog = false
    }

}