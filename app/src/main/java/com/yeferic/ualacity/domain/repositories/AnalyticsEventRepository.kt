package com.yeferic.ualacity.domain.repositories

import com.yeferic.ualacity.domain.models.AnalyticEventModel

interface AnalyticsEventRepository {
    fun trackEvent(event: AnalyticEventModel)
}
