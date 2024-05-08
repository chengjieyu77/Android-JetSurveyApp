package com.example.jetsurveyme.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title:String,
    onNavClick:()->Unit = {}
){
    CenterAlignedTopAppBar(title = { Text(text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
    ) },
        navigationIcon = {
            IconButton(onClick = onNavClick) {
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back icon",
                    tint = MaterialTheme.colorScheme.primary)
            }
        })
}