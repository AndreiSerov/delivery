package org.example.api.adapter.http

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
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
 * @param x X
 * @param y Y
 */
data class Location(

    @Schema(example = "null", required = true, description = "X")
    @get:JsonProperty("x", required = true) val x: kotlin.Int,

    @Schema(example = "null", required = true, description = "Y")
    @get:JsonProperty("y", required = true) val y: kotlin.Int
    ) {

}

