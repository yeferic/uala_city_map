package com.yeferic.ualacity.presentation.map.compose.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yeferic.desingsystem.atomicdesign.molecules.UalaSuggestTextFieldWidget
import com.yeferic.desingsystem.atomicdesign.molecules.UalaSuggestTextFieldWidgetItem
import com.yeferic.ualacity.R
import com.yeferic.ualacity.domain.models.CityQueryResultModel

@Composable
fun MapScreenHeader(
    modifier: Modifier,
    cities: List<CityQueryResultModel>,
    citySelected: CityQueryResultModel?,
    query: String,
    isLoading: Boolean,
    setCityAsFavorite: () -> Unit,
    onValueChange: (String) -> Unit,
    onSelected: (CityQueryResultModel?) -> Unit,
    onFocusEvent: () -> Unit = {},
) {
    Box(
        modifier =
            modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(4.dp),
                ),
    ) {
        UalaSuggestTextFieldWidget(
            items =
                cities.map {
                    UalaSuggestTextFieldWidgetItem(
                        id = it.id.toString(),
                        text = it.text,
                        icon = if (it.isFavorite) Icons.Default.Star else null,
                    )
                },
            onSelected = { selected ->
                onSelected(cities.firstOrNull { selected.id == it.id.toString() })
            },
            value = query,
            itemSelected =
                citySelected?.let {
                    UalaSuggestTextFieldWidgetItem(
                        id = it.id.toString(),
                        text = it.text,
                        icon = if (it.isFavorite) Icons.Default.Star else null,
                    )
                },
            onValueChange = onValueChange,
            isLoading = isLoading,
            modifierTextField = Modifier.fillMaxWidth(),
            placeholder = stringResource(R.string.mapscreen_search),
            leftIcon = Icons.Default.Search,
            rightIcon = Icons.Default.Star,
            onRightIconClick = setCityAsFavorite,
            onFocus = onFocusEvent,
        )
    }
}
