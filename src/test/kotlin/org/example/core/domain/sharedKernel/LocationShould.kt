package org.example.core.domain.sharedKernel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.assertThrows

class LocationShould {

    @Test
    fun `not contain coordinate less than 1`() {
        assertThrows<IllegalArgumentException> { Location(11, 1) }
    }

    @Test
    fun `not contain coordinate bigger than 10`() {
        assertThrows<IllegalArgumentException> { Location(1, 0) }
    }

    @Test
    fun `be equal to other location with same coordinates`() {
        val location1 = Location(1, 1)
        val location2 = Location(1, 1)

        assertTrue(location1 == location2)
    }

    @Test
    fun `not be equal to other location with different x coordinate`() {
        val location1 = Location(1, 1)
        val location2 = Location(5, 1)

        assertTrue(location1 != location2)
    }

    @Test
    fun `have possibility to count range from other location`() {
        val location1 = Location(2, 6)
        val location2 = Location(4, 9)

        assertEquals(5, location1.countStepsToOtherLocation(location2))
    }
}