package com.dz.bmstu_trade.data.mappers
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun imageVkMapper(url:String) {

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .size(coil.size.Size.ORIGINAL)
            .build()
    )

    var bitmap = remember<Bitmap?> {
        null
    }
    val imageState = painter.state

    if (imageState is AsyncImagePainter.State.Success) {
        bitmap = imageState.result.drawable.toBitmap().scale(16, 16)
    }

    Box()
    {
         bitmap?.let {Image(bitmap =
            it.asImageBitmap(), contentDescription = null)
        }
    }
}
