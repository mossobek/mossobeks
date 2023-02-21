package com.stirkaparus.stirkaparus.domain.repository

import com.stirkaparus.model.Response
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.data.repository.DataInDBResponse
import kotlinx.coroutines.flow.Flow

typealias UserResponse = Response<User>
typealias UserAndCompanyDetailsResponse = Response<Boolean>
typealias AddUserResponse = Response<Boolean>
typealias UserItems = List<User>
typealias UsersListResponse = Response<UserItems>


interface UserRepository {


    suspend fun addUser(
        name: String,
        phone: String,
        id: String,
        selected: String,
        email: String,
        company_id: String,
        company_name: String,
        city: String
    ): AddUserResponse

    fun getUserDetails(): Flow<DataInDBResponse>

    fun getUserListInCompany(company_id: String): Flow<UsersListResponse>

    suspend fun deleteUser(id: String,company_id: String): DeleteUserResponse
}