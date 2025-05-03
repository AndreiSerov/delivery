package org.example.core.port.dal.assignorders

import arrow.core.Either
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.DomainError

interface AssignOrdersDal {
    fun getFirstCreatedOrder(): Either<DomainError, Order?>
    fun getAvailableCouriers(): Either<DomainError, Collection<Courier>>
    fun saveChanges(order: Order)
}
