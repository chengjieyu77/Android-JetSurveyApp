package com.example.jetsurveyme.screen.login

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jetsurveyme.R
import com.example.jetsurveyme.component.ColoredButton
import com.example.jetsurveyme.component.ColorlessButton
import com.example.jetsurveyme.component.EmailInput
import com.example.jetsurveyme.component.EmailInputSecond
import com.example.jetsurveyme.navigation.JetsurveyScreens
import com.example.jetsurveyme.screen.signin.SigninViewModel
import com.example.jetsurveyme.util.isEmailValid
import com.example.jetsurveyme.widget.EmailState
import com.example.jetsurveyme.widget.EmailStateSaver
import com.example.jetsurveyme.widget.TextFieldState

@Composable
fun LoginScreen(
    //navController: NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = viewModel(),
    emailSignInSignUp: (email: String) -> Unit,
    onSignInAsGuest:()->Unit,
    //email: MutableState<String>
){
    //val emailObserved = signinViewModel.email.ob
    val logoVisible = remember {
        mutableStateOf(true)
    }
    val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val textFocused = rememberSaveable {
        mutableStateOf(false)
    }
    val emailStateSecond by rememberSaveable(stateSaver = EmailStateSaver) {
        mutableStateOf(EmailState())
    }

    var showBranding by rememberSaveable { mutableStateOf(true) }
    Column {
        AnimatedVisibility(visible = showBranding) {
            AppLogoAndCaption(modifier = modifier)
        }

        Spacer(modifier = modifier.height(50.dp))
        UserForm(modifier = modifier,
            emailState = emailStateSecond,
            onTextFieldFocus = {focused->
                               textFocused.value = focused
            },
            emailSignInSignUp = emailSignInSignUp

//            {email->
//                Log.d("email signin",email)
//                //signinViewModel.restoreEmail(email = email.value)
//                loginViewModel.checkIfEmailExists(email,
//                    toSignIn = { navController.navigate(JetsurveyScreens.SigninScreen.name) },
//                    toSignUp = {navController.navigate(JetsurveyScreens.SignUpScreen.name)})
//                }
            ,
            onFocusChange = {focused -> showBranding = !focused}){
            //navController.navigate(JetsurveyScreens.SurveyContentScreen.name)
            onSignInAsGuest()
        }
    }
}

@Composable
fun UserForm(modifier: Modifier = Modifier,
             emailState:TextFieldState,
             onTextFieldFocus:(Boolean) -> Unit = {},
             onFocusChange:(Boolean) ->Unit = {},
             emailSignInSignUp:(email:String)->Unit = {},
             guestSignIn:() -> Unit = {}){
    //val email = rememberSaveable{ mutableStateOf("") }
    //val valid = isEmailValid(emailState.value)

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign in or create an account",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary.copy(0.6f),
            modifier = modifier.padding(vertical = 4.dp))

        val onSubmit = {
            if (emailState.isValid){
                emailSignInSignUp(emailState.text)
            }else{
                emailState.enableShowErrors()
            }
        }
        onFocusChange(emailState.isFocused)
        EmailInputSecond(
            emailState = emailState,
            imeAction = ImeAction.Next,
            onAction = onSubmit
        )

//        EmailInput(email = emailState,
//            emailValid = valid,
//            onTextFieldFocus = onTextFieldFocus,
//            onAction = KeyboardActions {
//                if (valid) onAction.invoke() else return@KeyboardActions
//            })

        ColoredButton(text = "Continue",
            width = 1f,
            ){
            onSubmit.invoke()
        }

        OrDivider()

        ColorlessButton(
            text = "Sign in as guest",
            width = 1f,
        ){
            guestSignIn.invoke()
        }

    }
}

@Composable
fun OrDivider(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "or",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary.copy(0.6f))
    }

}


@Preview(showBackground = true)
@Composable
fun AppLogoAndCaption(modifier:Modifier = Modifier,
                      lightTheme:Boolean = LocalContentColor.current.luminance()<0.5f) {
    Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box( ){

            Icon(painter = painterResource(id = if (lightTheme) R.drawable.ic_logo_light else R.drawable.ic_logo_dark),
                contentDescription = "app logo",
                tint = Color.Unspecified)
        }

        Text(text = stringResource(id = R.string.app_caption),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier.padding(vertical = 16.dp))
    }
}

@Preview(showBackground=true)
@Composable
private fun UserFormPreview(){
    UserForm(emailState = remember {
        EmailState()
    })
}