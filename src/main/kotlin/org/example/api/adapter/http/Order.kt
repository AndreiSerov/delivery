package org.example.api.adapter.http

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import org.example.api.adapter.http.Location
import jakarta.validation.constraints.DecimalMax
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import jakarta.validation.Valid
import io.swagger.v3.oas.annotations.media.Schema

/**
 * 
 * @param id Идентификатор
 * @param location 
 */
data class Order(

    @Schema(example = "null", required = true, description = "Идентификатор")
    @get:JsonProperty("id", required = true) val id: java.util.UUID,

    @field:Valid
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("location", required = true) val location: Location
    ) {

}

