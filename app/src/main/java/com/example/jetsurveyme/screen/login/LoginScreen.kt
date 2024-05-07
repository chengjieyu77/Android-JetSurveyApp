package com.example.jetsurveyme.screen.login

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jetsurveyme.R
import com.example.jetsurveyme.component.ColoredButton
import com.example.jetsurveyme.component.ColorlessButton
import com.example.jetsurveyme.component.EmailInput
import com.example.jetsurveyme.navigation.JetsurveyScreens
import com.example.jetsurveyme.screen.signin.SigninViewModel
import com.example.jetsurveyme.util.isEmailValid

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    email: MutableState<String>
){
    //val emailObserved = signinViewModel.email.ob
    val logoVisible = remember {
        mutableStateOf(true)
    }
    val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val textFocused = rememberSaveable {
        mutableStateOf(false)
    }
    Column {
        AnimatedVisibility(visible = !textFocused.value) {
            AppLogoAndCaption(modifier = modifier)
        }

        Spacer(modifier = modifier.height(50.dp))
        UserForm(modifier = modifier,
            emailState = email,
            onTextFieldFocus = {focused->
                               textFocused.value = focused
            },
            onAction = {navController.navigate(JetsurveyScreens.SigninScreen.name)},
            emailSignIn = {
                //signinViewModel.restoreEmail(email = email.value)
                navController.navigate(JetsurveyScreens.SigninScreen.name)}){
            navController.navigate(JetsurveyScreens.SurveyContentScreen.name)
        }
    }
}

@Preview(showBackground=true)
@Composable
private fun UserFormPreview(){
    UserForm(emailState = remember {
        mutableStateOf("")
    })
}
@Composable
fun UserForm(modifier: Modifier = Modifier,
             emailState:MutableState<String>,
             onTextFieldFocus:(Boolean) -> Unit = {},
             onAction:()->Unit={},
             emailSignIn:()->Unit = {},
             guestSignIn:() -> Unit = {}){
    //val email = rememberSaveable{ mutableStateOf("") }
    val valid = isEmailValid(emailState.value)
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign in or create an account",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary.copy(0.6f),
            modifier = modifier.padding(vertical = 4.dp))

        EmailInput(email = emailState,
            emailValid = valid,
            onTextFieldFocus = onTextFieldFocus,
            onAction = KeyboardActions {
                if (valid) onAction.invoke() else return@KeyboardActions
            })

        ColoredButton(text = "Continue",
            width = 1f,
            enabled = valid){
            emailSignIn.invoke()
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
fun AppLogoAndCaption(modifier:Modifier = Modifier) {
    Column(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box( ){

            Icon(painter = painterResource(id = R.drawable.ic_logo_light),
                contentDescription = "app logo",
                tint = Color.Unspecified)
        }

        Text(text = stringResource(id = R.string.app_caption),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier.padding(vertical = 16.dp))
    }
}
