package org.example.infrastructure.adapter.postgres.repository

import arrow.core.raise.either
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import jakarta.transaction.Transactional
import org.example.DbInitializer
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.mapper.OrderMapper.toEntity
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.orderaggregate.OrderStatus
import org.example.core.domain.sharedKernel.Location
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import java.util.UUID

@SpringBootTest
@ContextConfiguration(initializers = [DbInitializer::class])
@AutoConfigureEmbeddedDatabase(
    provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
    type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES,
)
@Transactional
class OrderRepositoryTest(
    sut: OrderRepository,
) : FunSpec({

        afterTest { (_, _) ->
            sut.deleteAll()
        }

        test("save to database") {
            sut.save(Order(UUID.randomUUID(), Location.maxLocation).toEntity())

            val all = sut.findAll()

            all.size shouldBe 1
        }

        test("find all by status") {
            either {
                val destination = Location(5, 5).bind()
                val order = Order(UUID.randomUUID(), destination)
                val vasya =
                    Courier(
                        "vasya",
                        "velosiped",
                        2,
                        Location(1, 1).bind(),
                    ).bind()

                order.assign(vasya)

                sut.save(order.toEntity())

                val all = sut.findAll()
                val assignedOrders = sut.findAllByStatus(OrderStatus.ASSIGNED.name)
                val createdOrders = sut.findAllByStatus(OrderStatus.CREATED.name)

                all.size shouldBe 1
                assignedOrders.size shouldBe 1
                createdOrders.size shouldBe 0
            }
        }
    })
