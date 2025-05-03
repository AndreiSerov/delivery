package org.example.core.port.dal.movecouriers

import arrow.core.Either
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.DomainError

interface MoveCouriersDal {
    fun getAllAssignedOrders(): Either<DomainError, List<Order>>
    fun saveChanges(orders: List<Order>)
}