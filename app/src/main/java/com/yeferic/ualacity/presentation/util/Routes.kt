package com.yeferic.ualacity.presentation.util

sealed class Routes(
    var route: String,
) {
    data object LoadScreen : Routes("LoadScreen")

    data object MapScreen : Routes("MapScreen")
}
