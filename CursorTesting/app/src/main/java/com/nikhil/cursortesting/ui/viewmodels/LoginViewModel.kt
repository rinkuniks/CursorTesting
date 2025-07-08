package com.nikhil.cursortesting.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var loginSuccess by mutableStateOf(false)

    fun onEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
    }

    fun login() {
        isLoading = true
        errorMessage = null
        loginSuccess = false
        viewModelScope.launch {
            delay(1500) // Simulate network
            val emailPattern = Pattern.compile(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
            )
            if (!emailPattern.matcher(email).matches()) {
                errorMessage = "Invalid email format"
                loginSuccess = false
            } else {
                errorMessage = null // Success: valid email, any password
                loginSuccess = true
            }
            isLoading = false
        }
    }

    fun resetLoginSuccess() {
        loginSuccess = false
    }
} 