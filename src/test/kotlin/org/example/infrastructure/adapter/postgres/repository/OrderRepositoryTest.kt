package org.example.infrastructure.adapter.postgres.repository

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import org.example.DbInitializer
import org.example.core.domain.mapper.OrderMapper.toEntity
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.sharedKernel.Location
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration


@SpringBootTest
@ContextConfiguration(initializers = [DbInitializer::class])
@AutoConfigureEmbeddedDatabase(
    provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
    type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES
)
class OrderRepositoryTest(
    sut: OrderRepository
) : FunSpec({

    test("save to database") {
        sut.save(Order(Location.maxLocation).toEntity())

        val all = sut.findAll()

        all.size shouldBe 1
    }
})