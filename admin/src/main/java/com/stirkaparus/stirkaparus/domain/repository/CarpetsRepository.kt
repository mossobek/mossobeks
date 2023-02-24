package com.stirkaparus.stirkaparus.domain.repository

import com.stirkaparus.model.Carpet
import com.stirkaparus.model.Response


typealias AddCarpetResponse = Response<Boolean>

interface CarpetsRepository {
    suspend fun addCarpetInFirestore(carpet: Carpet):AddCarpetResponse
}