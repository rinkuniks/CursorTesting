package com.nikhil.cursortesting.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.nikhil.cursortesting.data.User
import com.nikhil.cursortesting.data.UserDao

data class SignUpState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class SignUpViewModel(private val userDao: UserDao) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun signUp(name: String, email: String, password: String) {
        _state.value = SignUpState(isLoading = true)
        viewModelScope.launch {
            val existingUser = userDao.getUserByEmail(email)
            if (existingUser != null) {
                _state.value = SignUpState(error = "Email already registered")
            } else {
                userDao.insertUser(User(name = name, email = email, password = password))
                _state.value = SignUpState(isSuccess = true)
            }
        }
    }

    fun resetState() {
        _state.value = SignUpState()
    }
} 