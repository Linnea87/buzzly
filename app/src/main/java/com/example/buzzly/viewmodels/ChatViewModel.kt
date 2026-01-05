package com.example.buzzly.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class ChatViewModel : ViewModel()  {
    val db = Firebase.firestore


    fun sendMessage(roomId: String, text: String, senderId: String) {
        val data = hashMapOf(
            "text" to text,
            "senderId" to senderId,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("chatRooms")
            .document(roomId)
            .collection("messages")
            .add(data)
    }

}