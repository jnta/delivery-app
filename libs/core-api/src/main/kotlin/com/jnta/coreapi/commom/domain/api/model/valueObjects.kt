package com.jnta.coreapi.commom.domain.api.model

import java.util.Date

data class AuditEntry(val who: String, val `when`: Date)
