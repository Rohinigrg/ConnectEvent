package com.example.connectevent

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectevent.model.UserModel
import com.example.connectevent.repository.UserRepoImpl
import com.example.connectevent.ui.theme.Blue
import com.example.connectevent.ui.theme.ConnectEventTheme
import com.example.connectevent.ui.theme.White
import com.example.connectevent.viewmodel.UserViewModel

class RegisterPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
          RegisterScreen()
        }
    }
}

@Composable
fun RegisterScreen(){
    val context = LocalContext.current
    val userViewModel=remember { UserViewModel(UserRepoImpl()) }

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Join Your Events",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E88E5)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.registerbg),
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Create an account to discover, join, and manage community events",
            fontSize = 14.sp,
            color = Color.Gray,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 30.dp)

        )

        Spacer(modifier = Modifier.height(26.dp))
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = {
                Text("Full Name")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            singleLine = true
        )


        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text("Email Address")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = {
                Text("Password")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            singleLine = true,
            visualTransformation = if (passwordVisibility)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = if (passwordVisibility)
                            painterResource(R.drawable.baseline_visibility_off_24)
                        else
                            painterResource(R.drawable.baseline_visibility_24),
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = {
                Text("Confirm Password")
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            singleLine = true,
            visualTransformation = if (confirmPasswordVisibility)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    confirmPasswordVisibility = !confirmPasswordVisibility
                }) {
                    Icon(
                        painter = if (confirmPasswordVisibility)
                            painterResource(R.drawable.baseline_visibility_off_24)
                        else
                            painterResource(R.drawable.baseline_visibility_24),
                        contentDescription = null
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))

        Button (
            onClick = {
                if (fullName.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (password != confirmPassword) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                userViewModel.register(email.trim(), password.trim()) { success, message, userId ->
                    if (success) {
                        val user = UserModel(
                            id = userId,
                            firstname = fullName.trim(),
                            email = email.trim()
                        )

                        userViewModel.addUserToDatabase(userId, user) { dbSuccess, dbMessage ->
                            Toast.makeText(context, dbMessage, Toast.LENGTH_SHORT).show()
                            if (dbSuccess) {
                                context.startActivity(
                                    Intent(context, LoginPage::class.java)
                                )
                            }
                        }
                    } else {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            )
        ) {
            Text(
                text = "Create Account",
                fontSize = 16.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Already a member? Login",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.clickable{
                val intent = Intent(context, LoginPage::class.java)
                context.startActivity(intent)
            }
        )
    }
}
@Preview
@Composable
fun RegisterPreview(){
    RegisterScreen()
}