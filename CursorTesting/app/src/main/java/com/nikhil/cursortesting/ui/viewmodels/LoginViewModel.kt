package com.nikhil.cursortesting.ui.viewmodels

import android.content.*
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.nikhil.cursortesting.data.*
import com.nikhil.cursortesting.utils.*
import kotlinx.coroutines.*
import java.util.regex.*

class LoginViewModel(private val userDao: UserDao) : ViewModel() {
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
	
	fun login(context: Context) {
		isLoading = true
		errorMessage = null
		loginSuccess = false
		viewModelScope.launch {
			val emailPattern = Pattern.compile(
				"^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
			)
			if (!emailPattern.matcher(email).matches()) {
				errorMessage = "Invalid email format"
				loginSuccess = false
			} else {
				val user = userDao.getUserByEmailAndPassword(email, password)
				if (user != null) {
					errorMessage = null
					loginSuccess = true
					LoginPrefs.setLogin(context, email)
				} else {
					errorMessage = "Invalid credentials"
					loginSuccess = false
				}
			}
			isLoading = false
		}
	}
	
	fun resetLoginSuccess() {
		loginSuccess = false
	}
} 