package org.example.core.domain.mapper

import org.example.core.domain.courieraggregate.Transport
import org.example.infrastructure.adapter.postgres.entity.TransportEntity

object TransportMapper {
    fun Transport.toEntity() = TransportEntity(
        id = id,
        name = name,
        speed = speed,
    )

    fun TransportEntity.toDomain() = Transport(
        id = id,
        name = name,
        speed = speed,
    )
}