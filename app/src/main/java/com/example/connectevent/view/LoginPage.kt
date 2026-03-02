package com.example.connectevent.view

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.connectevent.R
import com.example.connectevent.repository.UserRepoImpl
import com.example.connectevent.ui.theme.LightBlue
import com.example.connectevent.ui.theme.Skyblue
import com.example.connectevent.ui.theme.White
import com.example.connectevent.viewmodel.UserViewModel

class LoginPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginScreen()
        }
    }
}

@Composable
fun LoginScreen(
    userViewModel: UserViewModel = UserViewModel(UserRepoImpl())

) {

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visibility by remember { mutableStateOf(false) }

//    val isTest = androidx.test.platform.app.InstrumentationRegistry
//        .getInstrumentation().targetContext != null


    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(55.dp))
            Text(
                text = "ConnectEvent",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = LightBlue
            )
            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(R.drawable.backgroungimage),
                contentDescription = null,
                modifier = Modifier.height(200.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Reconnect with your community and discover event around you.",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 25.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))


            OutlinedTextField(
                value = email,
                onValueChange = { data ->
                    email = data
                },
                placeholder = {
                    Text(text = "Enter Your Email")
                },
                colors = with(TextFieldDefaults) {
                    colors(
                        unfocusedContainerColor = Skyblue,
                        focusedContainerColor = Skyblue,
                        focusedIndicatorColor = LightBlue,

                        unfocusedIndicatorColor = Color.Transparent
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .testTag("email")
            )
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { data ->
                    password = data
                },
                placeholder = {
                    Text("*******")
                },

                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Skyblue,
                    focusedContainerColor = Skyblue,
                    focusedIndicatorColor = LightBlue,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .testTag("password"),
                visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = {
                        visibility = !visibility
                    }) {
                        Icon(
                            painter = if (visibility)
                                painterResource(R.drawable.baseline_visibility_24)
                            else
                                painterResource(R.drawable.baseline_visibility_off_24),
                            contentDescription = null
                        )
                    }
                }

            )
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Forget Password?",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .clickable {
                        val intent = Intent(context, ForgetPasswordPage::class.java)
                        context.startActivity(intent)
                    },
                textAlign = TextAlign.End,
                color = Color.Black.copy(0.8f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(25.dp))
//            Button(
//                onClick = {
//                    if(email.isBlank() || password.isBlank()){
//                        Toast.makeText(context, "Email and password required", Toast.LENGTH_SHORT).show()
//                        return@Button
//                    }
//
//                    if(isTest){
//                        // TEST MODE: skip Firebase
//                        val intent = Intent(context, DashboardPage::class.java)
//                        context.startActivity(intent)
//                    } else {
//                        userViewModel.login(email.trim(), password.trim()){ success, message ->
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                            if(success){
//                                val intent = Intent(context, DashboardPage::class.java)
//                                context.startActivity(intent)
//                            }
//                        }
//                    }
//                },

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Email and password required", Toast.LENGTH_SHORT)
                            .show()
                        return@Button
                    }

                    // Remove the Instrumentation check
                    userViewModel.login(email.trim(), password.trim()) { success, message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        if (success) {
                            val intent = Intent(context, DashboardPage::class.java)
                            context.startActivity(intent)
                        }
                    }

                },

                modifier = Modifier
                    .width(300.dp)
                    .height(54.dp)
                    .testTag("login"),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1976D2)
                )
            ) {
                Text(
                    text = "Log In",
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Don’t have an account? Create one",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.clickable {
                    val intent = Intent(context, RegisterPage::class.java)
                    context.startActivity(intent)
                }
            )
        }
    }
}
@Preview
@Composable
fun LoginPreview(){
    LoginScreen()
}




