package org.example.core.domain.courieraggregate

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import org.example.core.domain.sharedKernel.DomainError
import org.example.core.domain.sharedKernel.Location
import org.example.core.domain.sharedKernel.TransportError
import java.util.UUID

@Suppress("DataClassPrivateConstructor")
data class Transport private constructor(
    val id: UUID,
    val name: String,
    val speed: Int
) {

    companion object {
        fun create(name: String, speed: Int, id: UUID = UUID.randomUUID()): Either<DomainError, Transport> = either {
            ensure(name.isEmpty()) { TransportError() }
            ensure(speed in 1..3) { TransportError() }

            Transport(
                id,
                name,
                speed
            )
        }
    }

    fun oneMove(current: Location, destination: Location): Location = current.takeAStep(destination, speed)

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
