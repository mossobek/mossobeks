package com.stirkaparus.stirkaparus.useCases

 import com.stirkaparus.stirkaparus.domain.repository.OrdersRepository

class GetCreatedOrders(
    private val repo: OrdersRepository
) {
 //   operator fun invoke() = repo.getCreatedOrders()
}