package org.example.core.domain.services

import arrow.core.Either
import arrow.core.raise.either
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.courieraggregate.CourierStatus
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.IllegalArgumentError
import org.springframework.stereotype.Service

@Service
class DispatchServiceImpl : DispatchService {
    override fun dispatch(
        order: Order,
        couriers: Collection<Courier>,
    ): Either<DomainError, Order> =
        either {
            val fastestCourier =
                couriers
                    .filter { it.status == CourierStatus.FREE }
                    .minByOrNull { it.countSteps(order.location).bind() }
                    ?: raise(IllegalArgumentError("Invalid input: no free couriers"))

            fastestCourier.busy().bind()
            order.assign(fastestCourier).bind()
        }
}
