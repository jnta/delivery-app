package com.jnta.deliveryapp.customer.domain.api

import com.jnta.deliveryapp.commom.domain.api.AuditableAbstractEvent
import com.jnta.deliveryapp.commom.domain.api.model.AuditEntry
import com.jnta.deliveryapp.commom.domain.api.model.Money
import com.jnta.deliveryapp.commom.domain.api.model.PersonName
import com.jnta.deliveryapp.customer.domain.api.model.CustomerId
import com.jnta.deliveryapp.customer.domain.api.model.CustomerOrderId

abstract class CustomerEvent(open val aggregateIdentifier: CustomerId, override val auditEntry: AuditEntry) :
    AuditableAbstractEvent(auditEntry)

abstract class CustomerOrderEvent(open val aggregateIdentifier: CustomerOrderId, override val auditEntry: AuditEntry) :
    AuditableAbstractEvent(auditEntry)

data class CustomerCreatedEvent(
    val name: PersonName,
    val orderLimit: Money,
    override val aggregateIdentifier: CustomerId,
    override val auditEntry: AuditEntry,
) : CustomerEvent(aggregateIdentifier, auditEntry)

data class CustomerOrderCreatedEvent(
    val customerOrderId: CustomerOrderId,
    val orderTotal: Money,
    override val aggregateIdentifier: CustomerId,
    override val auditEntry: AuditEntry,
) : CustomerEvent(aggregateIdentifier, auditEntry)

data class CustomerOrderDeliveredEvent(
    override val aggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry,
) : CustomerOrderEvent(aggregateIdentifier, auditEntry)

data class CustomerOrderRejectedEvent(
    override val aggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry,
) : CustomerOrderEvent(aggregateIdentifier, auditEntry)