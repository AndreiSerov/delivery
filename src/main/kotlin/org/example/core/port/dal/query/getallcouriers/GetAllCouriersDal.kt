package org.example.core.port.dal.query.getallcouriers

import arrow.core.Either
import org.example.core.application.usecase.query.getallcouriers.GetAllCouriersQuery
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.sharedKernel.DomainError

interface GetAllCouriersDal {
    fun getByQuery(query: GetAllCouriersQuery): Either<DomainError, List<Courier>>
}
