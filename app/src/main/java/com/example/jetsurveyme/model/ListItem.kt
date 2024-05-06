package com.example.jetsurveyme.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ListItem(
    val id:Int,
    val label:String,
    val initialChecked:Boolean = false
){
    var checked:Boolean by mutableStateOf(initialChecked)
}
