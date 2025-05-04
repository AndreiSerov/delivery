package org.example.infrastructure.adapter.postgres.repository

import org.example.infrastructure.adapter.postgres.entity.CourierEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CourierRepository : JpaRepository<CourierEntity, UUID> {
    fun findAllByStatus(status: String): Collection<CourierEntity>
}
