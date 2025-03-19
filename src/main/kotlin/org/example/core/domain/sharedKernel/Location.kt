package org.example.core.domain.sharedKernel

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kotlin.math.abs

class Location private constructor(
    val x: Int,
    val y: Int
) {

    fun countStepsToOtherLocation(location: Location): Either<LocationError, Int> =
        either { abs(x - location.x) + abs(y - location.y) }

    fun takeAStep(location: Location, count: Int = 1): Location = takeAStepHelper(x, y, location, count)

    override fun toString(): String = "Location(x=$x, y=$y)"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Location

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    private tailrec fun takeAStepHelper(x: Int, y: Int, location: Location, count: Int): Location {
        if (count == 0 || (x == location.x && y == location.y)) return Location(x, y)

        return when {
            location.x > x -> takeAStepHelper(x + 1, y, location, count - 1)
            location.x < x -> takeAStepHelper(x - 1, y, location, count - 1)
            location.y > y -> takeAStepHelper(x, y + 1, location, count - 1)
            else -> takeAStepHelper(x, y - 1, location, count - 1)
        }
    }

    companion object {

        val minLocation = Location(1, 1)
        val maxLocation = Location(10, 10)

        private val range = (minLocation.x..maxLocation.x)

        operator fun invoke(x: Int, y: Int): Either<LocationError, Location> = either {
            ensure(minLocation.x <= x && x <= maxLocation.x) { LocationError("x should be in range $range") }
            ensure(minLocation.y <= y && y <= maxLocation.y) { LocationError("y should be in range $range") }

            Location(x, y)
        }

        fun random(): Either<LocationError, Location> = either {
            Location(range.random(), range.random())
        }
    }
}
