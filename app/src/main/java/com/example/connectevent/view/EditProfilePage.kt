package com.example.connectevent.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectevent.view.ui.theme.ConnectEventTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.material3.ExperimentalMaterial3Api





class EditProfilePage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EditProfileScreen()

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(){
    val context = _root_ide_package_.androidx.compose.ui.platform.LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid

    val dbRef = FirebaseDatabase.getInstance().getReference("users").child(uid ?: "")

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    // 🔄 Load existing data
    LaunchedEffect(uid) {
        if (uid != null) {
            dbRef.get().addOnSuccessListener { snapshot ->
                name = snapshot.child("name").value?.toString() ?: ""
                gender = snapshot.child("gender").value?.toString() ?: ""
                dob = snapshot.child("dob").value?.toString() ?: ""
                location = snapshot.child("location").value?.toString() ?: ""
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Profile") },
                navigationIcon = {
                    IconButton(onClick = { (context as ComponentActivity).finish() }) {
                        Icon (
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text("Gender") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = dob,
                onValueChange = { dob = it },
                label = { Text("Date of Birth") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button  (
                onClick = {
                    Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show()
                    if (uid != null) {
                        val updates = mapOf(
                            "name" to name,
                            "gender" to gender,
                            "dob" to dob,
                            "location" to location
                        )

                        dbRef.updateChildren(updates)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "Profile updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                (context as ComponentActivity).finish()
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        }
    }

}

