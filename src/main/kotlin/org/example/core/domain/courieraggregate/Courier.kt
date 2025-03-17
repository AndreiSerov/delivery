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
    private var _name: String,
    private var _transport: Transport,
    private var _location: Location,
    private var _status: CourierStatus
) {
    val name: String get() = _name
    val transport: Transport get() = _transport
    val location: Location get() = _location
    val status: CourierStatus get() = _status

    fun free(): Courier = apply { _status = CourierStatus.FREE }

    fun countSteps(destination: Location): Either<DomainError, Int> = either {
        val steps = _location.countStepsToOtherLocation(destination).bind() / _transport.speed
        steps
    }

    fun move(destination: Location): Courier = apply {
        _location = transport.oneMove(_location, destination)
        _status = if (_status == CourierStatus.FREE) CourierStatus.BUSY else CourierStatus.FREE
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
