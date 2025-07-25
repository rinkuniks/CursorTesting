package com.nikhil.cursortesting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.nikhil.cursortesting.navigation.AppNavigation
import com.nikhil.cursortesting.ui.theme.CursorTestingTheme
import com.nikhil.cursortesting.utils.PermissionRequestScreen

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		
		setContent {
			CursorTestingTheme {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					PermissionRequestScreen(context = this)
//					AppNavigation()
				}
			}
		}
	}
}
