package com.stirkaparus.stirkaparus.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.stirkaparus.common.Constants.ORDERS
import com.stirkaparus.stirkaparus.data.repository.AuthRepositoryImpl
import com.stirkaparus.stirkaparus.data.repository.OrdersRepositoryImpl
import com.stirkaparus.stirkaparus.data.repository.UserRepositoryImpl
import com.stirkaparus.stirkaparus.domain.repository.AuthRepository
import com.stirkaparus.stirkaparus.domain.repository.OrdersRepository
import com.stirkaparus.stirkaparus.domain.repository.UserRepository
import com.stirkaparus.stirkaparus.useCases.GetCreatedOrders
import com.stirkaparus.stirkaparus.useCases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideOrdersRef() = Firebase.firestore.collection(ORDERS)

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideOrdersRepository(
        ordersRef: CollectionReference,
        db: FirebaseFirestore
    ): OrdersRepository = OrdersRepositoryImpl(ordersRef,db)

    @Provides
    fun provideUseCases(
        repo: OrdersRepository
    ) = UseCases(
        getOrders = GetCreatedOrders(repo)
    )

    @Provides
    fun provideAuthRepository(): AuthRepository =
        AuthRepositoryImpl(
            auth = Firebase.auth,

            )

    @Singleton
    @Provides
    fun provideSharedPrefsRepository(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("dstirkaparus", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideUserRepository(
        firebaseFirestore: FirebaseFirestore,

        ): UserRepository = UserRepositoryImpl(
        auth = Firebase.auth,
        firebaseFirestore = firebaseFirestore,
        db = Firebase.firestore

    )
}
