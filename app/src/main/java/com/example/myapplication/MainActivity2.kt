package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                SalesDashboard(onNavigate = {
                    startActivity(Intent(this, MainActivity::class.java))
                })
            }
        }
    }
}

@Composable
fun SalesDashboard(onNavigate: () -> Unit) {

    val products = listOf(
        Triple("🥤 Coca Cola", "Beverage", 15),
        Triple("🍜 Instant Noodles", "Food", 12),
        Triple("☕ Coffee 3-in-1", "Beverage", 8),
        Triple("🍞 White Bread", "Food", 45),
        Triple("🥔 Potato Chips", "Food", 18),
        Triple("🧴 Shampoo Sachet", "Household", 5),
        Triple("🍚 Rice", "Food", 50),
        Triple("🥤 Soft Drinks", "Beverage", 20)
    )

    val cart = remember { mutableStateListOf<Pair<String, Int>>() }
    var amountReceived by remember { mutableStateOf("") }
    var showChangeDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var showAddCustomerDialog by remember { mutableStateOf(false) }
    var customerName by remember { mutableStateOf("") }
    var customerContact by remember { mutableStateOf("") }
    var currentCustomer by remember { mutableStateOf<String?>(null) }
    var selectedFilter by remember { mutableStateOf("All") }
    var selectedCategory by remember { mutableStateOf("All categories") }
    var categoryDropdownExpanded by remember { mutableStateOf(false) }
    val baseFiltered = when (selectedFilter) {
        "Low Stock" -> products.filter { it.third < 10 }
        "Favorites" -> products.filter { it.first in listOf("🍜 Instant Noodles", "🥤 Coca Cola") }
        "Recent" -> cart.mapNotNull { c -> products.find { it.first == c.first } }
        else -> products
    }

    val filteredProducts = if (selectedCategory == "All categories") {
        baseFiltered
    } else {
        baseFiltered.filter { it.second == selectedCategory }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC7CDD7))
            .verticalScroll(rememberScrollState())
    ) {
        // Top Navbar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2563EB))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Logo on the left
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("SS", color = Color(0xFF2563EB), fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(8.dp))

            }

            // Navigation items on the right
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                NavbarItem("Home")
                NavbarItem("Features")

                var expanded by remember { mutableStateOf(false) }

                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Color.White)
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Analytics") },
                            onClick = { expanded = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Inventory") },
                            onClick = { expanded = false }
                        )
                    }
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Products Grid
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    OutlinedButton(onClick = { categoryDropdownExpanded = true }) {
                        Text(selectedCategory)
                    }

                    DropdownMenu(
                        expanded = categoryDropdownExpanded,
                        onDismissRequest = { categoryDropdownExpanded = false }
                    ) {
                        listOf("All categories", "Beverage", "Food", "Household").forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    selectedFilter = "All" // reset other filter
                                    categoryDropdownExpanded = false
                                }
                            )

                        }
                    }
                }

                Button(onClick = { /* Add Product */ }) {
                    Text("+ Add Product")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(400.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(filteredProducts) { (name, category, price) ->
                Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clickable {
                                val index = cart.indexOfFirst { it.first == name }
                                if (index >= 0) {
                                    val (n, q) = cart[index]
                                    cart[index] = n to q + 1
                                } else {
                                    cart.add(name to 1)
                                }
                            },
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text(category, color = Color.Gray, fontSize = 12.sp)
                            Text("₱$price", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                        }
                    }
                }//OutlinedTextField
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf( "Low Stock", "Favorites", "Recent").forEach { filter ->
                    FilterButton(
                        label = filter,
                        selected = selectedFilter == filter,
                        onClick = { selectedFilter = filter }
                    )
                }
            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        // Cart & Sale Summary
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Current Sale", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            LazyColumn(modifier = Modifier.height(150.dp)) {
                items(cart) { (itemName, qty) ->
                    val price = products.find { it.first == itemName }?.third ?: 0
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text("$itemName", modifier = Modifier.weight(1f))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(onClick = {
                                val index = cart.indexOfFirst { it.first == itemName }
                                if (index >= 0) {
                                    val newQty = qty - 1
                                    if (newQty <= 0) cart.removeAt(index)
                                    else cart[index] = itemName to newQty
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Decrease")
                            }
                            Text(qty.toString(), fontWeight = FontWeight.Bold)
                            IconButton(onClick = {
                                val index = cart.indexOfFirst { it.first == itemName }
                                if (index >= 0) cart[index] = itemName to qty + 1
                            }) {
                                Icon(Icons.Default.Add, contentDescription = "Increase")
                            }
                            IconButton(onClick = {
                                cart.removeIf { it.first == itemName }
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Remove")
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("₱${qty * price}")
                    }
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            val subtotal = cart.sumOf { (name, qty) ->
                val price = products.find { it.first == name }?.third ?: 0
                qty * price
            }
            val tax = subtotal * 0.12
            val total = subtotal + tax

            Text("Subtotal: ₱$subtotal")
            Text("Tax (12%): ₱${"%.2f".format(tax)}")
            Text("Total: ₱${"%.2f".format(total)}", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                PaymentMethodButton("Cash")
                PaymentMethodButton("Card")
                PaymentMethodButton("GCash")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = amountReceived,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        amountReceived = newValue
                    }
                },
                label = { Text("Amount Received") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                listOf(20, 50, 100).forEach {
                    Button(onClick = {
                        amountReceived = ((amountReceived.toIntOrNull() ?: 0) + it).toString()

                    }) {
                        Text("₱$it")
                    }
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                listOf(200, 500).forEach {
                    Button(onClick = {
                        amountReceived = ((amountReceived.toIntOrNull() ?: 0) + it).toString()

                    }) {
                        Text("₱$it")
                    }
                }
                Button(onClick = {
                    amountReceived = total.toInt().toString() // use total from your calculation
                }) {
                    Text("Exact")
                }
            }
            //selectedFilter

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if ((amountReceived.toDoubleOrNull() ?: 0.0) >= total) {
                        showChangeDialog = true

                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Complete Sale")
            }
            val change = (amountReceived.toDoubleOrNull() ?: 0.0) - total

            if (showChangeDialog) {
                ReceiptDialog(
                    cart = cart,
                    total = total,
                    amountReceived = amountReceived,
                    onDismiss = { showChangeDialog = false },
                    currentCustomer = currentCustomer // 👈 PASS IT HERE
                )

            }




            Button(onClick = { showAddCustomerDialog = true }) {
                Text("Add Customer")
            }

            OutlinedButton(onClick = { /* Add Discount */ }) {
                Text("Add Discount")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(onClick = onNavigate) {
                Text("Go to Profile Page")

            }
            if (showAddCustomerDialog) {
                AlertDialog(
                    onDismissRequest = { showAddCustomerDialog = false },
                    title = { Text("Add Customer") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = customerName,
                                onValueChange = { customerName = it },
                                label = { Text("Customer Name") },
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = customerContact,
                                onValueChange = { customerContact = it },
                                label = { Text("Contact Info") },
                                singleLine = true
                            )
                        }
                    },
                    confirmButton = {
                        Button(onClick = {
                            if (customerName.isNotBlank()) {
                                currentCustomer = "$customerName (${customerContact})"
                                showAddCustomerDialog = false
                            }
                        }) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showAddCustomerDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

        }
    }
}

@Composable
fun NavbarItem(label: String, isSelected: Boolean = false, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = if (isSelected) Color.White else Color.LightGray,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun FilterButton(label: String, selected: Boolean, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (selected) Color(0xFF2563EB) else Color.Transparent,
            contentColor = if (selected) Color.White else Color.Black
        )
    ) {
        Text(label)
    }
}

@Composable
fun PaymentMethodButton(method: String) {
    Button(onClick = { /* TODO */ }) {
        Text(method)
    }
}

@Preview(showBackground = true)
@Composable
fun SalesDashboardPreview() {
    MyApplicationTheme {
        SalesDashboard(onNavigate = {})
    }
}

@Composable
fun ReceiptDialog(
    cart: List<Pair<String, Int>>,
    total: Double,
    amountReceived: String,
    onDismiss: () -> Unit,
    currentCustomer: String?
) {
    val tax = total * 0.12
    val subtotal = total - tax
    val change = (amountReceived.toDoubleOrNull() ?: 0.0) - total

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("SARISARI STORE", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Your Neighborhood Store")
                Text("123 Main Street, Barangay Centro")
                Text("Tel: (02) 123-4567")

                Spacer(modifier = Modifier.height(12.dp))

                Text("Transaction #: 1003")

                val currentDateTime = java.util.Date()
                val dateFormat = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.getDefault())
                val timeFormat = java.text.SimpleDateFormat("hh:mm:ss a", java.util.Locale.getDefault())

                val formattedDate = dateFormat.format(currentDateTime)
                val formattedTime = timeFormat.format(currentDateTime)

                Text("Date: $formattedDate")
                Text("Time: $formattedTime")
                Text("Cashier: Admin")
                if (currentCustomer != null) {
                    Text("Customer: $currentCustomer", fontWeight = FontWeight.Medium)
                }


                Divider(modifier = Modifier.padding(vertical = 4.dp))

                cart.forEach { (name, quantity) ->
                    val unitPrice = when (name) {
                        "🥤 Coca Cola" -> 15.0
                        "🍜 Instant Noodles" -> 12.0
                        "☕ Coffee 3-in-1" -> 8.0
                        "🍞 White Bread" -> 45.0
                        "🥔 Potato Chips" -> 18.0
                        "🧴 Shampoo Sachet" -> 5.0
                        "🍚 Rice" -> 50.0
                        "🥤 Soft Drinks" -> 20.0
                        else -> 10.0
                    }
                    val totalPrice = unitPrice * quantity
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(name.replace(Regex("[^A-Za-z ]"), ""))
                        Text("$quantity x ₱${"%.2f".format(unitPrice)} = ₱${"%.2f".format(totalPrice)}")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Divider()

                Text("Subtotal: ₱${"%.2f".format(subtotal)}")
                Text("VAT (12%): ₱${"%.2f".format(tax)}")

                Divider(modifier = Modifier.padding(vertical = 4.dp))

                Text("TOTAL: ₱${"%.2f".format(total)}", fontWeight = FontWeight.Bold)
                Text("Cash: ₱${"%.2f".format(amountReceived.toDoubleOrNull() ?: 0.0)}")
                Text("Change: ₱${"%.2f".format(change)}", fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(8.dp))

                Text("Thank you for your purchase!", fontStyle = FontStyle.Italic)
                Text("Please come again!")
            }
        }
    )
}