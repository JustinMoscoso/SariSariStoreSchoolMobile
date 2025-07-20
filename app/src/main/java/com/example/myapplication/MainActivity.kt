package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ProfilePage(onNavigate = {
                    startActivity(Intent(this, MainActivity2::class.java))
                })
            }
        }
    }
}

@Composable
fun ProfilePage(onNavigate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC7CDD7)) // Page background
    ) {
        // Top Navbar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2563EB)) // Same blue as before
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo on top
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



        Spacer(modifier = Modifier.height(50.dp))

        // Profile Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Avatar
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SS",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF9FAFF)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "John Smith",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "john.smith@example.com",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "I’m a product designer passionate about crafting user-centered experiences that are both beautiful and functional.",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 32.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { /* handle edit */ }) {
                    Text("Edit Profile")
                }

                OutlinedButton(onClick = onNavigate) {
                    Text("Go to Sales")
                }
            }
        }
    }
}

@Composable
fun NavbarItem(label: String) {
    var isHovered by remember { mutableStateOf(false) }

    Text(
        text = label,
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif,
        color = if (isHovered) Color(175,205,252) else Color(249,250,255),
        modifier = Modifier
            .padding(8.dp)
            .clickable { /* handle nav */ }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewProfilePage() {
    MyApplicationTheme {
        ProfilePage(onNavigate = {})
    }
}
