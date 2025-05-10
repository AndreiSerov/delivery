package org.example.infrastructure.adapter.grpc

import org.springframework.stereotype.Component

@Component
class GeoClient(
    private val geoBlockingStub: GeoGrpc.GeoBlockingStub
) {

    fun findStreetLocation(street: String): Location {
        val request = GetGeolocationRequest.newBuilder()
            .setStreet(street).build()

        return geoBlockingStub.getGeolocation(request).location
    }
}