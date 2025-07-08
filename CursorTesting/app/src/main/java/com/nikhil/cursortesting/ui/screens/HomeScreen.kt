package com.nikhil.cursortesting.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import java.text.NumberFormat
import java.util.*
import java.util.Collections.emptyList
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikhil.cursortesting.data.AppDatabase
import com.nikhil.cursortesting.utils.LoginPrefs
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.launch

// Placeholder data models

data class Product(val id: Int, val name: String, val price: Double?, val imageRes: Int, val isGiveaway: Boolean = false)
data class CartItem(val product: Product)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToTextSizeDemo: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val userDao = db.userDao()
    val email by LoginPrefs.getUserEmailFlow(context).collectAsState(initial = null)
    var userName by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(email) {
        if (email != null) {
            scope.launch {
                val user = userDao.getUserByEmail(email!!)
                userName = user?.name ?: ""
            }
        }
    }

    var items by remember { mutableStateOf(emptyList<Item>()) }
    var newItemName by remember { mutableStateOf("") }
    var newItemPrice by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val total = items.sumOf { it.price }
    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale("en", "IN")) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with navigation button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (userName.isNotBlank()) "Hello, $userName!" else "My App",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            onNavigateToTextSizeDemo?.let { navigate ->
                Button(
                    onClick = navigate,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text("Text Size Demo")
                }
            }
        }

        // Main content area
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Left side (70%) - Add Item Card
            Card(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxHeight()
                    .padding(end = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Add New Item",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = newItemName,
                        onValueChange = { 
                            newItemName = it
                            showError = false
                        },
                        label = { Text("Item Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next
                        ),
                        isError = showError && newItemName.isBlank()
                    )

                    OutlinedTextField(
                        value = newItemPrice,
                        onValueChange = { 
                            if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                                newItemPrice = it
                                showError = false
                            }
                        },
                        label = { Text("Price") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = ImeAction.Done
                        ),
                        isError = showError && (newItemPrice.isBlank() || newItemPrice.toDoubleOrNull() == null)
                    )

                    if (showError) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Button(
                        onClick = {
                            when {
                                newItemName.isBlank() -> {
                                    showError = true
                                    errorMessage = "Please enter item name"
                                }
                                newItemPrice.isBlank() -> {
                                    showError = true
                                    errorMessage = "Please enter price"
                                }
                                newItemPrice.toDoubleOrNull() == null -> {
                                    showError = true
                                    errorMessage = "Please enter a valid price"
                                }
                                newItemPrice.toDoubleOrNull()!! <= 0 -> {
                                    showError = true
                                    errorMessage = "Price must be greater than 0"
                                }
                                else -> {
                                    items = items + Item(newItemName, newItemPrice.toDouble())
                                    newItemName = ""
                                    newItemPrice = ""
                                    showError = false
                                }
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Add Item")
                    }
                }
            }

            // Right side (30%) - Item List
            Card(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxHeight()
                    .padding(start = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        text = "Items List",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    if (items.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No items added yet",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(items) { item ->
                                Card(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(8.dp)
                                    ) {
                                        Text(
                                            text = item.name,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            text = currencyFormat.format(item.price),
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Total at bottom
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total:",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = currencyFormat.format(total),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Item(
    val name: String,
    val price: Double
)

@Composable
fun ProductCard(product: Product, onAdd: () -> Unit, columns: Int = 4) {
    val cardWidth = when (columns) {
        1 -> 220.dp
        2 -> 180.dp
        else -> 160.dp
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .width(cardWidth)
            .height(180.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.height(100.dp).fillMaxWidth()) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (product.isGiveaway) {
                    Box(
                        Modifier.align(Alignment.TopStart).padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFE6F7F2))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text("Give-away Item", color = Color(0xFF1BA784), fontSize = 12.sp)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(product.name, Modifier.padding(horizontal = 8.dp), fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(4.dp))
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.price?.let { "$${it}" } ?: "Free",
                    fontWeight = FontWeight.Bold,
                    color = if (product.price == null) Color(0xFF1BA784) else Color.Black
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onAdd) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_input_add),
                        contentDescription = "Add to cart"
                    )
                }
            }
        }
    }
}

@Composable
fun CartSummary(cart: List<CartItem>) {
    val subtotal = cart.sumOf { it.product.price ?: 0.0 }
    val loyaltyPoints = if (cart.isNotEmpty()) -5.0 else 0.0
    val tax = if (cart.isNotEmpty()) 1.0 else 0.0
    val total = subtotal + loyaltyPoints + tax

    Column(Modifier.fillMaxWidth()) {
        Text("Cart (${cart.size})", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                contentDescription = "VIP Member",
                modifier = Modifier.size(24.dp)
            )
            Text("  VIP Member", fontWeight = FontWeight.Medium)
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(Modifier.heightIn(max = 200.dp)) {
            items(cart) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Item")
                    Text("$10.00")
                }
            }
        }
        Divider(Modifier.padding(vertical = 8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Subtotal")
            Text("$${"%.1f".format(subtotal)}")
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Loyalty Points", color = Color(0xFF1BA784))
            Text("$${"%.1f".format(loyaltyPoints)}", color = Color(0xFF1BA784))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Tax")
            Text("$${"%.1f".format(tax)}")
        }
        Divider(Modifier.padding(vertical = 8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Total", fontWeight = FontWeight.Bold)
            Text("$${"%.1f".format(total)}", fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Charge $${"%.1f".format(total)}")
        }
    }
} 