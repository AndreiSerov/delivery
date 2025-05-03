package org.example.core.domain.orderaggregate

import arrow.core.raise.either
import io.kotest.assertions.fail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.sharedKernel.Location
import java.util.UUID

class OrderTest : FunSpec({

    test("assign") {
        either {
            val destination = Location(5, 5).bind()
            val order = Order(UUID.randomUUID(), destination)
            val vasya = Courier(
                "vasya",
                "velosiped",
                2,
                Location(1, 1).bind()
            ).bind()

            order.assign(vasya)
            vasya.move(order.location)

            order
        }.onRight {
            it.status shouldBe OrderStatus.ASSIGNED
        }.onLeft {
            fail("Should be right. Error=$it")
        }
    }

    test("complete") {
        either {
            val destination = Location(3, 1).bind()
            val order = Order(UUID.randomUUID(), destination)
            val vasya = Courier(
                "vasya",
                "velosiped",
                2,
                Location(1, 1).bind()
            ).bind()

            order.assign(vasya)
            vasya.move(order.location)
            order.complete()

            order
        }.onRight {
            it.status shouldBe OrderStatus.COMPLETED
        }.onLeft {
            fail("Should be right. Error=$it")
        }
    }
})
