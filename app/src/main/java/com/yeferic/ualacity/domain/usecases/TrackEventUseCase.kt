package com.yeferic.ualacity.domain.usecases

import com.yeferic.ualacity.domain.models.AnalyticEventModel
import com.yeferic.ualacity.domain.repositories.AnalyticsEventRepository
import javax.inject.Inject

class TrackEventUseCase
    @Inject
    constructor(
        private val analyticsEventRepository: AnalyticsEventRepository,
    ) {
        companion object {
            // Load Screen
            const val START_LOAD_DATA_EVENT = "startLoadData"
            const val FINISH_LOAD_DATA_EVENT = "finishLoadData"
            const val FAIL_LOAD_DATA_EVENT = "failLoadData"
            const val RETRY_LOAD_DATA_EVENT = "retryLoadData"

            // Map Screen
            const val FINISH_LOAD_MAP_EVENT = "finishLoadMap"
            const val ACCESS_LOCATION_ACCEPTED_EVENT = "accessLocationAccepted"
            const val ACCESS_LOCATION_DENIED_EVENT = "accessLocationDenied"

            const val FOCUS_ON_SEARCH_EVENT = "focusOnSearch"
            const val SELECT_CITY_EVENT = "selectCity"
            const val CITY_AS_FAVORITE_EVENT = "cityAsFavorite"
            const val CITY_NOT_AS_FAVORITE_EVENT = "cityNotAsFavorite"
        }

        fun trackStartLoadDataEvent() {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = START_LOAD_DATA_EVENT,
                ),
            )
        }

        fun trackFinishLoadDataEvent() {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = FINISH_LOAD_DATA_EVENT,
                ),
            )
        }

        fun trackFailLoadDataEvent() {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = FAIL_LOAD_DATA_EVENT,
                ),
            )
        }

        fun trackRetryLoadDataEvent() {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = RETRY_LOAD_DATA_EVENT,
                ),
            )
        }

        fun trackFinishLoadMapEvent() {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = FINISH_LOAD_MAP_EVENT,
                ),
            )
        }

        fun trackAccessLocationAcceptedEvent() {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = ACCESS_LOCATION_ACCEPTED_EVENT,
                ),
            )
        }

        fun trackAccessLocationDeniedEvent() {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = ACCESS_LOCATION_DENIED_EVENT,
                ),
            )
        }

        fun trackFocusOnSearchEvent() {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = FOCUS_ON_SEARCH_EVENT,
                ),
            )
        }

        fun trackSelectCityEvent(
            cityId: Int,
            cityName: String,
        ) {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = SELECT_CITY_EVENT,
                    data =
                        mapOf(
                            "cityId" to cityId,
                            "cityName" to cityName,
                        ),
                ),
            )
        }

        fun trackCityAsFavoriteEvent(
            cityId: Int,
            cityName: String,
        ) {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = CITY_AS_FAVORITE_EVENT,
                    data =
                        mapOf(
                            "cityId" to cityId,
                            "cityName" to cityName,
                        ),
                ),
            )
        }

        fun trackCityNotAsFavoriteEvent(
            cityId: Int,
            cityName: String,
        ) {
            analyticsEventRepository.trackEvent(
                AnalyticEventModel(
                    keyName = CITY_NOT_AS_FAVORITE_EVENT,
                    data =
                        mapOf(
                            "cityId" to cityId,
                            "cityName" to cityName,
                        ),
                ),
            )
        }
    }
