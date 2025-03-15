package org.example.core.domain.sharedKernel

import arrow.core.raise.either
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual
import io.kotest.matchers.types.shouldBeTypeOf


class LocationShould : FunSpec({

    test("not contain coordinate less than 1`()") {
        Location.create(11, 1).mapLeft {
            it.shouldBeTypeOf<LocationError>()
        }
    }

    test("not contain coordinate bigger than 10") {
        Location.create(1, 0).mapLeft {
            it.shouldBeTypeOf<LocationError>()
        }
    }


    test("be equal to other location with same coordinates") {
        val location1 = Location.create(1, 1)
        val location2 = Location.minLocation

        either {
            location1.bind() shouldBeEqual location2
        }
    }

    test("not be equal to other location with different x coordinate") {
        val location1 = Location.create(1, 1)
        val location2 = Location.create(5, 1)

        location1 shouldNotBeEqual location2
    }

    test("have possibility to count range from other location") {
        either {
            val location1 = Location.create(2, 6).bind()
            val location2 = Location.create(4, 9).bind()

            location1.countStepsToOtherLocation(location2).bind() shouldBeEqual 5
        }
    }

    test("have possibility to move by 3 steps") {
        either {
            val location1 = Location.create(2, 6).bind()
            val location2 = Location.create(4, 9).bind()

            val resultLocation = location1.takeAStep(location2, 3)

            resultLocation.x shouldBeEqual 4
            resultLocation.y shouldBeEqual 7
        }
    }
})