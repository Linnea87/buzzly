package com.example.buzzly.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.collection.emptyIntSet
import androidx.lifecycle.ViewModelProvider
import com.example.buzzly.data.UserRepository
import com.example.buzzly.databinding.FragmentSignUpBinding
import com.example.buzzly.utils.AuthInputValidator
import com.example.buzzly.viewmodels.AuthViewModel

class SignUpFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    private val userRepository = UserRepository()

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            val error = AuthInputValidator.validateEmailAndPassword(
                email = email,
                password = password
            )

            if (error != null) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            authViewModel.createAccount(email, password) { task ->
                if(task.isSuccessful){
                    userRepository.createUserProfile()
                    Toast.makeText(requireContext(), "Signup successful", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()
                }else{
                    // Firebase returns unsuccessful if authentication fails
                    // email already in use, network error, weak password etc
                    Toast.makeText(requireContext(), task.exception?.message ?: "Signup failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
      _binding = null
    }
}