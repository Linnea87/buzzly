package com.example.buzzly.viemodels

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult

class AuthViewModel : ViewModel() {

    val auth = Firebase.auth

    fun createAccount(email: String, password:String, callback: (Task<AuthResult>)-> Unit) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { resultTask ->
                callback(resultTask)
            }
    }
}
