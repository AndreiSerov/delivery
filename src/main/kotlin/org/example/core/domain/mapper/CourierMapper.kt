package org.example.core.domain.mapper

import arrow.core.raise.either
import org.example.core.domain.courieraggregate.Courier
import org.example.core.domain.courieraggregate.CourierStatus
import org.example.core.domain.mapper.TransportMapper.toDomain
import org.example.core.domain.mapper.TransportMapper.toEntity
import org.example.core.domain.sharedKernel.Location
import org.example.core.domain.sharedKernel.Name
import org.example.infrastructure.adapter.postgres.entity.CourierEntity
import org.example.infrastructure.adapter.postgres.entity.TransportEntity

object CourierMapper {
    fun Courier.toEntity() = CourierEntity(
        id = id,
        name = name.value,
        transport = transport.toEntity(),
        locationX = location.x,
        locationY = location.y,
        status = status.name,
    )

    fun CourierEntity.toDomain(transportEntity: TransportEntity) = either {
        Courier(
            id = id,
            name = Name(name).bind(),
            transport = transportEntity.toDomain(),
            location = Location(locationX, locationY).bind(),
            status = CourierStatus.valueOf(status),
        )
    }
}