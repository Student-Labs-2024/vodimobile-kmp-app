package com.vodimobile.presentation.screens.reservation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vodimobile.domain.model.remote.dto.service_list.ServicesDTO
import com.vodimobile.presentation.screens.reservation.utils.finalCost
import com.vodimobile.presentation.theme.ExtendedTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ServiceItem(
    item: ServicesDTO,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Chip(
        onClick = { onClick() },
        colors = ChipDefaults.chipColors(
            backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else
                ExtendedTheme.colorScheme.containerBack
        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {

        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = item.title.replaceFirstChar { it.uppercase() },
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else
                    MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = finalCost(item.cost),
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else
                    MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

