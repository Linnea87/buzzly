package com.example.buzzly.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.buzzly.R
import com.example.buzzly.databinding.FragmentSignInBinding
import com.example.buzzly.utils.AuthInputValidator
import com.example.buzzly.viewmodels.AuthViewModel

class SignInFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
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

            authViewModel.signIn(email,password,{
                Toast.makeText(requireContext(),"Success Log in", Toast.LENGTH_SHORT).show()
                val intent = Intent(requireContext(), ChatActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            },{
                Toast.makeText(requireContext(),"Unsuccessful log in", Toast.LENGTH_SHORT).show()
            })
        }

        binding.btnGoToSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,SignUpFragment())
                .addToBackStack(null)
                .commit()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
