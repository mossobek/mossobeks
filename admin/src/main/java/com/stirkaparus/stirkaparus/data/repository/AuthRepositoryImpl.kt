package com.stirkaparus.stirkaparus.data.repository

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.stirkaparus.model.Response
import com.stirkaparus.model.Response.*
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.domain.repository.*
 import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository {

    override val currentUser: FirebaseUser? get() = auth.currentUser


    override suspend fun firebaseSignUpWithEmailAndPassword(
        email: String, password: String
    ): SignInResponse {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
              Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }


    override suspend fun sendEmailVerification(): SendEmailVerificationResponse {
        return try {
            auth.currentUser?.sendEmailVerification()?.await()
            Success(true)
        } catch (e: Exception) {
            Failure(e)
        }
    }

    override suspend fun firebaseSignInWithEmailAndPassword(
        email: String, password: String, context: Context
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
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)

        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

    override suspend fun checkUserData(context: Context): SignInResponse {
        return try {
            val userId = auth.currentUser?.uid
            FirebaseFirestore.getInstance().collection(Constants.USERS).document(userId!!).get()
                .addOnSuccessListener {
                    val user = it.toObject(User::class.java)
                    if (user != null) {
                        Success(true)
                        //SharedPrefsRepository(context).setDataInSP(user)

                    }
                }
            Success(true)

        } catch (e: Exception) {
            Failure(e)

        }

    }
}