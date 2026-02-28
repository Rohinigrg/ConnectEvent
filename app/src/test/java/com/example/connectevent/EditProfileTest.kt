package com.example.connectevent

import com.example.connectevent.model.UserModel
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

class EditProfileTest {
    @Test
    fun editProfile_success_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val userId = "user123"
        val userModel = UserModel(
            id = userId,
            name = "Rohini",
            email = "rohin@example.com",
            gender = "Female",
            dob = "2000-01-01",
            location = "Kathmandu"
        )

        // Mock repo to simulate success callback
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Profile updated successfully")
            null
        }.`when`(repo).editProfile(eq(userId), eq(userModel), any())

        var resultSuccess = false
        var resultMessage = ""

        // Call ViewModel
        viewModel.editProfile(userId, userModel) { success, msg ->
            resultSuccess = success
            resultMessage = msg
        }

        // Assertions
        assertTrue(resultSuccess)
        assertEquals("Profile updated successfully", resultMessage)

        // Verify repo call
        verify(repo).editProfile(eq(userId), eq(userModel), any())
    }

    @Test
    fun editProfile_failure_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val userId = "user123"
        val userModel = UserModel(
            id = userId,
            name = "", // empty name to simulate failure
            email = "rohin@example.com",
            gender = "Female",
            dob = "2000-01-01",
            location = "Kathmandu"
        )

        // Mock repo to simulate failure callback
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(false, "Failed to update profile")
            null
        }.`when`(repo).editProfile(eq(userId), eq(userModel), any())

        var resultSuccess = true
        var resultMessage = ""

        // Call ViewModel
        viewModel.editProfile(userId, userModel) { success, msg ->
            resultSuccess = success
            resultMessage = msg
        }

        // Assertions
        assertFalse(resultSuccess)
        assertEquals("Failed to update profile", resultMessage)

        // Verify repo call
        verify(repo).editProfile(eq(userId), eq(userModel), any())
    }
}