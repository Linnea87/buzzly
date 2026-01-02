package com.example.buzzly.viemodels

import android.content.Intent
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

    fun signIn(email: String, password:String, onSuccess: ()-> Unit, onFailure: (Exception)-> Unit){

        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it)
        }
    }


}
