package org.example.core.application.usecase.command.createorder

import java.util.UUID

class CreateOrderCommand(
    val basketId: UUID,
    val street: String,
) {
    init {
        require(street.isNotBlank())
    }
}
