package com.example.jetsurveyme.component

import android.util.Patterns
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.jetsurveyme.R
import com.example.jetsurveyme.widget.EmailState
import com.example.jetsurveyme.widget.TextFieldState


@Composable
fun EmailInputSecond(
    emailState:TextFieldState = remember { EmailState() },
    imeAction: ImeAction = ImeAction.Next,
    onAction: ()->Unit
    ){
    OutlinedTextField(
        value = emailState.text,
        onValueChange = { emailState.text = it },
        label = {Text(text = "Email", style = MaterialTheme.typography.bodyMedium)},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .onFocusChanged { focusState ->
                emailState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.bodyMedium,
        isError = emailState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Email
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onAction()
            }
        ),
        singleLine = true,
        supportingText = {emailState.getError()?.let { error -> TextFieldError(textError = error) }})
}

@Composable
fun TextFieldError(textError: String) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.width(16.dp))
                Text(text = textError,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.error)
        }
}

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
                if (lastFocusState != state.isFocused) {
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
        trailingIcon ={
            IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                // Please provide localized description for accessibility services
                val description = if (passwordVisibility.value) "Show password" else "Hide password"
                if (passwordVisibility.value) Icon(painter = painterResource(id = R.drawable.visible), contentDescription = description ,
                    modifier = Modifier.size(25.dp))
                else Icon(painter = painterResource(id = R.drawable.hide) , contentDescription = description,
                    modifier = Modifier.size(25.dp))
            }
        },
        isError = !isPasswordConfirmed && passwordState.value.isNotEmpty() && isConfirmInput,
        supportingText = {
                         if (!isPasswordConfirmed && passwordState.value.isNotEmpty() && isConfirmInput){
                             Text(text = "Password does not accord to each other",
                                 style = MaterialTheme.typography.labelMedium,
                                 color = Color.Red.copy(0.8f))

                         }
        },
        enabled = enabled,
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
        keyboardActions = onAction,


    )

}




