package com.dz.bmstu_trade.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dz.bmstu_trade.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    sheetState: SheetState,
    onValueChange: (Boolean) -> Unit,
    onQrConnectClick: () -> Unit,
    onManualConnectClick: () -> Unit
) {
    val qrIconPainter = ImageVector.vectorResource(id = R.drawable.ic_qr_code_24)
    val codeIconPainter = ImageVector.vectorResource(id = R.drawable.ic_manual_24)
    ModalBottomSheet(
        onDismissRequest = {
            onValueChange(false)
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomButton(
                iconPainter = qrIconPainter,
                text = stringResource(R.string.qr_code_connect),
                onClick = onQrConnectClick,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomButton(
                iconPainter = codeIconPainter,
                text = stringResource(R.string.manual_connect),
                onClick = onManualConnectClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CustomButton(
    iconPainter: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .defaultMinSize(minHeight = 32.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Icon(
                imageVector = iconPainter,
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 32.dp, vertical = 12.dp),
                tint = Color.Unspecified
            )
        }

        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}