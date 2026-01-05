package com.example.buzzly.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class ChatViewModel : ViewModel()  {
    val db = Firebase.firestore
}