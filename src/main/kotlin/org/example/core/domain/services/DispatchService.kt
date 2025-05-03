package org.example.core.domain.services

import arrow.core.Either
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.DomainError

interface DispatchService {
    fun dispatch(order: Order, couriers: Collection<Courier>): Either<DomainError, Order>
}