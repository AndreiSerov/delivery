package org.example.infrastructure.adapter.postgres.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "orders")
class OrderEntity(

    id: UUID,

    @Column(name = "location_x", nullable = false)
    var locationX: Int,

    @Column(name = "location_y", nullable = false)
    var locationY: Int,

    @Column(nullable = false)
    var status: String,

    @Column(nullable = true)
    var courierId: UUID?
) : BaseEntity<OrderEntity>(id)
