package com.stirkaparus.driver.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
 import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.driver.common.Constants.SETTING
import com.stirkaparus.driver.common.Constants.VERSION_CONTROL
import com.stirkaparus.driver.domain.repository.*

import com.stirkaparus.model.Response
import com.stirkaparus.model.Response.*
import com.stirkaparus.model.Version
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser? get() = auth.currentUser


    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String
    ): SignInResponse {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            Response.Success(true)
        }
        catch (e: Exception) {
             Failure(e)
         }
    }



    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String,
        password: String
    ): SignInResponse {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }

    }


    override suspend fun reloadFirebaseUser(): ReloadUserResponse {
        return try {
            auth.currentUser?.reload()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): SendPasswordResetEmailResponse {
        return try {
            auth.sendPasswordResetEmail(email).await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override fun signOut() = auth.signOut()

    override suspend fun revokeAccess(): RevokeAccessResponse {
        return try {
            auth.currentUser?.delete()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }





    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser == null)

        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

    override fun versionControl() = callbackFlow {
        val snapshotListener = FirebaseFirestore
            .getInstance()
            .collection(SETTING)
            .document(VERSION_CONTROL)
            .addSnapshotListener { snapshot, e ->
                val versionResponse = if (snapshot != null) {
                    val vControl = snapshot.toObject(Version::class.java)
                    Response.Success(vControl)
                } else {
                    Response.Failure(e)
                }
                trySend(versionResponse)

            }
        awaitClose {
            snapshotListener.remove()
        }


    }
}