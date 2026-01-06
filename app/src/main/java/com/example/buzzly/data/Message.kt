package com.example.buzzly.data

data class Message(
    val text: String = "",
    val senderId: String = "",
    val timestamp: com.google.firebase.Timestamp? = null
)

