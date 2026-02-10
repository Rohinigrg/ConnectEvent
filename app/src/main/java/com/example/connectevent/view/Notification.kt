package com.example.connectevent.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectevent.model.NotificationUi
import com.example.connectevent.ui.theme.LightBlue
import com.example.connectevent.ui.theme.White
import com.example.connectevent.view.ui.theme.ConnectEventTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.DisposableEffect


class Notification : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotificationScreen()
        }
    }
}
@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(){
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val dbRef = FirebaseDatabase.getInstance().getReference("notifications").child(userId ?: "")

    var notifications by remember { mutableStateOf(listOf<NotificationUi>()) }
    var isLoading by remember { mutableStateOf(true) }
    val activity = LocalContext.current as? ComponentActivity


    // Fetch notifications from Firebase
    DisposableEffect(Unit) {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<NotificationUi>()
                for (child in snapshot.children) {
                    val notification = child.getValue(NotificationUi::class.java)
                    notification?.let { list.add(it) }
                }
                notifications = list.sortedByDescending { it.timestamp }
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                isLoading = false
            }
        }

        dbRef.addValueEventListener(listener)

        onDispose {
            dbRef.removeEventListener(listener)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Notifications", color = White, fontSize = 20.sp) },
                navigationIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = { activity?.finish() }
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = LightBlue
                )
            )


        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                notifications.isEmpty() -> Text(
                    "No notifications yet",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 16.sp
                )
                else -> LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(notifications) { notification ->
                        NotificationCard(notification)
                    }
                }

            }
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationUi) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = notification.title,
                fontSize = 16.sp,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = notification.message,
                fontSize = 14.sp,
                color = androidx.compose.ui.graphics.Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = android.text.format.DateFormat
                    .format("dd MMM, hh:mm a", notification.timestamp)
                    .toString(),
                fontSize = 12.sp,
                color = androidx.compose.ui.graphics.Color.LightGray
            )
        }
    }
}
