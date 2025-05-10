package org.example.core.application.usecase.query.getnotcompletedorders

import arrow.core.Either
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.port.dal.query.getnotcompletedorders.GetNotCompletedOrdersDal
import org.springframework.stereotype.Service

@Service
class GetNotCompletedOrdersHandler(
    private val dal: GetNotCompletedOrdersDal,
) {
    fun handle(query: GetNotCompletedOrdersQuery): Either<DomainError, List<Order>> = dal.getByQuery(query)
}
