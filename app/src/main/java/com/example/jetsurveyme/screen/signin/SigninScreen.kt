package com.example.jetsurveyme.screen.signin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jetsurveyme.R
import com.example.jetsurveyme.component.AppTopBar
import com.example.jetsurveyme.component.ColoredButton
import com.example.jetsurveyme.component.ColorlessButton
import com.example.jetsurveyme.component.EmailInput
import com.example.jetsurveyme.component.PasswordInput
import com.example.jetsurveyme.navigation.JetsurveyScreens
import com.example.jetsurveyme.screen.login.OrDivider
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SigninScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    signinViewModel: SigninViewModel = hiltViewModel(),
    email: String
){
//    val currentRoute = navController.currentBackStackEntry?.destination?.route
//    Log.d("current route",currentRoute.toString())
    val showLoginForm = rememberSaveable() {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = signinViewModel.signInState.collectAsState(initial = null)
    Scaffold(
        topBar = {
            AppTopBar(title = "Sign in",
                onNavClick = { navController.popBackStack() })
        }
    ) {paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {
            if (showLoginForm.value){
                UserFormSignIn(
                    emailFromWelcome = email,
                    //emailFromWelcome = signinViewModel.getEmail()
                            onForgetClicked = {}
                ){email,password ->
                    scope.launch {
                        signinViewModel.loginUser(email, password)
                    }

                }
            }else{
                UserFormSignIn(
                    emailFromWelcome = email
                    //emailFromWelcome = signinViewModel.getEmail()

                ){email,password ->

                    signinViewModel.registerUser(email, password)
                }
            }


            OrDivider()
            Spacer(modifier = modifier.height(10.dp))

            ColorlessButton(text = "Sign in as guest"){
                navController.navigate(JetsurveyScreens.SurveyContentScreen.name)
            }

            LaunchedEffect(key1 = state.value?.isSuccess) {
                scope.launch {
                    if (state.value?.isSuccess?.isNotEmpty() == true) {
                        val success = state.value?.isSuccess
                        Toast.makeText(context, "${success}", Toast.LENGTH_LONG).show()
                        navController.navigate(JetsurveyScreens.SurveyContentScreen.name)
                    }
                }
            }

            LaunchedEffect(key1 = state.value?.isError) {
                scope.launch {
                    if (state.value?.isError?.isNotEmpty() == true) {
                        val error = state.value?.isError
                        Toast.makeText(context, "${error}", Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }
}


@Composable
fun UserFormSignIn(
    modifier : Modifier = Modifier,
    loading:Boolean = false,
    isCreatedAccount:Boolean = false,
    emailFromWelcome:String,
    onForgetClicked:()->Unit = {},
    onDone: (String,String) -> Unit = {email,password ->}
){
    val email = rememberSaveable{ mutableStateOf(emailFromWelcome) }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable{ mutableStateOf(false) }
    //val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value,password.value){
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
                && password.value.length>6
    }

    val focusRequester = remember { FocusRequester() }

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

        PasswordInput(
            modifier = Modifier.focusRequester(focusRequester),
                //.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            //trailingIcon = {},
            passwordVisibility =passwordVisibility,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onDone(email.value.trim(),password.value.trim())
                keyboardController?.hide()
            }
        )

        Spacer(modifier = modifier.height(20.dp))
        ColoredButton(
            text = "Sign in",
            enabled = valid
        ){
            onDone(email.value.trim(),password.value.trim())
            keyboardController?.hide()
        }
        Spacer(modifier = modifier.height(40.dp))

        Text(text = "Forgot password?",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = modifier.clickable {
                onForgetClicked()
            })

        Spacer(modifier = modifier.height(40.dp))
    }





}