package com.example.jetsurveyme.screen.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetsurveyme.R
import com.example.jetsurveyme.component.ColoredButton
import com.example.jetsurveyme.component.ColorlessButton
import com.example.jetsurveyme.navigation.JetsurveyScreens

@Composable
fun LoginScreen(navController: NavController,
                modifier: Modifier = Modifier){

    Column {
        AppLogoAndCaption(modifier = modifier)
        Spacer(modifier = modifier.height(50.dp))
        UserForm(modifier = modifier){
            navController.navigate(JetsurveyScreens.SurveyContentScreen.name)
        }
    }
}

@Preview(showBackground=true)
@Composable
private fun UserFormPreview(){
    UserForm()
}
@Composable
fun UserForm(modifier: Modifier = Modifier,
             emailSignIn:()->Unit = {},
             guestSignIn:() -> Unit = {}){
    val email = rememberSaveable{ mutableStateOf("") }
    val valid = email.value.isNotEmpty()
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sign in or create an account",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary.copy(0.6f),
            modifier = modifier.padding(vertical = 4.dp))

        EmailInput(email = email)

        ColoredButton(text = "Continue", width = 1f){
            //onclick
        }

        Text(text = "or",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary.copy(0.6f))

        ColorlessButton(
            text = "Sign in as guest",
            width = 1f,
        ){
            guestSignIn.invoke()
        }

    }
}


@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    onAction: KeyboardActions = KeyboardActions.Default
){
    OutlinedTextField(value =email.value ,
        onValueChange = {email.value = it},
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        label = { Text(text = "Email")},
        placeholder = { Text(text = "Email")},
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            autoCorrect = false),
        keyboardActions = onAction
        )
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
            modifier = modifier.padding(vertical = 8.dp))
    }
}
