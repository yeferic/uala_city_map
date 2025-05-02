package com.yeferic.desingsystem.atomicdesign.molecules

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.core.content.ContextCompat
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

internal data object UalaMapWidgetTestTags {
    const val UALA_MAP_WIDGET = "ualaMapWidget"
}

@Composable
fun UalaMapWidget(
    modifier: Modifier = Modifier,
    cameraPosition: CameraPositionState,
    onMapClick: () -> Unit,
    onMapLoaded: () -> Unit,
    onAccessLocationEnable: () -> Unit = {},
    onAccessLocationDisable: () -> Unit = {},
) {
    val context = LocalContext.current

    val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    val permissionGranted =
        ContextCompat.checkSelfPermission(
            context,
            locationPermission,
        ) == PackageManager.PERMISSION_GRANTED

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (isGranted) {
                onAccessLocationEnable()
            } else {
                onAccessLocationDisable()
            }
        }

    LaunchedEffect(Unit) {
        launcher.launch(locationPermission)
    }

    GoogleMap(
        modifier = modifier.testTag(UalaMapWidgetTestTags.UALA_MAP_WIDGET),
        cameraPositionState = cameraPosition,
        properties =
            MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = permissionGranted,
            ),
        uiSettings =
            MapUiSettings(
                zoomControlsEnabled = true,
                myLocationButtonEnabled = false,
            ),
        onMapLoaded = {
            onMapLoaded()
        },
        onMapClick = {
            onMapClick()
        },
        onMapLongClick = {
            onMapClick()
        },
    )
}
