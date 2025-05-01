package com.yeferic.ualacity.domain.usecases

import com.yeferic.ualacity.domain.models.AnalyticEventModel
import com.yeferic.ualacity.domain.repositories.AnalyticsEventRepository
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
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
class TrackEventUseCaseTest {
    private lateinit var useCase: TrackEventUseCase

    private val testDispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var analyticsRepositoryMock: AnalyticsEventRepository

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
        useCase =
            TrackEventUseCase(
                analyticsEventRepository = analyticsRepositoryMock,
            )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(analyticsRepositoryMock)
    }

    @Test
    fun `trackStartLoadDataEvent should call to analyticsRepository`() {
        // Given
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackStartLoadDataEvent()

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
    }

    @Test
    fun `trackFinishLoadDataEvent should call to analyticsRepository`() {
        // Given
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackFinishLoadDataEvent()

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
    }

    @Test
    fun `trackFailLoadDataEvent should call to analyticsRepository`() {
        // Given
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackFailLoadDataEvent()

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
    }

    @Test
    fun `trackRetryLoadDataEvent should call to analyticsRepository`() {
        // Given
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackRetryLoadDataEvent()

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
    }

    @Test
    fun `trackFinishLoadMapEvent should call to analyticsRepository`() {
        // Given
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackFinishLoadMapEvent()

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
    }

    @Test
    fun `trackAccessLocationAcceptedEvent should call to analyticsRepository`() {
        // Given
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackAccessLocationAcceptedEvent()

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
    }

    @Test
    fun `trackAccessLocationDeniedEvent should call to analyticsRepository`() {
        // Given
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackAccessLocationDeniedEvent()

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
    }

    @Test
    fun `trackFocusOnSearchEvent should call to analyticsRepository`() {
        // Given
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackFocusOnSearchEvent()

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
    }

    @Test
    fun `trackSelectCityEvent should call to analyticsRepository`() {
        // Given
        val cityId = 1
        val cityName = "City Name"
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackSelectCityEvent(cityId, cityName)

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
        assert(eventSlot.captured.data.containsValue(cityName))
    }

    @Test
    fun `trackCityAsFavoriteEvent should call to analyticsRepository`() {
        // Given
        val cityId = 1
        val cityName = "City Name"
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackCityAsFavoriteEvent(cityId, cityName)

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
        assert(eventSlot.captured.data.containsValue(cityName))
    }

    @Test
    fun `trackCityNotAsFavoriteEvent should call to analyticsRepository`() {
        // Given
        val cityId = 1
        val cityName = "City Name"
        val eventSlot = slot<AnalyticEventModel>()
        every { analyticsRepositoryMock.trackEvent(capture(eventSlot)) } returns Unit

        // When
        useCase.trackCityNotAsFavoriteEvent(cityId, cityName)

        // Then
        verify {
            analyticsRepositoryMock.trackEvent(any())
        }

        assert(eventSlot.isCaptured)
        assert(eventSlot.captured.data.containsValue(cityName))
    }
}
