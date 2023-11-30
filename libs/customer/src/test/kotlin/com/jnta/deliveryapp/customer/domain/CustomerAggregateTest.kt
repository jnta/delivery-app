package com.jnta.deliveryapp.customer.domain

import com.jnta.deliveryapp.commom.domain.api.model.AuditEntry
import com.jnta.deliveryapp.commom.domain.api.model.Money
import com.jnta.deliveryapp.commom.domain.api.model.PersonName
import com.jnta.deliveryapp.customer.domain.api.CreateCustomerCommand
import com.jnta.deliveryapp.customer.domain.api.CreateCustomerOrderCommand
import com.jnta.deliveryapp.customer.domain.api.CustomerCreatedEvent
import com.jnta.deliveryapp.customer.domain.api.CustomerOrderCreatedEvent
import com.jnta.deliveryapp.customer.domain.api.model.CustomerId
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant
import java.util.*

class CustomerAggregateTest {
    private lateinit var fixture: FixtureConfiguration<Customer>
    private val orderLimit = Money(BigDecimal.TEN)
    private val auditEntry = AuditEntry("Test", Date.from(Instant.now()))
    private val auditEntry2 = AuditEntry("Test2", Date.from(Instant.now()))

    @BeforeEach
    fun setUp() {
        fixture = AggregateTestFixture(Customer::class.java)
        fixture.registerCommandDispatchInterceptor(BeanValidationInterceptor())
    }

    @Test
    fun createCustomerTest() {
        val name = PersonName("First", "Last")
        val createCustomerCommand = CreateCustomerCommand(name, orderLimit, auditEntry)
        val customerCreatedEvent =
            CustomerCreatedEvent(name, orderLimit, createCustomerCommand.targetAggregateIdentifier, auditEntry)

        fixture.given().`when`(createCustomerCommand).expectEvents(customerCreatedEvent)
    }

    @Test
    fun createCustomerOrderTest() {
        val personName = PersonName("First", "Last")
        val customerCreatedEvent = CustomerCreatedEvent(
            personName,
            orderLimit.add(Money(BigDecimal.ONE)),
            CustomerId("id"),
            auditEntry
        )
        val createCustomerOrderCommand = CreateCustomerOrderCommand(CustomerId("id"), orderLimit, auditEntry)
        val customerOrderCreatedEvent = CustomerOrderCreatedEvent(
            createCustomerOrderCommand.customerOrderId,
            orderLimit,
            CustomerId("id"),
            auditEntry
        )

        fixture.given(customerCreatedEvent).`when`(createCustomerOrderCommand)
            .expectEvents(customerOrderCreatedEvent)
    }

    @Test
    fun createCustomerOrderFailOrderLimitTest() {
        val personName = PersonName("First", "Last")
        val customerCreatedEvent = CustomerCreatedEvent(
            personName,
            orderLimit,
            CustomerId("id"),
            auditEntry
        )
        val createCustomerOrderCommand = CreateCustomerOrderCommand(CustomerId("id"), orderLimit, auditEntry)
        val customerOrderCreatedEvent = CustomerOrderCreatedEvent(
            createCustomerOrderCommand.customerOrderId,
            orderLimit,
            CustomerId("id"),
            auditEntry
        )

        fixture.given(customerCreatedEvent).`when`(createCustomerOrderCommand)
            .expectException(UnsupportedOperationException::class.java)
    }
}