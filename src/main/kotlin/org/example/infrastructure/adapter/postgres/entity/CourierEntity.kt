package org.example.infrastructure.adapter.postgres.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "couriers")
class CourierEntity(
    id: UUID,

    @Column(nullable = false)
    var name: String,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "transport_id")
    var transport: TransportEntity,

    @Column(name = "location_x", nullable = false)
    var locationX: Int,

    @Column(name = "location_y", nullable = false)
    var locationY: Int,

    @Column(nullable = false)
    var status: String
) : BaseEntity<CourierEntity>(id)
