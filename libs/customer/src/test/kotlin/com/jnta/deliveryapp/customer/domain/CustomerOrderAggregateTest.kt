package com.jnta.deliveryapp.customer.domain

import com.jnta.deliveryapp.commom.domain.api.model.AuditEntry
import com.jnta.deliveryapp.commom.domain.api.model.Money
import com.jnta.deliveryapp.customer.domain.api.CustomerOrderCreatedEvent
import com.jnta.deliveryapp.customer.domain.api.CustomerOrderDeliveredEvent
import com.jnta.deliveryapp.customer.domain.api.MarkCustomerOrderAsDeliveredCommand
import com.jnta.deliveryapp.customer.domain.api.model.CustomerId
import com.jnta.deliveryapp.customer.domain.api.model.CustomerOrderId
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant
import java.util.*


class CustomerOrderAggregateTest {
    private lateinit var fixture: FixtureConfiguration<CustomerOrder>
    private val who = "Test"
    private val auditEntry: AuditEntry = AuditEntry(who, Date.from(Instant.now()))
    private val orderId: CustomerOrderId = CustomerOrderId("orderId")
    private val customerId: CustomerId = CustomerId("customerId")
    private val orderTotal: Money = Money(BigDecimal.TEN)

    @BeforeEach
    fun setUp() {
        fixture = AggregateTestFixture(CustomerOrder::class.java)
        fixture.registerCommandDispatchInterceptor(BeanValidationInterceptor())
    }

    @Test
    fun markOrderAsDeliveredTest() {
        val customerOrderCreatedEvent = CustomerOrderCreatedEvent(orderId, orderTotal, customerId, auditEntry)
        val markCustomerOrderAsDeliveredCommand = MarkCustomerOrderAsDeliveredCommand(orderId, auditEntry)
        val customerOrderDeliveredEvent = CustomerOrderDeliveredEvent(orderId, auditEntry)

        fixture.given(customerOrderCreatedEvent)
            .`when`(markCustomerOrderAsDeliveredCommand)
            .expectEvents(customerOrderDeliveredEvent)
    }
}