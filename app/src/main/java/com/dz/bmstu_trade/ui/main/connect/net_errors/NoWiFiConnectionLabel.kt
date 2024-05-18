package com.dz.bmstu_trade.ui.main.connect.net_errors

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.dz.bmstu_trade.R

@Composable
fun NoWiFiConnectionLabel() {
    Text(
        text = "WiFi выключен, пожалуйста, включите его",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onErrorContainer,
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.spinner_description_label_padding)),
        textAlign = TextAlign.Center
    )
}