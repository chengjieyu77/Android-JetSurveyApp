package com.example.jetsurveyme.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetsurveyme.screen.finish.FinishScreen
import com.example.jetsurveyme.screen.login.LoginScreen
import com.example.jetsurveyme.screen.login.WelcomeRoute
import com.example.jetsurveyme.screen.signin.SigninScreen
import com.example.jetsurveyme.screen.signup.SignUpScreen
import com.example.jetsurveyme.screen.surveycontent.SurveyContentScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JetsurveyNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = JetsurveyScreens.LoginScreen.name){
        composable(JetsurveyScreens.LoginScreen.name){
            WelcomeRoute(
                navigateToSignInScreen = {email->
                                         navController.navigate(JetsurveyScreens.SigninScreen.name + "/$email")
                    Log.d("current route",navController.currentBackStackEntry?.destination?.route.toString())
                },
                navigateToSignUpScreen = {email ->
                    navController.navigate(JetsurveyScreens.SignUpScreen.name + "/$email")
                    Log.d("current route",navController.currentBackStackEntry?.destination?.route.toString())
                },
                onSignInAsGuest = {
                    navController.navigate(JetsurveyScreens.SurveyContentScreen.name)
                    Log.d("current route",navController.currentBackStackEntry?.destination?.route.toString())
                })


        }
        composable(JetsurveyScreens.SurveyContentScreen.name){
            SurveyContentScreen(navController = navController)
        }

        val route = JetsurveyScreens.SigninScreen.name
        composable(
             "$route/{email}",
            arguments = listOf(navArgument("email"){
                type = NavType.StringType
            })
        ){navBack->
            navBack.arguments?.getString("email").let{
                email->
                SigninScreen(navController = navController,
                    email = email.toString())
                Log.d("email",email.toString())
                Log.d("current route",navController.currentBackStackEntry?.destination?.route.toString())
            }


        }


        composable(
            "${JetsurveyScreens.SignUpScreen.name}/{email}",
            arguments = listOf(navArgument("email"){type = NavType.StringType})
        ){navBack->
            navBack.arguments?.getString("email").let{
                email->
                SignUpScreen(navController = navController,
                    email = email.toString())
            }

        }
        composable(JetsurveyScreens.FinishScreen.name){
            FinishScreen(navController = navController)
        }
    }
}