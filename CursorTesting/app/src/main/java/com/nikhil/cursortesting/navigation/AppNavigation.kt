package com.nikhil.cursortesting.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nikhil.cursortesting.ui.screens.HomeScreen
import com.nikhil.cursortesting.ui.screens.LoginScreen
import com.nikhil.cursortesting.ui.screens.SignUpScreen
import com.nikhil.cursortesting.ui.screens.TextSizeDemoScreen
import com.nikhil.cursortesting.ui.viewmodels.LoginViewModel
import com.nikhil.cursortesting.ui.viewmodels.SignUpViewModel
import androidx.compose.runtime.LaunchedEffect

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
    val loginViewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    NavHost(
        navController = navController,
        startDestination = "signup"
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
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                email = loginViewModel.email,
                password = loginViewModel.password,
                onEmailChange = loginViewModel::onEmailChange,
                onPasswordChange = loginViewModel::onPasswordChange,
                onLoginClick = {
                    loginViewModel.login()
                },
                isLoading = loginViewModel.isLoading,
                errorMessage = loginViewModel.errorMessage
            )
            LaunchedEffect(loginViewModel.loginSuccess) {
                if (loginViewModel.loginSuccess) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                    loginViewModel.resetLoginSuccess()
                }
            }
        }
        
        composable(Screen.TextSizeDemo.route) {
            TextSizeDemoScreen()
        }
    }
} 