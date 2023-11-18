package com.jnta.coreapi.customer.domain.api

import com.jnta.coreapi.commom.domain.api.AuditableAbstractCommand
import com.jnta.coreapi.commom.domain.api.model.AuditEntry
import com.jnta.coreapi.commom.domain.api.model.Money
import com.jnta.coreapi.commom.domain.api.model.PersonName
import com.jnta.coreapi.customer.domain.api.model.CustomerId
import com.jnta.coreapi.customer.domain.api.model.CustomerOrderId
import jakarta.validation.Valid
import org.axonframework.modelling.command.TargetAggregateIdentifier

abstract class CustomerCommand(open val targetAggregateIdentifier: CustomerId, override val auditEntry: AuditEntry) :
    AuditableAbstractCommand(auditEntry)

abstract class CustomerOrderCommand(
    open val targetAggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry,
) : AuditableAbstractCommand(auditEntry)

data class CreateCustomerCommand(
    @TargetAggregateIdentifier override val targetAggregateIdentifier: CustomerId,
    @field:Valid val name: PersonName,
    val orderLimit: Money,
    override val auditEntry: AuditEntry,
) : CustomerCommand(targetAggregateIdentifier, auditEntry)

data class CreateCustomerOrderCommand(
    @TargetAggregateIdentifier override val targetAggregateIdentifier: CustomerId,
    val customerOrderId: CustomerOrderId,
    @field:Valid val orderTotal: Money,
    override val auditEntry: AuditEntry,
) : CustomerCommand(targetAggregateIdentifier, auditEntry) {
    constructor(targetAggregateIdentifier: CustomerId, orderTotal: Money, auditEntry: AuditEntry)
            : this(targetAggregateIdentifier, CustomerOrderId(), orderTotal, auditEntry)
}

data class MarkCustomerOrderAsDeliveredCommand(
    @TargetAggregateIdentifier override val targetAggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry,
) : CustomerOrderCommand(targetAggregateIdentifier, auditEntry)