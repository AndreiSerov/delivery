package org.example.core.application.usecase.command.createorder

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.zonky.test.db.AutoConfigureEmbeddedDatabase
import jakarta.transaction.Transactional
import org.example.DbInitializer
import org.example.infrastructure.adapter.postgres.repository.OrderRepository
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@SpringBootTest
@ContextConfiguration(initializers = [DbInitializer::class])
@AutoConfigureEmbeddedDatabase(
    provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
    type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES,
)
@Transactional
class CreateOrderHandlerTest(
    sut: CreateOrderHandler,
    orderRepository: OrderRepository
) : FunSpec({

    afterTest { (_, _) ->
        orderRepository.deleteAll()
    }

    test("when order is not exists then create new") {
        val command = CreateOrderCommand(UUID.randomUUID(), "Lenina")
        sut.handle(command)

        val orderInDb = orderRepository.findById(command.basketId).getOrNull()
        orderInDb?.id shouldBe command.basketId
    }
})
