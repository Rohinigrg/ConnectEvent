package com.example.connectevent.view

import android.content.Context
import android.widget.Toast
import com.example.connectevent.model.Event
import com.google.firebase.database.FirebaseDatabase

fun joinEvent(context: Context, event: Event) {
    val userId = "go1LmTtUfWhL5ZRYfmMkAMcuWji1" // replace with FirebaseAuth later

    val dbRef = FirebaseDatabase.getInstance()
        .getReference("users")
        .child(userId)
        .child("joinedEvents")
        .child(event.id)

    dbRef.setValue(event)
        .addOnSuccessListener {
            Toast.makeText(context, "Event joined!", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener {
            Toast.makeText(context, "Failed to join event", Toast.LENGTH_SHORT).show()
        }
}