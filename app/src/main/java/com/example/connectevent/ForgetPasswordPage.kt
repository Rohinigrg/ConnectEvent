package com.example.connectevent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectevent.repository.UserRepoImpl
import com.example.connectevent.ui.theme.ConnectEventTheme
import com.example.connectevent.ui.theme.White
import com.example.connectevent.viewmodel.UserViewModel

class ForgetPasswordPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ForgetPasswordScreen()

        }
    }
}

@Composable
fun ForgetPasswordScreen(){
    val context = LocalContext.current
    val userViewModel=remember { UserViewModel(UserRepoImpl()) }

    var email by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(24.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Image(
            painter = painterResource(id = R.drawable.registerbg),
            contentDescription = "Forgot Password",
            modifier = Modifier
                .height(220.dp)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Forgot password",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Email Label
        Text(
            text = "Email",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 15.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Email Field (Clean Style)
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text("Your email id")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Submit Button
        Button (
            onClick = {
                if (email.isBlank()) {
                    Toast.makeText(context, "Enter your email", Toast.LENGTH_SHORT).show()
            } else {
                userViewModel.forgetPassword(email.trim()) { success, message ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    if (success) {
                        // Navigate back to Login page after sending reset email
                        val intent = Intent(context, LoginPage::class.java)
                        context.startActivity(intent)
                    }
                }
            }
                // UI only – logic later
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 15.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(24.dp))
        TextButton (onClick = {
            val intent = Intent(context, LoginPage::class.java)
            context.startActivity(intent)       }) {
           Text(
               text = "Back to login",
               color = Color.Black
           )
       }
    }

}

@Preview
@Composable
fun ForgetPasswordPreview(){
   ForgetPasswordScreen()
}