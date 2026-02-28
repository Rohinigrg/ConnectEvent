package com.example.connectevent

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.connectevent.view.DashboardPage
import com.example.connectevent.view.LoginPage
import com.example.connectevent.view.LoginScreen
import com.example.connectevent.viewmodel.UserViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginInstrumentedTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        // Initialize Espresso Intents to capture outgoing Intents
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun loginButton_navigates_to_user_dashboard() {
        // Use FakeUserViewModel for testing
        composeRule.setContent {
            LoginScreen(userViewModel = UserViewModel(FakeUserRepo()))        }

        // Enter email
        composeRule.onNodeWithTag("email")
            .performTextInput("test@gmail.com")

        // Enter password
        composeRule.onNodeWithTag("password")
            .performTextInput("123456")

        // Click login button
        composeRule.onNodeWithTag("login")
            .performClick()

        // Now login always succeeds, so we can assert navigation
        Intents.intended(hasComponent(DashboardPage::class.java.name))
    }
}