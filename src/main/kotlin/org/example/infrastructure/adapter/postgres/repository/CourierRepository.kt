package org.example.infrastructure.adapter.postgres.repository

import org.example.infrastructure.adapter.postgres.entity.CourierEntity
import org.example.infrastructure.adapter.postgres.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CourierRepository : JpaRepository<CourierEntity, UUID> {
    fun findAllByStatus(status: String): Collection<CourierEntity>
}