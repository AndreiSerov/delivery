package org.example.core.port.dal.command.createorder

import arrow.core.Either
import arrow.core.raise.either
import org.example.core.domain.mapper.OrderMapper.toDomain
import org.example.core.domain.mapper.OrderMapper.toEntity
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.Location
import org.example.infrastructure.adapter.grpc.GeoClient
import org.example.infrastructure.adapter.postgres.repository.OrderRepository
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Component
class CreateOrderDalImpl(
    val orderRepository: OrderRepository,
    val geoClient: GeoClient,
) : CreateOrderDal {
    override fun getOrder(basketId: UUID): Either<DomainError, Order?> =
        either {
            val order = orderRepository.findById(basketId).getOrNull()
            order?.toDomain()?.bind()
        }

    override fun saveOrder(order: Order) {
        orderRepository.save(order.toEntity())
    }

    override fun getLocation(street: String): Either<DomainError, Location> = either {
        return geoClient.findStreetLocation(street)
            .let { Location(it.x, it.y) }
    }
}
