package com.example.buzzly.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.buzzly.databinding.ActivitySignInBinding
import com.example.buzzly.viemodels.AuthViewModel

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnGoToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            authViewModel.signIn(email,password,{

                Toast.makeText(this,"Success Log in", Toast.LENGTH_SHORT).show()
//                val intent = Intent(this, SignInActivity::class.java)
//                startActivity(intent)
            },{
                Toast.makeText(this,"Unsuccessful log in", Toast.LENGTH_SHORT).show()
            })
        }

    }


}