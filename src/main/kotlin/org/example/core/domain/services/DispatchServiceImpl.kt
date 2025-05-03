package org.example.core.domain.services

import arrow.core.Either
import arrow.core.raise.either
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.courieraggregate.CourierStatus
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.IllegalArgumentError

class DispatchServiceImpl : DispatchService {
    override fun dispatch(order: Order, couriers: Collection<Courier>): Either<DomainError, Courier> = either {
        val fastestCourier = couriers
            .filter { it.status == CourierStatus.FREE }
            .minByOrNull { it.countSteps(order.location).bind() }
            ?: raise(IllegalArgumentError("Invalid input: no free couriers"))

        order.assign(fastestCourier).bind()

        fastestCourier.busy().bind()
    }
}