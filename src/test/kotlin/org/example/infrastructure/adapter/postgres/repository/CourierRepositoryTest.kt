package org.example.infrastructure.adapter.postgres.repository

import arrow.core.raise.either
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import jakarta.transaction.Transactional
import org.example.DbInitializer
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.mapper.CourierMapper.toEntity
import org.example.core.domain.sharedKernel.Location
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration


@SpringBootTest
@ContextConfiguration(initializers = [DbInitializer::class])
@AutoConfigureEmbeddedDatabase(
    provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
    type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES
)
@Transactional
class CourierRepositoryTest(
    sut: CourierRepository,
    transportRepository: TransportRepository
) : FunSpec({

    test("save to database") {
        either {
            val vasya = Courier(
                "vasya",
                "velosiped",
                2,
                Location(1, 1).bind()
            ).bind()

            sut.save(vasya.toEntity())

            val couriers = sut.findAll()

            couriers.size shouldBe 1

            val transport = couriers.first().transport
            transport shouldNotBe null
            transport.id shouldBeEqual vasya.transport.id
        }
    }
})