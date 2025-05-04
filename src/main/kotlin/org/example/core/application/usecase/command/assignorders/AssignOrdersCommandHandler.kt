package org.example.core.application.usecase.command.assignorders

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.core.domain.services.DispatchService
import org.example.core.domain.sharedKernel.AssignOrdersError
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.port.dal.command.assignorders.AssignOrdersDal
import org.springframework.stereotype.Service

@Service
class AssignOrdersCommandHandler(
    private val dal: AssignOrdersDal,
    private val dispatchService: DispatchService,
) {
    fun handle(command: AssignOrdersCommand): Either<DomainError, Unit> =
        either {
            val order = dal.getFirstCreatedOrder().bind() ?: raise(AssignOrdersError("No orders in CREATED status"))
            val availableCouriers = dal.getAvailableCouriers().bind()
            ensure(availableCouriers.isEmpty()) { AssignOrdersError("No free couriers") }
            val dispatchedOrder = dispatchService.dispatch(order, availableCouriers).bind()

            dal.saveChanges(dispatchedOrder)
        }
}
