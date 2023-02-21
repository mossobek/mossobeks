package com.stirkaparus.driver.useCases

import com.stirkaparus.driver.domain.repository.AddOrderRepository
import com.stirkaparus.model.Order

class AddOrder(
    private val repo: AddOrderRepository
) {

    suspend operator fun invoke(
        order: Order
    ) = repo.addOrderToFireStore(order)
}