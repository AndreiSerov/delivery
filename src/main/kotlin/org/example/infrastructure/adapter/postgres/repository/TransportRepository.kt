package org.example.infrastructure.adapter.postgres.repository

import org.example.infrastructure.adapter.postgres.entity.TransportEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TransportRepository : JpaRepository<TransportEntity, UUID>