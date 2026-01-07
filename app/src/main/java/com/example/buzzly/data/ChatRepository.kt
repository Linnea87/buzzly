package com.example.buzzly.data

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore

class ChatRepository {

    private val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    private fun getCurrentUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")
    }

    fun createChatWith(
        otherUserId: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit = {}
    ) {
        val currentUserId = getCurrentUserId()
        val chatRef = db.collection("chatRooms").document()
        val chatId = chatRef.id

        val chatData = mapOf(
            "participants" to listOf(currentUserId, otherUserId),
            "createdAt" to FieldValue.serverTimestamp()
        )

        chatRef
            .set(chatData)
            .addOnSuccessListener {
                onSuccess(chatId)
            }
            .addOnFailureListener {
                onFailure(it)
            }
    }

    fun sendMessage(chatId: String, message: Message) {
        val data = hashMapOf(
            "text" to message.text,
            "senderId" to message.senderId,
            "timestamp" to FieldValue.serverTimestamp()
        )

        db.collection("chatRooms")
            .document(chatId)
            .collection("messages")
            .add(data)
    }

    fun listenToMessages(
        chatId: String,
        onMessagesChanged: (List<Message>) -> Unit
    ) {
        db.collection("chatRooms")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->

                if (error != null || snapshot == null) return@addSnapshotListener

                val messages = snapshot.documents.mapNotNull {
                    it.toObject(Message::class.java)
                }

                onMessagesChanged(messages)
            }
    }
}
