package com.example.connectevent.model

data class UserModel(
    val id: String="",
    val name: String="",
    val gender: String="",
    val dob: String="",
    val email: String="",
    val location: String=""
){
fun toMap(): Map<String,Any>{
    return mapOf(
        "id" to "id",
        "name" to "name",
        "gender" to "gender",
        "dob" to "dob",
        " email" to " email",
        "location" to "location"
    )
}}
