package com.jnta.deliveryapp.customer.domain

import com.jnta.deliveryapp.customer.domain.api.model.CustomerId
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
internal class Customer {
    private lateinit var id: CustomerId
}