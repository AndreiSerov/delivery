package org.example.core.domain.sharedKernel

sealed interface DomainError

data object IllegalArgumentError : DomainError

class LocationError(val message: String = "") : DomainError

class TransportError(val message: String = "") : DomainError

