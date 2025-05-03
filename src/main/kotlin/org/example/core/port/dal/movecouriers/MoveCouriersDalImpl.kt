package org.example.core.port.dal.movecouriers

import arrow.core.Either
import arrow.core.raise.either
import org.example.core.domain.mapper.OrderMapper.toDomain
import org.example.core.domain.mapper.OrderMapper.toEntity
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.orderaggregate.OrderStatus
import org.example.core.domain.sharedKernel.DomainError
import org.example.infrastructure.adapter.postgres.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
class MoveCouriersDalImpl(
    private val orderRepository: OrderRepository
) : MoveCouriersDal {
    override fun getAllAssignedOrders(): Either<DomainError, List<Order>> = either {
        orderRepository.findAllByStatus(OrderStatus.ASSIGNED.name).map {
            it.toDomain().bind()
        }
    }

    override fun saveChanges(orders: List<Order>) {
        orderRepository.saveAll(orders.map { it.toEntity() })
    }
}