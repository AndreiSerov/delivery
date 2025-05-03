package org.example.core.port.dal.command.assignorders

import arrow.core.Either
import arrow.core.raise.either
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.courieraggregate.CourierStatus
import org.example.core.domain.mapper.CourierMapper.toDomain
import org.example.core.domain.mapper.OrderMapper.toDomain
import org.example.core.domain.mapper.OrderMapper.toEntity
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.orderaggregate.OrderStatus
import org.example.core.domain.sharedKernel.DomainError
import org.example.infrastructure.adapter.postgres.repository.CourierRepository
import org.example.infrastructure.adapter.postgres.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
class AssignOrdersDalImpl(
    private val orderRepository: OrderRepository,
    private val courierRepository: CourierRepository
) : AssignOrdersDal {

    override fun getFirstCreatedOrder(): Either<DomainError, Order?> = either {
        orderRepository.findFirstByStatus(OrderStatus.CREATED.name)?.toDomain()?.bind()
    }

    override fun getAvailableCouriers(): Either<DomainError, Collection<Courier>> = either {
        courierRepository.findAllByStatus(CourierStatus.FREE.name)
            .map { it.toDomain().bind() }
    }

    override fun saveChanges(order: Order) {
        orderRepository.save(order.toEntity())
    }
}