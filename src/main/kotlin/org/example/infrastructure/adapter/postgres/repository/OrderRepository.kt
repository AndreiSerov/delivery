package org.example.infrastructure.adapter.postgres.repository

import org.example.infrastructure.adapter.postgres.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface OrderRepository : JpaRepository<OrderEntity, UUID> {
    fun findAllByStatus(status: String): Collection<OrderEntity>
    fun findFirstByStatus(status: String): OrderEntity?
}