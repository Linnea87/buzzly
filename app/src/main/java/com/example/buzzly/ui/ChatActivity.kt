package com.example.buzzly.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.buzzly.R
import com.example.buzzly.databinding.ActivityChatBinding
import com.example.buzzly.viewmodels.ChatViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var chatViewModel: ChatViewModel
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = auth.currentUser

        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]

        if (user == null) {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmentChatContainer, ProfileFragment())
            addToBackStack(null)
            commit()
        }
    }
}
