package com.stirkaparus.driver.domain.repository

import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import kotlinx.coroutines.flow.Flow

typealias UserResponse = Response<User>
typealias CheckUserResponse = Response<User>




interface UserRepository {

    fun getUserDetails(): Flow<UserResponse>
     fun checkUser(id: String): Flow<CheckUserResponse>

}