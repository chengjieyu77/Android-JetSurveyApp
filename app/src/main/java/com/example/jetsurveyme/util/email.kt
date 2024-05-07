package com.example.jetsurveyme.util

import android.util.Patterns

fun isEmailValid(email: CharSequence?): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}