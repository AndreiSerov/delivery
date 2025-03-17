package org.example.core.domain.orderaggregate

import arrow.core.raise.either
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.sharedKernel.Location

class OrderTest : FunSpec({

    test("assign") {
        either {
            val destination = Location.create(5, 5).bind()
            val order = Order.create(destination)
            val vasya = Courier.create(
                "vasya",
                "velosiped",
                2,
                Location.create(1, 1).bind()
            ).bind()

            order.assign(vasya.id)
            vasya.move(order.location)

            order.status shouldBe OrderStatus.ASSIGNED
        }
    }

    test("complete") {
        either {
            val destination = Location.create(3, 1).bind()
            val order = Order.create(destination)
            val vasya = Courier.create(
                "vasya",
                "velosiped",
                2,
                Location.create(1, 1).bind()
            ).bind()

            order.assign(vasya.id)
            vasya.move(order.location)
            order.complete(vasya.id)

            order.status shouldBe OrderStatus.COMPLETED
        }
    }
})
