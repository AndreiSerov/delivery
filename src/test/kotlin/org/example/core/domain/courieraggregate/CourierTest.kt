package org.example.core.domain.courieraggregate

import arrow.core.raise.either
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.core.domain.sharedKernel.Location

class CourierTest : FunSpec({

    test("count steps") {
        either {
            val vasya = Courier.create(
                "vasya",
                "velosiped",
                2,
                Location.create(1, 1).bind()
            ).bind()

            val destination = Location.create(5, 5).bind()

            vasya.countSteps(destination).bind() shouldBe 4
        }
    }

    test("free") {
        either {
            val vasya = Courier.create(
                "vasya",
                "velosiped",
                2,
                Location.create(1, 1).bind()
            ).bind()

            vasya.free().status shouldBe CourierStatus.FREE
        }
    }

    test("move") {
        either {
            val vasya = Courier.create(
                "vasya",
                "velosiped",
                2,
                Location.create(1, 1).bind()
            ).bind()

            val destination = Location.create(5, 5).bind()
            val newLocation = vasya.move(destination).location

            newLocation.x shouldBe 3
            newLocation.y shouldBe 1
        }
    }
})
