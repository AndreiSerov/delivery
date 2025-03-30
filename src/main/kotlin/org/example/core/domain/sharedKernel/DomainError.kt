package org.example.core.domain.sharedKernel

sealed interface DomainError {
    val context: String
}

data class IllegalArgumentError(override val context: String) : DomainError

class LocationError(override val context: String) : DomainError

class TransportError(override val context: String) : DomainError

