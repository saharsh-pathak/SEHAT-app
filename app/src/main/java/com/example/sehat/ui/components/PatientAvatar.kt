package com.example.sehat.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.sehat.R

@Composable
fun PatientAvatar(
    name: String,
    gender: String,
    size: Dp = 48.dp,
    modifier: Modifier = Modifier
) {
    val isFemale = gender.equals("Female", ignoreCase = true)
    val lowerName = name.lowercase()

    val drawableRes = when {
        lowerName.contains("ramesh") -> R.drawable.avatar_male_elder
        lowerName.contains("saharsh") -> R.drawable.avatar_male_young
        lowerName.contains("lata") -> R.drawable.avatar_female_young
        lowerName.contains("sita") -> R.drawable.avatar_female_elder
        isFemale -> {
            if (Math.abs(name.hashCode()) % 2 == 0) R.drawable.avatar_female_young else R.drawable.avatar_female_elder
        }
        else -> {
            if (Math.abs(name.hashCode()) % 2 == 0) R.drawable.avatar_male_young else R.drawable.avatar_male_elder
        }
    }

    Image(
        painter = painterResource(id = drawableRes),
        contentDescription = "$name Avatar",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
    )
}
