package org.example.core.application.usecase.command.movecouriers

import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import org.example.core.domain.sharedKernel.MoveCouriersError
import org.example.core.port.dal.command.movecouriers.MoveCouriersDal
import org.springframework.stereotype.Service

@Service
class MoveCouriersHandler(
    val dal: MoveCouriersDal,
) {
    fun handle(command: MoveCouriersCommand) =
        either {
            dal
                .getAllAssignedOrders()
                .bind()
                .map { order ->
                    ensureNotNull(order.courier) { MoveCouriersError("$order has not courier") }

                    order.courier?.move(order.location)
                    if (order.courier?.location == order.location) {
                        order.complete()
                        order.courier?.free()
                    }

                    order
                }.let { dal.saveChanges(it) }
        }
}
