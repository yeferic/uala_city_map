package com.yeferic.ualacity.data.repositories

import android.content.SharedPreferences
import io.mockk.MockKAnnotations
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocalRepositoryImplTest {
    private lateinit var repositoryImpl: LocalRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @MockK(relaxed = true)
    private lateinit var sharedPreferencesMock: SharedPreferences

    @MockK(relaxed = true)
    private lateinit var editor: SharedPreferences.Editor

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        every { sharedPreferencesMock.edit() } returns editor

        repositoryImpl =
            LocalRepositoryImpl(
                sharedPreferences = sharedPreferencesMock,
            )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        confirmVerified(sharedPreferencesMock, editor)
    }

    @Test
    fun `saveFavoriteCity should add city to SharedPreferences`() {
        // Given
        val existingCities = setOf("123", "456")
        val newCity = "789"
        every { sharedPreferencesMock.getStringSet(any(), any()) } returns existingCities

        // When
        repositoryImpl.saveFavoriteCity(newCity)

        // Then
        val expectedSet = setOf("123", "456", newCity)
        verify {
            sharedPreferencesMock.getStringSet(any(), any())
            sharedPreferencesMock.edit()
            editor.putStringSet(any(), expectedSet)
            editor.apply()
        }
    }

    @Test
    fun `getFavoriteCities should return set from SharedPreferences`() {
        // Given
        val savedCities = setOf("321", "654")
        every { sharedPreferencesMock.getStringSet(any(), any()) } returns savedCities

        // When
        val result = repositoryImpl.getFavoriteCities()

        // Then
        verify {
            sharedPreferencesMock.getStringSet(any(), any())
        }
        Assertions.assertEquals(savedCities, result)
    }

    @Test
    fun `removeFavoriteCity should remove city from SharedPreferences`() {
        // Given
        val cityToRemove = "123"
        val savedCities = setOf("111", cityToRemove, "333")
        every { sharedPreferencesMock.getStringSet(any(), any()) } returns savedCities

        // When
        repositoryImpl.removeFavoriteCity(cityToRemove)

        // Then
        val expectedSet = setOf("111", "333")
        verify {
            sharedPreferencesMock.getStringSet(any(), any())
            sharedPreferencesMock.edit()
            editor.putStringSet(any(), expectedSet)
            editor.apply()
        }
    }

    @Test
    fun `getFavoriteCities should return empty set if SharedPreferences returns null`() {
        // Given
        every { sharedPreferencesMock.getStringSet(any(), any()) } returns null

        // When
        val result = repositoryImpl.getFavoriteCities()

        // Then
        verify {
            sharedPreferencesMock.getStringSet(any(), any())
        }
        Assertions.assertEquals(emptySet<String>(), result)
    }
}
