package com.jnta.coreapi.commom.domain.api

import com.jnta.coreapi.commom.domain.api.model.AuditEntry

abstract class AuditableAbstractEvent(open val auditEntry: AuditEntry)