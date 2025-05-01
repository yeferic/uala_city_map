package com.yeferic.ualacity.data.repositories

import com.google.firebase.analytics.FirebaseAnalytics
import com.yeferic.ualacity.domain.models.AnalyticEventModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AnalyticsEventRepositoryImplTest {
    private lateinit var repositoryImpl: AnalyticsEventRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var firebaseAnalyticsMock: FirebaseAnalytics

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        repositoryImpl =
            AnalyticsEventRepositoryImpl(
                firebaseAnalytics = firebaseAnalyticsMock,
            )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(
            firebaseAnalyticsMock,
        )
    }

    @Test
    fun `trackEvent should log event to Firebase`() {
        // Given
        val eventName = "EventName"
        val eventData = mapOf("key" to "value")
        val event = AnalyticEventModel(eventName, eventData)

        every { firebaseAnalyticsMock.logEvent(eventName, any()) } just Runs

        // When
        repositoryImpl.trackEvent(event)

        // Then
        verify { firebaseAnalyticsMock.logEvent(eventName, any()) }
    }
}
