package org.example.core.port.dal.query.getnotcompletedorders

import arrow.core.Either
import arrow.core.raise.either
import org.example.core.application.usecase.query.getnotcompletedorders.GetNotCompletedOrdersQuery
import org.example.core.domain.mapper.OrderMapper.toDomain
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.orderaggregate.OrderStatus
import org.example.core.domain.sharedKernel.DomainError
import org.example.infrastructure.adapter.postgres.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
class GetNotCompletedOrdersDalImpl(
    private val orderRepository: OrderRepository,
) : GetNotCompletedOrdersDal {
    override fun getByQuery(query: GetNotCompletedOrdersQuery): Either<DomainError, List<Order>> =
        either {
            orderRepository.findAllByStatusNot(OrderStatus.COMPLETED.name).map { it.toDomain().bind() }
        }
}
