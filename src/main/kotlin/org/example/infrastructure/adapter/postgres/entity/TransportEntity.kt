package org.example.infrastructure.adapter.postgres.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.example.core.domain.sharedKernel.Name
import java.util.*

@Entity
@Table(name = "transports")
class TransportEntity(
    id: UUID,

    @Column(nullable = false)
    var name: Name,

    @Column(nullable = false)
    var speed: Int,
) : BaseEntity<OrderEntity>(id)
