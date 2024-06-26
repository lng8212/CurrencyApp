package presentation.component


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.model.CurrencyCode
import org.jetbrains.compose.resources.painterResource
import primaryColor
import surfaceColor
import textColor

/**
 * @Author: longkd
 * @Since: 23:22 - 26/06/2024
 */

@Composable
fun CurrencyCodePickerView(
    code: CurrencyCode,
    isSelected: Boolean,
    onSelect: (CurrencyCode) -> Unit,
) {
    val saturation =
        remember { androidx.compose.animation.core.Animatable(if (isSelected) 1f else 0f) }
    LaunchedEffect(isSelected) {
        saturation.animateTo(if (isSelected) 1f else 0f)
    }

    val colorMatrix = remember(saturation.value) {
        androidx.compose.ui.graphics.ColorMatrix().apply {
            setToSaturation(saturation.value)
        }
    }

    val animatedAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )
    Row(
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(size = 8.dp))
            .clickable { onSelect(code) }
            .padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(code.flag),
                contentDescription = null,
                colorFilter = androidx.compose.ui.graphics.ColorFilter.colorMatrix(colorMatrix)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.alpha(animatedAlpha),
                text = code.name,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
        }
        CurrencyCodeSelector(isSelected = isSelected)
    }
}

@Composable
private fun CurrencyCodeSelector(isSelected: Boolean = false) {
    val animatedColor by animateColorAsState(
        targetValue = if (isSelected) primaryColor else textColor.copy(alpha = 0.1f),
        animationSpec = tween(durationMillis = 300)
    )
    Box(
        modifier = Modifier.size(18.dp)
            .clip(CircleShape)
            .background(animatedColor),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            androidx.compose.material3.Icon(
                modifier = Modifier.size(12.dp),
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = surfaceColor
            )
        }
    }
}
