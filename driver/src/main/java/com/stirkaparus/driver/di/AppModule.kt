package com.stirkaparus.driver.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.stirkaparus.driver.common.Constants
import com.stirkaparus.driver.data.repository.*
import com.stirkaparus.driver.domain.repository.*
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
    fun provideOrdersRef() = Firebase.firestore.collection(Constants.ORDERS)

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideOrdersRepository(
        prefs: SharedPreferences,
        firebaseFirestore: FirebaseFirestore
    ): OrdersRepository =
        OrdersRepositoryImpl(
            prefs = prefs,
            firebaseFirestore = firebaseFirestore
        )


    @Provides
    fun provideAuthRepository(): AuthRepository = AuthRepositoryImpl(
        auth = Firebase.auth
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
    )


    @Provides
    fun provideAddOrderRepository(
        ordersRef: CollectionReference,
    ): AddOrderRepository = AddOrderRepositoryImpl(
        ordersRef
    )

    @Provides
    fun provideOrderDetailsRepository(
        firebaseFirestore: FirebaseFirestore,
        prefs: SharedPreferences
    ): OrderDetailsRepository = OrderDetailsRepositoryImpl(
        firebaseFirestore = firebaseFirestore,
        prefs = prefs
    )

    @Provides
    fun provideReportsRepository(
        firebaseFirestore: FirebaseFirestore,
    ): ReportsRepository = ReportsRepositoryImpl(
        firebaseFirestore = firebaseFirestore
    )


}
