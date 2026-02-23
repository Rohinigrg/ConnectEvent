package com.example.connectevent.view

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectevent.model.Event

class ViewEventPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ViewEventScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewEventScreen() {

    val context = LocalContext.current

    // 🔹 SAME EVENTS AS JOIN PAGE
    val events = listOf(
        Event("1", "Cleanliness Program", "Community cleaning drive. The program usually begins with an awareness session" +
                " where participants are educated about the importance of cleanliness, proper waste management, recycling, and maintaining personal hygiene. " +
            "Kathmandu", "12 March 2026"),
        Event("2", "Music Program", "Live music event. The program includes various performances such as singing, instrumental music, band performances," +
                " and cultural songs. It brings people together, promotes unity, and creates a joyful atmosphere." +
                " among performers.", "Baneshwor", "18 March 2026"),
        Event("3", "Tech Meetup", "Discussion on new technologies. During the event, experts and speakers present on topics such as software development, artificial" +
                " intelligence, cybersecurity, mobile app development, and emerging technologies.", "Lalitpur", "22 March 2026"),
        Event("3", "Blood Donation", "Discussion on new technologies. The event is conducted in collaboration with healthcare professionals who ensure safe and hygienic blood collection." +
                " Donors are given proper guidance, health check-ups, and refreshments after donation.", "Lalitpur", "22 March 2026"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("View Events") },
                navigationIcon = {
                    IconButton(onClick = {
                        (context as? Activity)?.finish()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(events) { event ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = event.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(event.description)
                        Text("📍 ${event.location}")
                        Text("📅 ${event.date}")
                    }
                }
            }
        }
    }
}
