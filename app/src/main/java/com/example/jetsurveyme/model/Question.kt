package com.example.jetsurveyme.model

import androidx.annotation.StringRes
import java.lang.reflect.Type

data class Question<T>(
    @StringRes val title:Int,
    val answer: T
)
