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
    private var _location: Location,
    private var _status: OrderStatus,
    private var _courierId: UUID?
) {
    val location: Location get() = _location
    val status: OrderStatus get() = _status
    val courierId: UUID? get() = _courierId

    fun assign(courierId: UUID): Order = apply {
        _status = OrderStatus.ASSIGNED
        _courierId = courierId
    }

    fun complete(courierId: UUID): Either<DomainError, Order> = either {
        ensure(_status != OrderStatus.ASSIGNED) { IllegalArgumentError }

        this@Order.apply { _status = OrderStatus.COMPLETED }
    }

    companion object {
        fun create(location: Location, id: UUID = UUID.randomUUID()): Order {
            return Order(id, location, OrderStatus.CREATED, null)
        }
    }
}