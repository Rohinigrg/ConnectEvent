package com.example.connectevent.model

data class UserModel(
    val id: String="",
    val firstname: String="",
    val lastname: String="",
    val gender: String="",
    val dob: String="",
    val email: String="",
){
fun toMap(): Map<String,Any>{
    return mapOf(
        "id" to "id",
        "firstname" to "firstname",
        "lastname" to "lastname",
        "gender" to "gender",
        "dob" to "dob",
        " email" to " email"
    )
}}
