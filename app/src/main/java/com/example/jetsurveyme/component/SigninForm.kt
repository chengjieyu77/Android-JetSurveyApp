package com.example.jetsurveyme.component

import android.util.Patterns
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    enabled: Boolean = true,
    emailValid:Boolean = true,
    onTextFieldFocus:(Boolean) -> Unit = {},
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default
){
    var lastFocusState by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(value =email.value ,
        onValueChange = {email.value = it},
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .onFocusChanged { state ->
                if (lastFocusState != state.isFocused){
                    onTextFieldFocus(state.isFocused)
                }
                lastFocusState = state.isFocused
            },
        label = { Text(text = "Email") },
        enabled = enabled,
        isError = !emailValid && email.value.isNotEmpty(),
        //placeholder = { Text(text = "Email") },
        supportingText = {
                         if (!emailValid && email.value.isNotEmpty()){
                             Text(text = "Invalid email:${email.value}",
                                 style = MaterialTheme.typography.labelMedium,
                                 color = Color.Red.copy(0.8f))
                         }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction,
            autoCorrect = false),
        keyboardActions = onAction
    )
}


@Composable
fun PasswordInput(modifier: Modifier,
                  passwordState: MutableState<String>,
                  labelId: String,
                  enabled: Boolean,
                  trailingIcon: @Composable () -> Unit,
                  passwordVisibility: MutableState<Boolean>,
                  isConfirmInput:Boolean = false,
                  isPasswordConfirmed:Boolean = false,
                  imeAction: ImeAction = ImeAction.Done,
                  onAction: KeyboardActions = KeyboardActions.Default) {
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId)},
        trailingIcon = trailingIcon,
        isError = !isPasswordConfirmed && passwordState.value.isNotEmpty() && isConfirmInput,
        supportingText = {
                         if (!isPasswordConfirmed && passwordState.value.isNotEmpty() && isConfirmInput){
                             Text(text = "Password does not accord to each other",
                                 style = MaterialTheme.typography.labelMedium,
                                 color = Color.Red.copy(0.8f))

                         }
        },
        enabled = enabled,
        //singleLine = true,
        //textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        //trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)},
        keyboardActions = onAction,


    )

}




