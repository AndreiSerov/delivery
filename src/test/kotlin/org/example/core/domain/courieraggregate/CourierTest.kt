package org.example.core.domain.courieraggregate

import arrow.core.Either
import arrow.core.left
import arrow.core.mapOrAccumulate
import arrow.core.nonEmptyListOf
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.example.core.domain.sharedKernel.Location

class CourierTest : FunSpec({

    test("count steps") {
        either {
            val vasya = Courier(
                "vasya",
                "velosiped",
                2,
                Location(1, 1).bind()
            ).bind()

            val destination = Location(5, 5).bind()

            vasya.countSteps(destination).bind() shouldBe 4
        }
    }

    test("free") {
        either {
            val vasya = Courier(
                "vasya",
                "velosiped",
                2,
                Location(1, 1).bind()
            ).bind()

            vasya.free().status shouldBe CourierStatus.FREE
        }
    }

    test("move") {
        either {
            val vasya = Courier(
                "vasya",
                "velosiped",
                2,
                Location(1, 1).bind()
            ).bind()

            val destination = Location(5, 5).bind()
            val newLocation = vasya.move(destination).location

            newLocation.x shouldBe 3
            newLocation.y shouldBe 1
        }
    }
})


data class NotEven(val i: Int)

fun Raise<NotEven>.isEven(i: Int): Int =
    i.also { ensure(i % 2 == 0) { NotEven(i) } }

fun isEven2(i: Int): Either<NotEven, Int> =
    either { isEven(i) }

val errors = nonEmptyListOf(NotEven(1), NotEven(3), NotEven(5), NotEven(7), NotEven(9)).left()

fun example() {
    (1..10).mapOrAccumulate { isEven(it) } shouldBe errors
    (1..10).mapOrAccumulate { isEven2(it).bind() } shouldBe errors
}

fun main() {
    example()
}