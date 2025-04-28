package com.yeferic.ualacity.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.yeferic.ualacity.data.repositories.CityRepositoryImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartActivity : ComponentActivity() {
    @Inject
    lateinit var cityRepository: CityRepositoryImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            try {
                cityRepository.fetchRemoteCities()
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}
