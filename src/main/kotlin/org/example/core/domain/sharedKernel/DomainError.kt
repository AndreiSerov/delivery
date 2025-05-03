package org.example.core.domain.sharedKernel

sealed interface DomainError {
    val context: String
}

data class IllegalArgumentError(override val context: String) : DomainError

data class CourierError(override val context: String) : DomainError

data class DispatchError(override val context: String) : DomainError

data class LocationError(override val context: String) : DomainError

data class TransportError(override val context: String) : DomainError

data class MoveCouriersError(override val context: String) : DomainError

data class AssignOrdersError(override val context: String) : DomainError
