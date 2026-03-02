package com.example.connectevent

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.Intents.intended
import com.example.connectevent.view.LoginPage
import com.example.connectevent.view.DashboardPage
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<LoginPage>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testLoginNavigatesToDashboard() {

        // Enter Email
        composeRule.onNodeWithTag("email")
            .assertIsDisplayed()
            .performTextInput("rohinigrg339@gmail.com")

        // Enter Password
        composeRule.onNodeWithTag("password")
            .assertIsDisplayed()
            .performTextInput("rohini123")

        // Click Login Button
        composeRule.onNodeWithTag("login")
            .assertIsDisplayed()
            .performClick()

        // WAIT for Firebase async call to complete
        composeRule.waitForIdle()
        Thread.sleep(4000)   // Important because Firebase is async

        // Verify navigation happened
        composeRule.onNodeWithTag("dashboardScreen")
            .assertIsDisplayed()    }
}