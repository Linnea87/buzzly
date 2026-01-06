package com.example.buzzly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buzzly.data.ChatRepository
import com.example.buzzly.data.Message

class ChatViewModel : ViewModel()  {
    private val repository = ChatRepository()

    private val _currentRoomId = MutableLiveData<String>()
    val currentRoomId: LiveData<String> = _currentRoomId

    fun startChatWith(userId: String) {
        repository.createChatWith(
            otherUserId = userId,
            onSuccess = { roomId ->
                _currentRoomId.postValue(roomId)
            }
        )
    }

    fun sendMessage(message: Message) {
        val roomId = _currentRoomId.value ?: return
        repository.sendMessage(roomId, message)
    }
}

