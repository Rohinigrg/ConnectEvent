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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.platform.LocalContext
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.callback.ErrorInfo


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
    val context = LocalContext.current

    val config = mapOf(
        "cloud_name" to "dfeo4xaob",
        "api_key" to "967334667241449",
        "api_secret" to "V_f0Kk59dN9oVGQIv6-Q19RXJgM"
    )

    try {
        MediaManager.init(context, config)
    } catch (e: Exception) {
    }

    val uid = FirebaseAuth.getInstance().currentUser?.uid

    val dbRef = FirebaseDatabase.getInstance().getReference("users").child(uid ?: "")

    var name by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    // 🔄 Load existing data
    LaunchedEffect(uid) {
        if (uid != null) {
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    name = snapshot.child("name").value?.toString() ?: ""
                    gender = snapshot.child("gender").value?.toString() ?: ""
                    dob = snapshot.child("dob").value?.toString() ?: ""
                    location = snapshot.child("location").value?.toString() ?: ""
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
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
        )

        {
            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Select Profile Image")
            }

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

                    if (uid != null) {

                        val updates = mapOf(
                            "name" to name,
                            "gender" to gender,
                            "dob" to dob,
                            "location" to location
                        )

                        dbRef.updateChildren(updates)

                        imageUri?.let { uri ->

                            MediaManager.get().upload(uri)
                                .callback(object : UploadCallback {

                                    override fun onSuccess(
                                        requestId: String?,
                                        resultData: Map<*, *>?
                                    ) {

                                        val imageUrl =
                                            resultData?.get("secure_url").toString()

                                        dbRef.child("imageUrl")
                                            .setValue(imageUrl)

                                        Toast.makeText(
                                            context,
                                            "Profile Updated Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        (context as ComponentActivity).finish()
                                    }

                                    override fun onError(
                                        requestId: String?,
                                        error: ErrorInfo?
                                    ) {
                                        Toast.makeText(
                                            context,
                                            "Image Upload Failed",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    override fun onStart(requestId: String?) {}
                                    override fun onProgress(
                                        requestId: String?,
                                        bytes: Long,
                                        totalBytes: Long
                                    ) {}
                                    override fun onReschedule(
                                        requestId: String?,
                                        error: ErrorInfo?
                                    ) {}
                                })
                                .dispatch()

                        } ?: run {
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

