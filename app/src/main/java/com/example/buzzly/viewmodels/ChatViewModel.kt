package com.example.buzzly.viewmodels

import androidx.lifecycle.ViewModel
import com.example.buzzly.data.Message
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class ChatViewModel : ViewModel()  {
    val db = Firebase.firestore

    fun sendMessage(roomId: String, message: Message) {
        val data = hashMapOf(
            "text" to message.text,
            "senderId" to message.senderId,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("chatRooms")
            .document(roomId)
            .collection("messages")
            .add(data)
    }
}