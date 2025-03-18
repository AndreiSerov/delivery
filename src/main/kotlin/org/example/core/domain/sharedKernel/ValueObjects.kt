package org.example.core.domain.sharedKernel

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

@JvmInline
value class Name private constructor(val value: String) {
    companion object {
        operator fun invoke(value: String): Either<DomainError, Name> = either {
            ensure(value.isNotEmpty()) { IllegalArgumentError("Name shouldn't be empty") }
            Name(value)
        }
    }
}