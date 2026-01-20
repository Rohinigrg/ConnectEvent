package com.example.connectevent.view

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
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
import com.google.firebase.database.FirebaseDatabase
import com.example.connectevent.model.Event


class JoinEventPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JoinEventScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinEventScreen() {

    val context = LocalContext.current

    val events = listOf(
        Event("Cleanliness Program", "Community cleaning drive", "Kathmandu", "12 March 2026"),
        Event("Music Program", "Live music event", "Baneshwor", "18 March 2026"),
        Event("Tech Meetup", "Discussion on new technologies", "Lalitpur", "22 March 2026")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Join Events") },
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
                JoinEventCard(
                    event = event,
                    onJoinClick = {
                        Toast.makeText(
                            context,
                            "Joined ${event.title}",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Firebase join logic later
                        val userId = "user123" // later replace with FirebaseAuth UID
                        val db = FirebaseDatabase.getInstance()
                            .getReference("users")
                            .child(userId)
                            .child("joinedEvents")

                        db.push().setValue(event)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Event Joined", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed to join event", Toast.LENGTH_SHORT).show()
                            }
                    }
                )
            }
        }
    }
}

@Composable
fun JoinEventCard(
    event: Event,
    onJoinClick: () -> Unit
) {
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

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onJoinClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Join Event")
            }
        }
    }
}
