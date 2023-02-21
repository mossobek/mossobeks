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
import com.stirkaparus.driver.common.dataStore
import com.stirkaparus.driver.data.repository.AddOrderRepositoryImpl
import com.stirkaparus.driver.data.repository.AuthRepositoryImpl
import com.stirkaparus.driver.data.repository.OrdersRepositoryImpl
import com.stirkaparus.driver.data.repository.UserRepositoryImpl
import com.stirkaparus.driver.domain.repository.*
import com.stirkaparus.driver.useCases.GetOrders
import com.stirkaparus.driver.useCases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    // just my preference of naming including the package name
    name = "stirkaparus.driver"
)

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideOrdersRef() = Firebase.firestore.collection(Constants.ORDERS)

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    fun provideOrdersRepository(
        ordersRef: CollectionReference,
        db: FirebaseFirestore
    ): OrdersRepository = OrdersRepositoryImpl(ordersRef)

    @Provides
    fun provideUseCases(
        repo: OrdersRepository,
    ) = UseCases(
        getOrders = GetOrders(repo)
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





}
