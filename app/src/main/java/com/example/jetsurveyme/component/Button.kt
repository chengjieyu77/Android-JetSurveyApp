package com.example.jetsurveyme.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job

@Composable
fun ColoredButton(
    modifier : Modifier = Modifier,
    text:String = "",
    width:Float = 1f,
    enabled :Boolean = true,
    onClick:() -> Unit = {}
){
    Button(onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth(width)
            .padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(text = text)
    }
}

@Composable
fun ColorlessButton(
    modifier : Modifier = Modifier,
    text:String,
    width:Float = 1f,
    enabled: Boolean = true,
    onClick:() -> Unit = {}
){
    Button(onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth(width)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        border = BorderStroke(width=1.dp,color= MaterialTheme.colorScheme.onSurface)
    ) {
        Text(text = text)
    }
}

//@Composable
//fun FormButtons(
//    modifier: Modifier = Modifier,
//    textLeft:String,
//    textRight:String,
//    onLeftClick: Job = {},
//    onRightClick:()->Unit = {}
//){
//    Row(
//        modifier = modifier.fillMaxWidth()
//    ) {
//        ColorlessButton(text = textLeft, onClick = onLeftClick)
//        ColoredButton(text = textRight, onClick = onRightClick)
//    }
//}