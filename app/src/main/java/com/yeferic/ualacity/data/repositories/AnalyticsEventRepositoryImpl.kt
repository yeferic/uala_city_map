package com.yeferic.ualacity.data.repositories

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.yeferic.ualacity.domain.models.AnalyticEventModel
import com.yeferic.ualacity.domain.repositories.AnalyticsEventRepository
import javax.inject.Inject

class AnalyticsEventRepositoryImpl
    @Inject
    constructor(
        private val firebaseAnalytics: FirebaseAnalytics,
    ) : AnalyticsEventRepository {
        override fun trackEvent(event: AnalyticEventModel) {
            firebaseAnalytics.logEvent(
                event.keyName,
                getBundleOfEvent(event.data),
            )
        }

        private fun getBundleOfEvent(data: Map<String, Any>) =
            Bundle().apply {
                data.forEach { (key, value) ->
                    when (value) {
                        is String -> putString(key, value)
                        is Int -> putInt(key, value)
                        is Long -> putLong(key, value)
                        is Double -> putDouble(key, value)
                        is Float -> putFloat(key, value)
                        is Boolean -> putBoolean(key, value)
                        else -> putString(key, value.toString())
                    }
                }
            }
    }
