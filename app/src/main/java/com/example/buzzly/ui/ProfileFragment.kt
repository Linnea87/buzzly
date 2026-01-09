package com.example.buzzly.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.buzzly.R
import com.example.buzzly.databinding.FragmentProfileBinding
import com.example.buzzly.viewmodels.ChatViewModel
import com.example.buzzly.viewmodels.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var profileViewModel : ProfileViewModel

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatViewModel = ViewModelProvider(requireActivity())[ChatViewModel::class.java]
        profileViewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
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

        binding.rvUsers.layoutManager = LinearLayoutManager(requireContext())

        profileViewModel.users.observe(viewLifecycleOwner) { users ->
            binding.rvUsers.adapter = UserAdapter(users) { user ->
                chatViewModel.startChatWith(user.uid, user.displayName)
            }
        }

        profileViewModel.loadUsers()
        profileViewModel.loadCurrentUser()


        profileViewModel.currentUser.observe(viewLifecycleOwner) { user ->
            binding.tvProfileName.text = user.displayName
            binding.tvProfileSubtitle.text = getString(R.string.Username, user.displayName)
        }


        chatViewModel.currentChatId.observe(viewLifecycleOwner) { chatId ->
            if (chatId != null) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentChatContainer, ChatRoomFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding.btnSignOut.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        auth.signOut()

        val intent = Intent(requireContext(), SignInActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)

        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}