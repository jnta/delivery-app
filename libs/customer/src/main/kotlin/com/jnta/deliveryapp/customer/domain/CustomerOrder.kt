package com.jnta.deliveryapp.customer.domain

import com.jnta.deliveryapp.commom.domain.api.model.Money
import com.jnta.deliveryapp.customer.domain.api.CreateCustomerOrderCommand
import com.jnta.deliveryapp.customer.domain.api.CustomerOrderCreatedEvent
import com.jnta.deliveryapp.customer.domain.api.CustomerOrderDeliveredEvent
import com.jnta.deliveryapp.customer.domain.api.MarkCustomerOrderAsDeliveredCommand
import com.jnta.deliveryapp.customer.domain.api.model.CustomerId
import com.jnta.deliveryapp.customer.domain.api.model.CustomerOrderId
import com.jnta.deliveryapp.customer.domain.api.model.CustomerOrderStatus
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.ToStringBuilder
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
internal class CustomerOrder {
    @AggregateIdentifier
    private lateinit var id: CustomerOrderId
    private lateinit var customerId: CustomerId
    private lateinit var status: CustomerOrderStatus
    private lateinit var orderTotal: Money

    constructor()

    constructor(command: CreateCustomerOrderCommand) {
        AggregateLifecycle.apply(
            CustomerOrderCreatedEvent(
                command.customerOrderId,
                command.orderTotal,
                command.targetAggregateIdentifier,
                command.auditEntry
            )
        )
    }

    @EventSourcingHandler
    fun on(event: CustomerOrderCreatedEvent) {
        this.id = event.customerOrderId
        this.customerId = event.aggregateIdentifier
        this.status = CustomerOrderStatus.CREATED
        this.orderTotal = event.orderTotal
    }

    @CommandHandler
    fun handle(command: MarkCustomerOrderAsDeliveredCommand) {
        if (CustomerOrderStatus.CREATED == status) {
            AggregateLifecycle.apply(CustomerOrderDeliveredEvent(command.targetAggregateIdentifier, command.auditEntry))
        } else {
            throw UnsupportedOperationException("Impossible to deliver order in status $status")
        }
    }

    @EventSourcingHandler
    fun on(event: CustomerOrderDeliveredEvent) {
        this.status = CustomerOrderStatus.DELIVERED
    }

    override fun toString(): String = ToStringBuilder.reflectionToString(this)

    override fun equals(other: Any?): Boolean = EqualsBuilder.reflectionEquals(this, other)

    override fun hashCode(): Int = HashCodeBuilder.reflectionHashCode(this)
}