package org.example.core.domain.courieraggregate

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.core.domain.sharedKernel.CourierError
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.Location
import org.example.core.domain.sharedKernel.Name
import java.util.*

class Courier private constructor(
    val id: UUID,
    name: Name,
    transport: Transport,
    location: Location,
    status: CourierStatus
) {

    var name: Name = name
        private set
    var transport: Transport = transport
        private set
    var location: Location = location
        private set
    var status: CourierStatus = status
        private set


    fun free(): Courier = apply { status = CourierStatus.FREE }
    fun busy(): Either<CourierError, Courier> = either {
        ensure(status != CourierStatus.BUSY) { CourierError("Courier id=$id already busy") }

        this@Courier.apply { status = CourierStatus.BUSY }
    }

    fun countSteps(destination: Location): Either<DomainError, Double> = either {
        val steps = location.countStepsToOtherLocation(destination).bind() / transport.speed.toDouble()
        steps
    }

    fun move(destination: Location): Courier = apply {
        location = transport.move(location, destination)
        status = if (status == CourierStatus.FREE) CourierStatus.BUSY else CourierStatus.FREE
    }

    companion object {
        operator fun invoke(
            name: String,
            transportName: String,
            transportSpeed: Int,
            location: Location
        ): Either<DomainError, Courier> = either {
            Courier(
                UUID.randomUUID(),
                Name(name).bind(),
                Transport(transportName, transportSpeed).bind(),
                location,
                CourierStatus.FREE
            )
        }
    }
}
