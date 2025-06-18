package com.nikhil.cursortesting.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

data class SignUpState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class SignUpViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun signUp(name: String, email: String, password: String) {
        // TODO: Implement actual sign-up logic here
        // For now, we'll just simulate a successful sign-up
        _state.value = SignUpState(isLoading = true)
        
        viewModelScope.launch {
            delay(1000)
            _state.value = SignUpState(isSuccess = true)
        }
    }

    fun resetState() {
        _state.value = SignUpState()
    }
} 