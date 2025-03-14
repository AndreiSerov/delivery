package org.example.core.domain.sharedKernel

sealed interface DomainError

class LocationError(val message: String = "") : DomainError

