package com.stirkaparus.driver.useCases

import com.stirkaparus.driver.domain.repository.OrdersRepository

class GetOrders(
    private val repo: OrdersRepository,
) {
    operator fun invoke(
        companyId: String
    ) = repo.getCreatedOrders(
        companyId
    )
}