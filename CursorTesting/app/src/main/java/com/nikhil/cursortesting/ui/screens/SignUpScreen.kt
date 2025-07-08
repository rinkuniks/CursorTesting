package com.nikhil.cursortesting.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikhil.cursortesting.ui.viewmodels.SignUpState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    onSignUpClick: (String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit,
    state: SignUpState,
    onSnackbarShown: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    var shouldNavigateToLogin by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            snackbarHostState.showSnackbar("Sign up successful!")
            name = ""
            email = ""
            password = ""
            confirmPassword = ""
            shouldNavigateToLogin = true
            onSnackbarShown()
        }
    }
    LaunchedEffect(shouldNavigateToLogin) {
        if (shouldNavigateToLogin) {
            onNavigateToLogin()
            shouldNavigateToLogin = false
        }
    }
    LaunchedEffect(state.error) {
        state.error?.let {
            snackbarHostState.showSnackbar(it)
            onSnackbarShown()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Button(
                onClick = {
                    if (password == confirmPassword) {
                        onSignUpClick(name, email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                } else {
                    Text("Sign Up")
                }
            }

            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Already have an account? ",
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyMedium)
                Text(
                    modifier = Modifier.clickable(onClick = onNavigateToLogin),
                    text = "Login",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
