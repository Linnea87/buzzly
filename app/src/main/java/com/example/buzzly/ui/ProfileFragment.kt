package com.example.buzzly.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.buzzly.R
import com.example.buzzly.databinding.FragmentProfileBinding
import com.example.buzzly.viewmodels.ChatViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatViewModel = ViewModelProvider(requireActivity())[ChatViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTestStartChat.setOnClickListener {
            val otherUserId = "REAL_FIREBASE_UID"
            chatViewModel.startChatWith(otherUserId)
        }

        chatViewModel.currentChatId.observe(viewLifecycleOwner) { chatId ->
            if (chatId != null) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentChatContainer, ChatRoomFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}