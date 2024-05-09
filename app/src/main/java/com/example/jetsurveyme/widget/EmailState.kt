package com.example.jetsurveyme.widget

import android.util.Patterns

class EmailState (val email:String?=null):
        TextFieldState(validator = ::isEmailValid , errorFor = ::emailValidationError){
            init {
                email?.let {
                    text = it
                }
            }
        }

private fun emailValidationError(email: String):String{
    return "Invalid email: $email"
}

private fun isEmailValid(email: String):Boolean{
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

val EmailStateSaver = textFieldStateSaver(EmailState())