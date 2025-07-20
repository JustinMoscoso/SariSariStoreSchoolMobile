package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC7CDD7))
    ) {
        // Top Navbar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2563EB))
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Logo aligned to the left
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "SS",
                        color = Color(0xFF2563EB),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Navbar items under logo
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 9.dp)
            ) {
                NavbarItem("Home")
                NavbarItem("Features")
                NavbarItem("Analytics")
                NavbarItem("Inventory")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Main Dashboard Content
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Sales Dashboard",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2563EB)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Overview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Overview",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2563EB)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Total Revenue: ₱120,500", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text("Orders: 245", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                    Text("Top Product: Wireless Earbuds", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Analytics Cards
            Text(
                text = "Quick Analytics",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2563EB)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AnalyticsCard(title = "Today’s Sales", value = "₱8,400")
                AnalyticsCard(title = "New Orders", value = "16")
                AnalyticsCard(title = "Returning Users", value = "9")
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { /* Handle detailed report navigation */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("View Detailed Report")
            }

            OutlinedButton(onClick = onNavigate) {
                Text("Go to Profile Page")
            }
        }
    }
}

@Composable
fun AnalyticsCard(title: String, value: String) {
    Card(
        modifier = Modifier
            .width(110.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = title, fontSize = 12.sp, color = Color.DarkGray)
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SalesDashboardPreview() {
    MyApplicationTheme {
        SalesDashboard(onNavigate = {})
    }
}
