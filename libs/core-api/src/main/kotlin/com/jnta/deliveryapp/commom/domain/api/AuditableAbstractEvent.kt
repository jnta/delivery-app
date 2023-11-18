package com.jnta.deliveryapp.commom.domain.api

import com.jnta.deliveryapp.commom.domain.api.model.AuditEntry

abstract class AuditableAbstractEvent(open val auditEntry: AuditEntry)