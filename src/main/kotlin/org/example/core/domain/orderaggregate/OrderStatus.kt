package org.example.core.domain.orderaggregate

enum class OrderStatus {
    CREATED,
    ASSIGNED,
    COMPLETED,
    ;

    val canBeAssigned: Boolean get() = this == CREATED
    val canBeComplete: Boolean get() = this == ASSIGNED
}
