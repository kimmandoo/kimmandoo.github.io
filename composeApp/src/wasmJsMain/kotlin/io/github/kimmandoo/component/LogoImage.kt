package io.github.kimmandoo.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kimmandoo_porfolio.composeapp.generated.resources.Res
import kimmandoo_porfolio.composeapp.generated.resources.ic_dark_mode
import org.jetbrains.compose.resources.painterResource

@Composable
fun LogoImage(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_dark_mode),
            contentDescription = null,
            modifier = Modifier.fillMaxHeight(),
            tint = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    }
}