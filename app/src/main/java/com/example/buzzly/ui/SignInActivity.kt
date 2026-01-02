package com.example.buzzly.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.buzzly.R
import com.example.buzzly.databinding.ActivitySignInBinding
import com.example.buzzly.viewmodels.AuthViewModel

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragmentContainer, SignInFragment())
            addToBackStack(null)
            commit()
        }
    }
}