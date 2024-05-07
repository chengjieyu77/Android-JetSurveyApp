package com.example.jetsurveyme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetsurveyme.screen.finish.FinishScreen
import com.example.jetsurveyme.screen.login.LoginScreen
import com.example.jetsurveyme.screen.signin.SigninScreen
import com.example.jetsurveyme.screen.signin.SigninViewModel
import com.example.jetsurveyme.screen.surveycontent.SurveyContentScreen

@Composable
fun JetsurveyNavigation(){
    val navController = rememberNavController()
    val email = rememberSaveable{ mutableStateOf("") }
    NavHost(navController = navController, startDestination = JetsurveyScreens.LoginScreen.name){
        composable(JetsurveyScreens.LoginScreen.name){
            LoginScreen(navController = navController,
                email = email)
        }
        composable(JetsurveyScreens.SurveyContentScreen.name){
            SurveyContentScreen(navController = navController)
        }
        composable(JetsurveyScreens.SigninScreen.name){
            SigninScreen(navController = navController,
                email = email)
        }
        composable(JetsurveyScreens.FinishScreen.name){
            FinishScreen(navController = navController)
        }
    }
}