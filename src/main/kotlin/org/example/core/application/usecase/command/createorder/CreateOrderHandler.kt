package org.example.core.application.usecase.command.createorder

import arrow.core.raise.either
import org.example.core.domain.orderaggregate.Order
import org.example.core.port.dal.command.createorder.CreateOrderDal
import org.springframework.stereotype.Service

@Service
class CreateOrderHandler(
    val dal: CreateOrderDal,
) {
    fun handle(command: CreateOrderCommand) = either {
        dal.getOrder(command.basketId).bind()?.let { return@either }

        val location = dal.getLocation(command.street).bind()
        val order = Order(command.basketId, location)

        dal.saveOrder(order)
    }
}
