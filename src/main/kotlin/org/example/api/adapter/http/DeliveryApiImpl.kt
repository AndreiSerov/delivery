package org.example.api.adapter.http

import org.apache.commons.lang3.RandomStringUtils
import org.example.core.application.usecase.command.createorder.CreateOrderCommand
import org.example.core.application.usecase.command.createorder.CreateOrderHandler
import org.example.core.application.usecase.query.getallcouriers.GetAllCouriersHandler
import org.example.core.application.usecase.query.getallcouriers.GetAllCouriersQuery
import org.example.core.application.usecase.query.getnotcompletedorders.GetNotCompletedOrdersHandler
import org.example.core.application.usecase.query.getnotcompletedorders.GetNotCompletedOrdersQuery
import org.example.core.domain.sharedKernel.DomainError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID
import org.example.api.adapter.http.Error as ErrorResponse
import org.example.core.domain.courieraggregate.Courier as CourierDomain
import org.example.core.domain.orderaggregate.Order as OrderDomain
import org.example.core.domain.sharedKernel.Location as LocationDomain

@RestController
class DeliveryApiImpl(
    private val createOrderHandler: CreateOrderHandler,
    private val getAllCouriersHandler: GetAllCouriersHandler,
    private val getNotCompletedOrdersHandler: GetNotCompletedOrdersHandler
) : DeliveryApi {

    override fun createOrder(): ResponseEntity<out Any> {
        val orderId = UUID.randomUUID()
        val street = RandomStringUtils.insecure().nextPrint(20)
        return createOrderHandler
            .handle(CreateOrderCommand(orderId, street))
            .fold(
                { error -> handleError(error) },
                { ResponseEntity.created(URI.create("/orders/$orderId")).build() }
            )
    }

    override fun getCouriers(): ResponseEntity<out Any> = getAllCouriersHandler
        .handle(GetAllCouriersQuery())
            .fold(
                { error -> handleError(error) },
                { couriers -> ResponseEntity.ok(couriers.map { it.toResponse() }) }
            )

    override fun getOrders(): ResponseEntity<out Any> = getNotCompletedOrdersHandler
        .handle(GetNotCompletedOrdersQuery())
            .fold(
                { error -> handleError(error) },
                { orders -> ResponseEntity.ok(orders.map { it.toResponse() }) }
            )

    private fun handleError(error: DomainError): ResponseEntity<ErrorResponse> = ResponseEntity
        .internalServerError()
            .body(
                ErrorResponse(
                    code = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = error.context
                )
            )
}

private fun CourierDomain.toResponse() = Courier(
    id = id,
    name = name.value,
    location = location.toResponse()
)

private fun LocationDomain.toResponse(): Location = Location(x, y)

private fun OrderDomain.toResponse() = Order(
    id = id,
    location = location.toResponse()
)
