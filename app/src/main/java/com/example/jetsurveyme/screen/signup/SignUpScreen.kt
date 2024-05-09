package com.example.jetsurveyme.screen.signup

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetsurveyme.R
import com.example.jetsurveyme.component.AppTopBar
import com.example.jetsurveyme.component.ColoredButton
import com.example.jetsurveyme.component.ColorlessButton
import com.example.jetsurveyme.component.EmailInput
import com.example.jetsurveyme.component.PasswordInput
import com.example.jetsurveyme.navigation.JetsurveyScreens
import com.example.jetsurveyme.screen.login.OrDivider
import com.example.jetsurveyme.screen.signin.SigninViewModel
import com.example.jetsurveyme.screen.signin.UserFormSignIn

@Composable
fun SignUpScreen(navController: NavController,
                 modifier: Modifier = Modifier,
                 email: String,
                 signinViewModel: SigninViewModel = hiltViewModel()){
//    val currentRoute = navController.currentBackStackEntry?.destination?.route
//    Log.d("current route",currentRoute.toString())

    Scaffold(
        topBar = {
            AppTopBar(title = "Create account",
                onNavClick = { navController.popBackStack() })
        }
    ) {paddingValues->
        Column(modifier = modifier.padding(paddingValues = paddingValues)) {
            UserFormSignUp(emailFromWelcome =email ){email,password->
                signinViewModel.registerUser(email,password)
            }

            OrDivider()
            Spacer(modifier = modifier.height(10.dp))

            ColorlessButton(text = "Sign in as guest"){
                navController.navigate(JetsurveyScreens.SurveyContentScreen.name)
            }
        }
    }

}

@Composable
fun UserFormSignUp(
    modifier : Modifier = Modifier,
    loading:Boolean = false,
    isCreatedAccount:Boolean = false,
    emailFromWelcome:String,
    onDone: (String,String) -> Unit = {email,password ->}
){



    val email = rememberSaveable{ mutableStateOf(emailFromWelcome) }
    val password = rememberSaveable { mutableStateOf("") }
    val confirmPassword = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable{ mutableStateOf(false) }
    //val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusRequester = remember { FocusRequester() }
    val confirmValid = remember(password.value,confirmPassword.value){
        password.value.trim().equals(confirmPassword.value.trim())
    }
    val valid = remember(email.value,password.value){
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
                 && confirmValid
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(
            modifier = modifier,
            email = email,
            enabled = !loading,
            imeAction = ImeAction.Next,
            onAction = KeyboardActions{
                //passwordFocusRequest.requestFocus()//it means when you are done, it's going to go ahead and focus on the next field
                focusRequester.requestFocus()
            })

        //password
        PasswordInput(
            modifier = Modifier.focusRequester(focusRequester),
            //.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility =passwordVisibility,
            imeAction = ImeAction.Next,
            onAction = KeyboardActions{
                focusRequester.requestFocus()
            }
        )

        //confirm password
        PasswordInput(
            modifier = Modifier.focusRequester(focusRequester),
            //.focusRequester(passwordFocusRequest),
            passwordState = confirmPassword,
            labelId = "Confirm Password",
            isPasswordConfirmed = confirmValid,
            enabled = !loading,
            passwordVisibility =passwordVisibility,
            isConfirmInput = true,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onDone(email.value.trim(),password.value.trim())
                keyboardController?.hide()
            }
        )

        Text(text = stringResource(id = R.string.agreeService),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp))

        ColoredButton(text = "Create account",
            enabled = valid){
            onDone(email.value.trim(),password.value.trim())
            keyboardController?.hide()
        }
    }
}