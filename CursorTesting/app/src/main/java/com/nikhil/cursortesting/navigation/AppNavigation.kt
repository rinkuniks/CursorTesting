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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikhil.cursortesting.data.AppDatabase
import androidx.compose.runtime.collectAsState
import com.nikhil.cursortesting.utils.LoginPrefs
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

sealed class Screen(val route: String) {
    object SignUp : Screen("signup")
    object Login : Screen("login")
    object Home : Screen("home")
    object TextSizeDemo : Screen("textsize_demo")
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
    val startDestination = if (isLoggedIn == true) Screen.Home.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
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
        
        composable(Screen.TextSizeDemo.route) {
            TextSizeDemoScreen()
        }
    }
} 