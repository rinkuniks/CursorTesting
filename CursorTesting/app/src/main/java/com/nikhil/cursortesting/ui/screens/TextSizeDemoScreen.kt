package com.nikhil.cursortesting.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nikhil.cursortesting.utils.ResponsiveText
import com.nikhil.cursortesting.utils.Utils

@Composable
fun TextSizeDemoScreen() {
    val context = LocalContext.current
    var showDeviceInfo by remember { mutableStateOf(false) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Dynamic Text Size Demo",
                fontSize = ResponsiveText.headlineLarge().sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            Text(
                text = "Device: ${ResponsiveText.getDeviceCategory()}",
                fontSize = ResponsiveText.titleMedium().sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Button(
                onClick = { showDeviceInfo = !showDeviceInfo },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Toggle Device Info")
            }
        }
        
        if (showDeviceInfo) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = Utils.getDeviceInfo(context),
                        fontSize = ResponsiveText.bodySmall().sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
        
        // Predefined text sizes
        item {
            Text(
                text = "Predefined Text Sizes",
                fontSize = ResponsiveText.titleLarge().sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )
        }
        
        item {
            Text(
                text = "Headline Large",
                fontSize = ResponsiveText.headlineLarge().sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Text(
                text = "Headline Medium",
                fontSize = ResponsiveText.headlineMedium().sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Text(
                text = "Headline Small",
                fontSize = ResponsiveText.headlineSmall().sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Text(
                text = "Title Large",
                fontSize = ResponsiveText.titleLarge().sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        item {
            Text(
                text = "Title Medium",
                fontSize = ResponsiveText.titleMedium().sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        item {
            Text(
                text = "Title Small",
                fontSize = ResponsiveText.titleSmall().sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        item {
            Text(
                text = "Body Large - This is a sample body text with large size",
                fontSize = ResponsiveText.bodyLarge().sp
            )
        }
        
        item {
            Text(
                text = "Body Medium - This is a sample body text with medium size",
                fontSize = ResponsiveText.bodyMedium().sp
            )
        }
        
        item {
            Text(
                text = "Body Small - This is a sample body text with small size",
                fontSize = ResponsiveText.bodySmall().sp
            )
        }
        
        item {
            Text(
                text = "Label Large",
                fontSize = ResponsiveText.labelLarge().sp
            )
        }
        
        item {
            Text(
                text = "Label Medium",
                fontSize = ResponsiveText.labelMedium().sp
            )
        }
        
        item {
            Text(
                text = "Label Small",
                fontSize = ResponsiveText.labelSmall().sp
            )
        }
        
        // Custom text sizes
        item {
            Text(
                text = "Custom Text Sizes",
                fontSize = ResponsiveText.titleLarge().sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
        }
        
        item {
            Text(
                text = "Custom Size 18sp",
                fontSize = ResponsiveText.custom(18f).sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        item {
            Text(
                text = "Custom Size 20sp",
                fontSize = ResponsiveText.custom(20f).sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        item {
            Text(
                text = "Custom Size 26sp",
                fontSize = ResponsiveText.custom(26f).sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        // Different scaling methods
        item {
            Text(
                text = "Different Scaling Methods",
                fontSize = ResponsiveText.titleLarge().sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
        }
        
        item {
            Text(
                text = "Width-based scaling (16sp)",
                fontSize = ResponsiveText.byWidth(16f).sp
            )
        }
        
        item {
            Text(
                text = "Height-based scaling (16sp)",
                fontSize = ResponsiveText.byHeight(16f).sp
            )
        }
        
        item {
            Text(
                text = "Diagonal-based scaling (16sp)",
                fontSize = ResponsiveText.byDiagonal(16f).sp
            )
        }
        
        // Comparison section
        item {
            Text(
                text = "Size Comparison",
                fontSize = ResponsiveText.titleLarge().sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
            )
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Fixed 16sp:",
                    fontSize = 16.sp
                )
                Text(
                    text = "Dynamic 16sp:",
                    fontSize = ResponsiveText.custom(16f).sp
                )
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Fixed 20sp:",
                    fontSize = 20.sp
                )
                Text(
                    text = "Dynamic 20sp:",
                    fontSize = ResponsiveText.custom(20f).sp
                )
            }
        }
        
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Fixed 24sp:",
                    fontSize = 24.sp
                )
                Text(
                    text = "Dynamic 24sp:",
                    fontSize = ResponsiveText.custom(24f).sp
                )
            }
        }
        
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
} 