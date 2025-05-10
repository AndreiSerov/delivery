package org.example.core.application.usecase.query.getallcouriers

import arrow.core.Either
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.port.dal.query.getallcouriers.GetAllCouriersDal
import org.springframework.stereotype.Service

@Service
class GetAllCouriersHandler(
    private val dal: GetAllCouriersDal,
) {
    fun handle(query: GetAllCouriersQuery): Either<DomainError, List<Courier>> = dal.getByQuery(query)
}
