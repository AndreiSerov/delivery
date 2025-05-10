package org.example.infrastructure.adapter.config

import io.grpc.ManagedChannelBuilder
import org.example.infrastructure.adapter.grpc.GeoGrpc
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcConfiguration(
    @Value("\${config.geo.host}")
    private val host: String
) {

    @Bean
    fun geoBlockingStub(): GeoGrpc.GeoBlockingStub {
        val channel = ManagedChannelBuilder.forTarget(host).usePlaintext().build()

        return GeoGrpc.newBlockingStub(channel)
    }
}
