package com.example.connectevent

import com.example.connectevent.model.UserModel
import com.example.connectevent.repository.UserRepo

class FakeUserRepo: UserRepo {
    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        callback(true, "Login Success (Fake)")
    }

    override fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        callback(true, "Register Success (Fake)", "fakeUserId")
    }

    override fun addUserToDatabase(userId: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        callback(true, "User added (Fake)")
    }

    override fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        callback(true, "Password reset sent (Fake)")
    }

    override fun deleteAccount(userId: String, callback: (Boolean, String) -> Unit) {
        callback(true, "Account deleted (Fake)")
    }

    override fun editProfile(userId: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        callback(true, "Profile updated (Fake)")
    }

    override fun getUserById(userId: String, callback: (Boolean, String, UserModel?) -> Unit) {
        callback(true, "User fetched (Fake)", null)
    }

    override fun getAllUser(callback: (Boolean, String, List<UserModel>?) -> Unit) {
        callback(true, "All users fetched (Fake)", emptyList())
    }

}