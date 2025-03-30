package org.example.core.domain.orderaggregate

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.IllegalArgumentError
import org.example.core.domain.sharedKernel.Location
import java.util.*

class Order private constructor(
    val id: UUID,
    location: Location,
    status: OrderStatus,
    courierId: UUID?
) {
    var location: Location = location
        private set
    var status: OrderStatus = status
        private set
    var courierId: UUID? = courierId
        private set

    fun assign(courierId: UUID): Either<DomainError, Order> = either {
        ensure(!status.canBeAssigned) { IllegalArgumentError("") }

        this@Order.apply {
            status = OrderStatus.ASSIGNED
            this.courierId = courierId
        }
    }

    fun complete(): Either<DomainError, Order> = either {
        ensure(status.canBeComplete) { IllegalArgumentError("") }

        this@Order.apply { status = OrderStatus.COMPLETED }
    }

    companion object {
        operator fun invoke(location: Location, id: UUID = UUID.randomUUID()): Order {
            return Order(id, location, OrderStatus.CREATED, null)
        }
    }
}
