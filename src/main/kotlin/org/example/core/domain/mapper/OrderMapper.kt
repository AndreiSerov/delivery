package org.example.core.domain.mapper

import arrow.core.raise.either
import org.example.core.domain.mapper.CourierMapper.toDomain
import org.example.core.domain.mapper.CourierMapper.toEntity
import org.example.core.domain.orderaggregate.Order
import org.example.core.domain.orderaggregate.OrderStatus
import org.example.core.domain.sharedKernel.Location
import org.example.infrastructure.adapter.postgres.entity.OrderEntity

object OrderMapper {
    fun Order.toEntity() = OrderEntity(
        id = id,
        locationX = location.x,
        locationY = location.y,
        status = status.name,
        courier = courier?.toEntity(),
    )

    fun OrderEntity.toDomain() = either {
        Order(
            id = id,
            location = Location(locationX, locationY).bind(),
            status = OrderStatus.valueOf(status),
            courier = courier?.toDomain()?.bind()
        )
    }
}