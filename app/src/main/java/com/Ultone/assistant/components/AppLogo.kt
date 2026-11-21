
package com.ultone.assistant.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ultone.assistant.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Int = 60
) {
    Image(
        painter = painterResource(R.drawable.app_logo),
        contentDescription = "Ultron x Luna Logo",
        modifier = modifier.size(size.dp)
    )
}
