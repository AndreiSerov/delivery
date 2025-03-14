package org.example.core.domain.sharedKernel

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlin.math.abs

data class Location private constructor(
    val x: Int,
    val y: Int
) {

    fun countStepsToOtherLocation(location: Location): Either<LocationError, Int> =
        either { abs(x - location.x) + abs(y - location.y) }

    companion object {

        val minLocation = Location(1, 1)
        val maxLocation = Location(10, 10)

        private val range = (minLocation.x..maxLocation.x)

        fun create(x: Int, y: Int): Either<LocationError, Location> = either {
            ensure(minLocation.x < x && x < maxLocation.x) { LocationError("x should be in range $range") }
            ensure(minLocation.y < y && y < maxLocation.y) { LocationError("y should be in range $range") }

            Location(x, y)
        }

        fun random(): Either<LocationError, Location> = either {
            Location(range.random(), range.random())
        }
    }
}
