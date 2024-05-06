package com.example.jetsurveyme.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class ComicCharacter(
    @DrawableRes val image:Int,
    @StringRes val name:Int,
    val initialSelected:Boolean = false
){
    var selected:Boolean by mutableStateOf(initialSelected)
}
