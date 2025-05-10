package org.example.core.port.dal.query.getnotcompletedorders

import arrow.core.Either
import org.example.core.application.usecase.query.getnotcompletedorders.GetNotCompletedOrdersQuery
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.DomainError

interface GetNotCompletedOrdersDal {
    fun getByQuery(query: GetNotCompletedOrdersQuery): Either<DomainError, List<Order>>
}
