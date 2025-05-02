package com.yeferic.ualacity.domain.models

import androidx.annotation.Size

data class AnalyticEventModel(
    @field:Size(max = 40)
    val keyName: String,
    val data: Map<String, Any> = emptyMap(),
)
