package com.nikhil.cursortesting.ui.viewmodels

import androidx.lifecycle.*
import com.nikhil.cursortesting.data.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

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