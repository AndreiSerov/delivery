package org.example.core.domain.services

import arrow.core.raise.either
import io.kotest.assertions.fail
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.IllegalArgumentError
import org.example.core.domain.sharedKernel.Location
import java.util.UUID

class DispatchServiceTest :
    FunSpec({

        test("dispatch") {
            either {
                val destination = Location(3, 1).bind()
                val order = Order(UUID.randomUUID(), destination)
                val vasya =
                    Courier(
                        "vasya",
                        "velosiped",
                        2,
                        Location(1, 1).bind(),
                    ).bind()
                val winner =
                    Courier(
                        "winner",
                        "velosiped",
                        2,
                        Location(2, 1).bind(),
                    ).bind()

                val sut = DispatchServiceImpl()
                sut
                    .dispatch(
                        order = order,
                        couriers =
                            listOf(
                                vasya,
                                winner,
                            ),
                    ).bind()
            }.onRight {
                it.courier?.name?.value shouldBe "winner"
            }.onLeft {
                fail("Should be right. Error=${it.context}")
            }
        }

        test("dispatch with diff speed") {
            either {
                val destination = Location(3, 1).bind()
                val order = Order(UUID.randomUUID(), destination)
                val vasya =
                    Courier(
                        "vasya",
                        "velosiped",
                        2,
                        Location(1, 1).bind(),
                    ).bind()
                val winner =
                    Courier(
                        "winner",
                        "velosiped",
                        2,
                        Location(2, 1).bind(),
                    ).bind()
                val sut = DispatchServiceImpl()

                sut
                    .dispatch(
                        order = order,
                        couriers =
                            listOf(
                                vasya,
                                winner,
                            ),
                    ).bind()
            }.onRight {
                it.courier?.name?.value shouldBe "winner"
            }.onLeft {
                fail("Should be right. Error=it")
            }
        }

        test("dispatch no free couriers") {
            either {
                val destination = Location(3, 1).bind()
                val order = Order(UUID.randomUUID(), destination)
                val vasya =
                    Courier(
                        "vasya",
                        "velosiped",
                        2,
                        Location(1, 1).bind(),
                    ).bind()
                        .busy()
                        .bind()
                val winner =
                    Courier(
                        "winner",
                        "velosiped",
                        2,
                        Location(2, 1).bind(),
                    ).bind()
                        .busy()
                        .bind()
                val sut = DispatchServiceImpl()

                sut
                    .dispatch(
                        order = order,
                        couriers =
                            listOf(
                                vasya,
                                winner,
                            ),
                    ).bind()
            }.onLeft {
                it.shouldBeTypeOf<IllegalArgumentError>()
            }.onRight {
                fail("Should be left")
            }
        }
    })
