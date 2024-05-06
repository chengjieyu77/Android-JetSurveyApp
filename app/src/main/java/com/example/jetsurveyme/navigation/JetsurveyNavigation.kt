package com.example.jetsurveyme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetsurveyme.screen.login.LoginScreen
import com.example.jetsurveyme.screen.surveycontent.SurveyContentScreen

@Composable
fun JetsurveyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = JetsurveyScreens.LoginScreen.name){
        composable(JetsurveyScreens.LoginScreen.name){
            LoginScreen(navController = navController)
        }
        composable(JetsurveyScreens.SurveyContentScreen.name){
            SurveyContentScreen(navController = navController)
        }
    }
}