package com.nikhil.cursortesting.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikhil.cursortesting.ui.screens.HomeScreen
import com.nikhil.cursortesting.ui.screens.SignUpScreen
import com.nikhil.cursortesting.ui.screens.TextSizeDemoScreen
import com.nikhil.cursortesting.ui.viewmodels.SignUpViewModel

sealed class Screen(val route: String) {
    object SignUp : Screen("signup")
    object Login : Screen("login")
    object Home : Screen("home")
    object TextSizeDemo : Screen("textsize_demo")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val signUpViewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToTextSizeDemo = {
                    navController.navigate(Screen.TextSizeDemo.route)
                }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignUpClick = { name, email, password ->
                    signUpViewModel.signUp(name, email, password)
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Home.route)
                }
            )
        }
        
        composable(Screen.Login.route) {
            // TODO: Implement Login screen
        }
        
        composable(Screen.TextSizeDemo.route) {
            TextSizeDemoScreen()
        }
    }
} 