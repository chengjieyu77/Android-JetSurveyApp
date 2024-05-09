package com.example.jetsurveyme.screen.login

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WelcomeRoute(
    navigateToSignInScreen:(email:String) -> Unit,
    navigateToSignUpScreen:(email:String) -> Unit,
    onSignInAsGuest:()->Unit,
){
    val welcomeViewModel: LoginViewModel = viewModel<LoginViewModel>()
    LoginScreen(
        emailSignInSignUp = {email ->
            welcomeViewModel.checkIfEmailExists(
                email = email,
                toSignIn = navigateToSignInScreen,
                toSignUp = navigateToSignUpScreen)
        },
        onSignInAsGuest = onSignInAsGuest
    )
}