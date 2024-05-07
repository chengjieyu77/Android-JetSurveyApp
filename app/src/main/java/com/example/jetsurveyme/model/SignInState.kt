package com.example.jetsurveyme.model

data class SignInState(
    val isSignInSuccessful:Boolean = false,
    val isError:String? = null,
    val isLoading:Boolean = false,
    val isSuccess:String?=""
)
