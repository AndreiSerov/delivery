package org.example.core.domain.courieraggregate

import arrow.core.raise.either
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.core.domain.sharedKernel.Location

class TransportTest : FunSpec({

    test("oneMove") {
        either {
            val car = Transport.create("car", 2).bind()
            val newLocation = car.oneMove(
                Location.create(1, 1).bind(),
                Location.create(1, 9).bind(),
            )
            newLocation.x shouldBe 1
            newLocation.y shouldBe 3
        }
    }
})
