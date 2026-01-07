package com.example.buzzly.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.buzzly.data.Message
import com.example.buzzly.databinding.FragmentChatRoomBinding
import com.example.buzzly.viewmodels.ChatViewModel
import com.google.firebase.auth.FirebaseAuth

class ChatRoomFragment : Fragment() {
    private var _binding: FragmentChatRoomBinding? = null
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
        _binding = FragmentChatRoomBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        chatViewModel.currentChatId.observe(viewLifecycleOwner) { chatId ->
            if (chatId != null) {
                chatViewModel.observeMessages()
            }
        }

        chatViewModel.messages.observe(viewLifecycleOwner) { messages ->
            val text = messages.joinToString("\n") {
                "${it.senderId.take(5)}: ${it.text}"
            }
            binding.tvMessagesPlaceholder.text = text
        }

        binding.btnSend.setOnClickListener {
            val text = binding.etMessage.text.toString().trim()
            if (text.isNotEmpty()) {
                chatViewModel.sendMessage(
                    Message(
                        text = text,
                        senderId = FirebaseAuth.getInstance().currentUser!!.uid
                    )
                )
                binding.etMessage.text.clear()
            }
        }
    }

    override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
    }
}
