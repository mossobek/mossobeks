package com.stirkaparus.driver.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.driver.common.Constants.USERS
import com.stirkaparus.driver.domain.repository.UserRepository

import com.stirkaparus.model.Response
import com.stirkaparus.model.Response.*
import com.stirkaparus.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

typealias DataInDBResponse = Response<User>


@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    firebaseFirestore: FirebaseFirestore,
 ) : UserRepository {

    private val userRef = firebaseFirestore.collection(USERS)


    override fun getUserDetails(
    )  = callbackFlow {
        val userDocumentSnapshot = userRef.document(auth.currentUser?.uid.toString())
        val snapshotListener = userDocumentSnapshot.addSnapshotListener { snapshot, e ->
            val userResponse = if (snapshot != null) {
                val user = snapshot.toObject(User::class.java)
                Success(user)
            } else {
                Failure(e)
            }
            trySend(userResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }


}