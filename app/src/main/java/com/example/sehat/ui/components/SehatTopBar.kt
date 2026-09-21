package com.example.sehat.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sehat.ui.theme.CreamBackground
import com.example.sehat.ui.theme.MaroonPrimary
import com.example.sehat.ui.theme.TextPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SehatTopBar(
    title: String,
    onBackClick: (() -> Unit)? = null,
    onHelpClick: (() -> Unit)? = null,
    languageSelector: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        },
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaroonPrimary
                    )
                }
            }
        },
        actions = {
            languageSelector?.invoke()
            if (onHelpClick != null) {
                IconButton(onClick = onHelpClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                        contentDescription = "Help",
                        tint = MaroonPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CreamBackground,
            titleContentColor = TextPrimary,
            navigationIconContentColor = MaroonPrimary,
            actionIconContentColor = MaroonPrimary
        ),
        windowInsets = TopAppBarDefaults.windowInsets,
        modifier = modifier
    )
}
