package com.example.jetsurveyme.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Superhero(
    @DrawableRes val image:Int,
    @StringRes val name:Int
)
