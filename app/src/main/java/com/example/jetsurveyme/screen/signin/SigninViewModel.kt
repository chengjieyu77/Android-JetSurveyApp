package com.example.jetsurveyme.screen.signin

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetsurveyme.model.SignInState
import com.example.jetsurveyme.repository.AuthRepository
import com.example.jetsurveyme.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val repository: AuthRepository
):ViewModel(){
    private val auth:FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()

    fun loginUser(email:String,password:String) = viewModelScope.launch{
        repository.loginUser(email,password).collect{result->
            when(result){
                is Resource.Success -> {
                    _signInState.send(SignInState(isSuccess = "Sign in Success"))
                }
                is Resource.Loading ->{
                    _signInState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {
                    _signInState.send(SignInState(isError = result.message))
                }
            }
        }
    }

    fun registerUser(email: String,password: String) = viewModelScope.launch {
        repository.registerUser(email,password).collect{result->
            when(result){
                is Resource.Success -> {
                    _signInState.send(SignInState(isSuccess = "Register success"))
                }
                is Resource.Loading -> {
                    _signInState.send(SignInState(isLoading = true))
                }
                is Resource.Error -> {
                    _signInState.send(SignInState(isError = result.message))
                }
            }

        }
    }

//    private var _email =
//        mutableStateOf("")
//
//    private val email:String = _email.value
//
//    fun restoreEmail(email:String){
//        _email.value = email
//    }
//
//    fun getEmail():String{
//        return email
//    }
}