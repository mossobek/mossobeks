package com.stirkaparus.stirkaparus.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.stirkaparus.common.Constants.COMPANIES
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED_TIME
import com.stirkaparus.stirkaparus.common.Constants.ORDERS
import com.stirkaparus.stirkaparus.data.repository.*
import com.stirkaparus.stirkaparus.domain.repository.*
import com.stirkaparus.stirkaparus.useCases.GetCreatedOrders
import com.stirkaparus.stirkaparus.useCases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
        db: FirebaseFirestore,
        prefs: SharedPreferences

    ): OrdersRepository = OrdersRepositoryImpl(
        ordersRef,
        db,
        prefs)

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

    @Provides
    fun provideOrderDetailsRepository(
        firebaseFirestore: FirebaseFirestore,
        prefs: SharedPreferences
    ): OrderDetailsRepository = OrderDetailsRepositoryImpl(
        firebaseFirestore = firebaseFirestore,
        auth = Firebase.auth,
        prefs = prefs
    )

    @Provides
    fun provideOrdersQuery(
        db: FirebaseFirestore
    ) = db.collection(ORDERS)
        .orderBy(DELIVERED_TIME, Query.Direction.ASCENDING)


    @Provides
    fun provideReportsRepository(
        firebaseFirestore: FirebaseFirestore,
        prefs: SharedPreferences
    ): ReportsRepository = ReportsRepositoryImpl(
        firebaseFirestore = firebaseFirestore,
        prefs = prefs
    )
    @Provides
    fun provideAllReportsListRepository(
        firebaseFirestore: FirebaseFirestore,
        prefs: SharedPreferences
    ): AllReportsListRepository = AllReportsListRepositoryImpl(
        firebaseFirestore = firebaseFirestore,
        prefs = prefs
    )

    @Provides
    fun provideCarpetsRepository(
        firebaseFirestore: FirebaseFirestore,
    ): CarpetsRepository = CarpetsRepositoryImpl(
        firebaseFirestore = firebaseFirestore
    )


}
