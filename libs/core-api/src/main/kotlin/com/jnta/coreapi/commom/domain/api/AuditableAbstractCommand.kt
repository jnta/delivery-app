package com.jnta.coreapi.commom.domain.api

import com.jnta.coreapi.commom.domain.api.model.AuditEntry

abstract class AuditableAbstractCommand(open val auditEntry: AuditEntry)