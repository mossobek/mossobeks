package com.stirkaparus.stirkaparus.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.stirkaparus.common.Constants.CITY
import com.stirkaparus.stirkaparus.common.Constants.COMPANIES
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_ID
import com.stirkaparus.stirkaparus.common.Constants.COMPANY_NAME
import com.stirkaparus.stirkaparus.common.Constants.CREATED_TIME
import com.stirkaparus.stirkaparus.common.Constants.ID
import com.stirkaparus.stirkaparus.common.Constants.PHONE
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.common.Constants.USERS
import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.common.Constants.NAME
import com.stirkaparus.stirkaparus.common.Constants.ROLE
import com.stirkaparus.stirkaparus.domain.repository.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

typealias DataInDBResponse = Response<User>


@Singleton
class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    firebaseFirestore: FirebaseFirestore,
    private val db: FirebaseFirestore

) : UserRepository {
    private val userRef = firebaseFirestore.collection(USERS)
    private val companyRef = firebaseFirestore.collection(COMPANIES)


    override fun getUserDetails(
    ) = callbackFlow {

        val userId = auth.currentUser?.uid

        val snapShotListener = userRef.document(userId!!)
            .addSnapshotListener { snapshot, e ->
                val userResponse = if (snapshot != null) {
                    val user = snapshot.toObject(User::class.java)
                    Response.Success(user)

                } else {
                    Response.Failure(e)
                }
                trySend(userResponse)
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override fun getUserListInCompany(company_id: String) = callbackFlow {

        val snapshotListener = companyRef.document(company_id).collection(USERS)
                
            .addSnapshotListener { snapshot, e ->
                val usersResponse = if (snapshot != null) {
                    val users = snapshot.toObjects(User::class.java)
                    Log.e(TAG, "getUserListInCompany: $users", )
                    Response.Success(users)
                } else {
                    Response.Failure(e)
                }
                trySend(usersResponse)
            }
        awaitClose {
            snapshotListener.remove()
        }

    }

    override suspend fun deleteUser(id: String,company_id: String): DeleteUserResponse {
        return try {
            db.runTransaction { trans ->
                trans.delete(
                    companyRef.document(company_id).collection(USERS).document(id),

                )
                trans.delete(
                    userRef.document(id),

                )
            }.await()

            Response.Success(true)


        } catch (e: Exception) {
            Log.e(TAG, "deleteUser: ${e.message}")
            Response.Failure(e)
        }
    }

    override suspend fun addUser(
        name: String,
        phone: String,
        id: String,
        selected: String,
        email: String,
        company_id: String,
        company_name: String,
        city: String
    ): AddUserResponse {
        return try {
            db.runTransaction { trans ->
                trans.set(
                    companyRef.document(company_id).collection(USERS).document(id),
                    mapOf(
                        CITY to city,
                        COMPANY_ID to company_id,
                        COMPANY_NAME to company_name,
                        CREATED_TIME to FieldValue.serverTimestamp(),
                        ID to id,
                        NAME to name,
                        PHONE to phone,
                        ROLE to selected
                    )
                )
                trans.set(
                    userRef.document(id),
                    mapOf(
                        CITY to city,
                        COMPANY_ID to company_id,
                        COMPANY_NAME to company_name,
                        CREATED_TIME to FieldValue.serverTimestamp(),
                        ID to id,
                        NAME to name,
                        PHONE to phone,
                        ROLE to selected
                    )
                )
            }.await()

            Response.Success(true)


        } catch (e: Exception) {
            Log.e(TAG, "addUserAndCompanyDetails: ${e.message}")
            Response.Failure(e)
        }


    }


}