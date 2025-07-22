package com.nikhil.cursortesting.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.compose.*
import com.nikhil.cursortesting.data.*
import com.nikhil.cursortesting.ui.screens.*
import com.nikhil.cursortesting.ui.viewmodels.*
import com.nikhil.cursortesting.utils.*

sealed class Screen(val route: String) {
    object SignUp : Screen("signup")
    object Login : Screen("login")
    object LoginResponsive : Screen("loginResponsive")
    object Home : Screen("home")
    object TextSizeDemo : Screen("text-size_demo")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userDao = db.userDao()
    val signUpViewModel = viewModel<SignUpViewModel>(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return SignUpViewModel(userDao) as T
        }
    })
    val loginViewModel = viewModel<LoginViewModel>(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(userDao) as T
        }
    })

    val isLoggedIn by LoginPrefs.isLoggedInFlow(context).collectAsState(initial = null)
    if (isLoggedIn == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    val startDestination = if (isLoggedIn == true) Screen.Home.route else Screen.LoginResponsive.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Home.route) {
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
                },
                state = signUpViewModel.state.collectAsState().value,
                onSnackbarShown = { signUpViewModel.resetState() }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                email = loginViewModel.email,
                password = loginViewModel.password,
                onEmailChange = loginViewModel::onEmailChange,
                onPasswordChange = loginViewModel::onPasswordChange,
                onLoginClick = {
                    loginViewModel.login(context)
                },
                isLoading = loginViewModel.isLoading,
                errorMessage = loginViewModel.errorMessage,
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                }
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

        composable(Screen.LoginResponsive.route) {
            LoginScreenResponsive()
        }

        composable(Screen.TextSizeDemo.route) {
            TextSizeDemoScreen()
        }
    }
}
