package com.stirkaparus.stirkaparus.domain.repository

import com.stirkaparus.model.Carpet
import com.stirkaparus.model.Response


typealias AddCarpetResponse = Response<Boolean>
typealias UpdateCarpetResponse = Response<Boolean>

interface CarpetsRepository {
    suspend fun addCarpetInFirestore(carpet: Carpet):AddCarpetResponse
    suspend fun updateCarpetInFirestore(oldCarpet: Carpet,carpet: Carpet): UpdateCarpetResponse
}