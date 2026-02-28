package com.example.connectevent

import com.example.connectevent.repository.UserRepo
import com.example.connectevent.viewmodel.UserViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ForgetPasswordTest {
    @Test
    fun forgetPassword_success_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val email = "rohin@example.com"

        // Mock repo to simulate success callback
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Password reset email sent")
            null
        }.`when`(repo).forgetPassword(eq(email), any())

        var resultSuccess = false
        var resultMessage = ""

        // Call ViewModel
        viewModel.forgetPassword(email) { success, msg ->
            resultSuccess = success
            resultMessage = msg
        }

        // Assertions
        assertTrue(resultSuccess)
        assertEquals("Password reset email sent", resultMessage)

        // Verify repo call
        verify(repo).forgetPassword(eq(email), any())
    }

    @Test
    fun forgetPassword_failure_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val email = "invalid@example.com"

        // Mock repo to simulate failure callback
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(false, "Email not found")
            null
        }.`when`(repo).forgetPassword(eq(email), any())

        var resultSuccess = true
        var resultMessage = ""

        // Call ViewModel
        viewModel.forgetPassword(email) { success, msg ->
            resultSuccess = success
            resultMessage = msg
        }

        // Assertions
        assertFalse(resultSuccess)
        assertEquals("Email not found", resultMessage)

        // Verify repo call
        verify(repo).forgetPassword(eq(email), any())
    }
}

