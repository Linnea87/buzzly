package com.example.buzzly.data

import android.util.Log
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
        Log.d("CHAT_REPO", "Listening to messages for chatId=$chatId")

        db.collection("chatRooms")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    Log.e("CHAT_REPO", "Listener error", error)
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.mapNotNull {
                    it.toObject(Message::class.java)
                } ?: emptyList()

                Log.d("CHAT_REPO", "Messages size=${messages.size}")
                onMessagesChanged(messages)
            }
    }
}
