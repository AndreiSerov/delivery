package org.example.core.domain.orderaggregate

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.IllegalArgumentError
import org.example.core.domain.sharedKernel.Location
import java.util.*

class Order internal constructor(
    val id: UUID,
    location: Location,
    status: OrderStatus,
    courier: Courier? = null
) {
    var location: Location = location
        private set
    var status: OrderStatus = status
        private set
    var courier: Courier? = courier
        private set

    fun assign(courier: Courier): Either<DomainError, Order> = either {
        ensure(status.canBeAssigned) { IllegalArgumentError("") }

        this@Order.apply {
            status = OrderStatus.ASSIGNED
            this.courier = courier
        }
    }

    fun complete(): Either<DomainError, Order> = either {
        ensure(status.canBeComplete) { IllegalArgumentError("") }

        this@Order.apply { status = OrderStatus.COMPLETED }
    }

    override fun toString(): String {
        return "Order(id=$id, location=$location, status=$status)"
    }

    companion object {
        operator fun invoke(id: UUID, location: Location): Order {
            return Order(id, location, OrderStatus.CREATED)
        }
    }
}
