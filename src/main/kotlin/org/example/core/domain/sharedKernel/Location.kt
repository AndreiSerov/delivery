package org.example.core.domain.sharedKernel

import kotlin.math.abs

data class Location(
    val x: Int,
    val y: Int
) {

    init {
        validateCoordinate(x)
        validateCoordinate(y)
    }

    fun countStepsToOtherLocation(location: Location): Int = abs(x - location.x) + abs(y - location.y)

    companion object {
        private val range = (1..10)

        fun random() = Location(range.random(), range.random())

        private fun validateCoordinate(num: Int) {
            require(num in range) { "Coordinate should be in range $range" }
        }
    }
}
