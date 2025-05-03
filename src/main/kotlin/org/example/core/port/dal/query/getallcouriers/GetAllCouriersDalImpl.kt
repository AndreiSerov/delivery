package org.example.core.port.dal.query.getallcouriers

import arrow.core.Either
import arrow.core.raise.either
import org.example.core.application.usecase.query.getallcouriers.GetAllCouriersQuery
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.mapper.CourierMapper.toDomain
import org.example.core.domain.sharedKernel.DomainError
import org.example.infrastructure.adapter.postgres.repository.CourierRepository
import org.springframework.stereotype.Component

@Component
class GetAllCouriersDalImpl(
    private val courierRepository: CourierRepository
): GetAllCouriersDal {
    override fun getByQuery(query: GetAllCouriersQuery): Either<DomainError, List<Courier>>  = either {
        courierRepository.findAll().map { it.toDomain().bind() }
    }
}