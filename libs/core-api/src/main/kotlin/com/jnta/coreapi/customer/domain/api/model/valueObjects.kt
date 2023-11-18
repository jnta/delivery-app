package com.jnta.coreapi.customer.domain.api.model

import java.io.Serializable
import java.util.*

data class CustomerId(val identifier: String) : Serializable {
    constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}

data class CustomerOrderId(val identifier: String) : Serializable {
    constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}