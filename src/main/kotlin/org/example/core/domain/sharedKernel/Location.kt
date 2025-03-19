package org.example.core.domain.sharedKernel

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlin.math.abs

@Suppress("DataClassPrivateConstructor")
data class Location private constructor(
    val x: Int,
    val y: Int
) {

    fun countStepsToOtherLocation(location: Location): Either<LocationError, Int> =
        either { abs(x - location.x) + abs(y - location.y) }

    fun takeAStep(location: Location, count: Int = 1): Location {
        if (count == 0 || this == location) return this

        return when {
            location.x > x -> copy(x = x + 1)
            location.x < x -> copy(x = x - 1)
            location.y > y -> copy(y = y + 1)
            else -> copy(y = y - 1)
        }
            .takeAStep(location, count - 1)
    }

    companion object {

        val minLocation = Location(1, 1)
        val maxLocation = Location(10, 10)

        private val range = (minLocation.x..maxLocation.x)

        operator fun invoke(x: Int, y: Int): Either<LocationError, Location> = either {
            ensure(minLocation.x < x && x < maxLocation.x) { LocationError("x should be in range $range") }
            ensure(minLocation.y < y && y < maxLocation.y) { LocationError("y should be in range $range") }

            Location(x, y)
        }

        fun random(): Either<LocationError, Location> = either {
            Location(range.random(), range.random())
        }
    }
}
