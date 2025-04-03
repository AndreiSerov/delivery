package org.example.core.domain.courieraggregate

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.Location
import org.example.core.domain.sharedKernel.Name
import org.example.core.domain.sharedKernel.TransportError
import java.util.*

class Transport private constructor(
    val id: UUID,
    val name: Name,
    val speed: Int
) {

    companion object {
        operator fun invoke(name: String, speed: Int, id: UUID = UUID.randomUUID()): Either<DomainError, Transport> = either {
            ensure(speed in 1..3) { TransportError("Speed out of range (1..3)") }

            Transport(
                id,
                Name(name).bind(),
                speed
            )
        }
    }

    fun move(startLocation: Location, location: Location): Location = startLocation.takeAStep(location, speed)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Transport

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
