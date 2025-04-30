package com.yeferic.ualacity.presentation.map.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import com.yeferic.desingsystem.atomicdesign.molecules.UalaMapWidget
import com.yeferic.desingsystem.tokens.white
import com.yeferic.ualacity.presentation.map.compose.widgets.MapScreenHeader
import com.yeferic.ualacity.presentation.map.viewmodels.MapViewModel

@Composable
fun MapScreen(viewModel: MapViewModel = hiltViewModel()) {
    var isMapLoaded by remember { mutableStateOf(false) }
    val cameraPositionState =
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(-34.6037, -58.3816), 14f)
        }
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val query by viewModel.query.collectAsState()
    val city by viewModel.citySelected.collectAsState()
    val result by viewModel.queryResult.collectAsState()

    println(result)
    LaunchedEffect(city) {
        city?.let {
            cameraPositionState.animate(
                update =
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it.coordinates.latitude,
                            it.coordinates.longitude,
                        ),
                        14f,
                    ),
                durationMs = 1000,
            )
        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize().safeDrawingPadding()) {
        val (map, header, loader) = createRefs()

        UalaMapWidget(
            modifier =
                Modifier
                    .fillMaxSize()
                    .constrainAs(map) {
                        linkTo(top = parent.top, bottom = parent.bottom)
                        linkTo(start = parent.start, end = parent.end)
                    },
            cameraPosition = cameraPositionState,
            onMapClick = {
                focusManager.clearFocus()
            },
            onMapLoaded = {
                isMapLoaded = true
            },
        )

        MapScreenHeader(
            modifier =
                Modifier
                    .constrainAs(header) {
                        linkTo(
                            start = parent.start,
                            end = parent.end,
                        )
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom,
                            bottomMargin = 36.dp,
                            bias = 0f,
                        )
                    }.padding(16.dp),
            cities = result,
            citySelected = city,
            onSelected = {
                viewModel.onCitySelected(it)
            },
            onValueChange = viewModel::onTextChanged,
            query = query,
            setCityAsFavorite = viewModel::changeFavoriteStatus,
            isLoading = uiState.isLoading,
        )

        if (isMapLoaded.not()) {
            Box(
                modifier =
                    Modifier
                        .constrainAs(loader) {
                            linkTo(top = parent.top, bottom = parent.bottom)
                            linkTo(start = parent.start, end = parent.end)
                        }.fillMaxSize()
                        .background(white.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }
    }
}
