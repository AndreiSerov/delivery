package org.example.core.port.dal.command.createorder

import arrow.core.Either
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.Location
import java.util.UUID

interface CreateOrderDal {
    fun getOrder(basketId: UUID): Either<DomainError, Order?>

    fun saveOrder(order: Order)

    fun getLocation(street: String): Either<DomainError, Location>
}
