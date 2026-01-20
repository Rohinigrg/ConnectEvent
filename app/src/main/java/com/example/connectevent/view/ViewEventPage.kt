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
        Event("Cleanliness Program", "Community cleaning drive", "Kathmandu", "12 March 2026"),
        Event("Music Program", "Live music event", "Baneshwor", "18 March 2026"),
        Event("Tech Meetup", "Discussion on new technologies", "Lalitpur", "22 March 2026")
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
