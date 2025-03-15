package org.example.core.domain.courieraggregate

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.IllegalArgumentError
import org.example.core.domain.sharedKernel.Location
import java.util.UUID

data class Courier private constructor(
    val id: UUID,
    val name: String,
    val transport: Transport,
    val location: Location,
    val status: CourierStatus
) {

    fun free() = copy(status = CourierStatus.FREE)

    fun countSteps(destination: Location): Either<DomainError, Int> = either {
        val steps = location.countStepsToOtherLocation(destination).bind() / transport.speed
        steps
    }

    fun move(destination: Location): Courier {
        val newLocation = transport.oneMove(location, destination)
        return copy(location = newLocation)
    }


    companion object {
        fun create(
            name: String,
            transportName: String,
            transportSpeed: Int,
            location: Location
        ): Either<DomainError, Courier> = either {
            ensure(name.isEmpty()) { IllegalArgumentError }

            Courier(
                UUID.randomUUID(),
                name,
                Transport.create(transportName, transportSpeed).bind(),
                location,
                CourierStatus.FREE
            )
        }
    }
}