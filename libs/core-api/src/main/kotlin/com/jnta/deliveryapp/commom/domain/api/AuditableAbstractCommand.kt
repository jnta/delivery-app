package com.jnta.deliveryapp.commom.domain.api

import com.jnta.deliveryapp.commom.domain.api.model.AuditEntry

abstract class AuditableAbstractCommand(open val auditEntry: AuditEntry)