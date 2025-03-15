package org.example.core.domain.orderaggregate

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.IllegalArgumentError
import org.example.core.domain.sharedKernel.Location
import java.util.*

@Suppress("DataClassPrivateConstructor")
data class Order private constructor(
    val id: UUID,
    val location: Location,
    val status: OrderStatus,
    val courierId: UUID?
) {

    fun assign(courierId: UUID): Order = copy(status = OrderStatus.ASSIGNED, courierId = courierId)

    fun complete(courierId: UUID): Either<DomainError, Order> = either {
        ensure(status == OrderStatus.ASSIGNED) { IllegalArgumentError }

        copy(status = OrderStatus.COMPLETED)
    }

    companion object {
        fun create(id: UUID, location: Location): Order {
            return Order(id, location, OrderStatus.CREATED, null)
        }
    }
}