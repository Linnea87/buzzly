package com.example.buzzly.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class UserRepository {

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    fun createUserProfile() {
        val user = auth.currentUser ?: return

        val data = User(
            uid = user.uid,
            email = user.email ?: "",
            displayName = user.email?.substringBefore("@") ?: "User"
        )

        db.collection("users")
            .document(user.uid)
            .set(data)
    }

    fun getAllUsers(onResult: (List<User>) -> Unit) {
        val currentUid = auth.currentUser?.uid ?: return

        db.collection("users")
            .get()
            .addOnSuccessListener { snapshot ->
                val users = snapshot.documents
                    .mapNotNull { it.toObject(User::class.java) }
                    .filter { it.uid != currentUid }

                onResult(users)
            }
    }
}
