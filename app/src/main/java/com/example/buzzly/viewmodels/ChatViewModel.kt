package com.example.buzzly.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buzzly.data.ChatRepository
import com.example.buzzly.data.Message

class ChatViewModel : ViewModel()  {
    private val repository = ChatRepository()

    private val _currentChatId = MutableLiveData<String>()
    private val _messages = MutableLiveData<List<Message>>()

    val currentChatId: LiveData<String> = _currentChatId
    val messages: LiveData<List<Message>> = _messages

    fun startChatWith(userId: String) {
        repository.createChatWith(
            otherUserId = userId,
            onSuccess = { chatId ->
                _currentChatId.postValue(chatId)
            }
        )
    }

    fun sendMessage(message: Message) {
        val chatId = _currentChatId.value ?: return
        repository.sendMessage(chatId, message)
    }

    fun observeMessages() {
        val chatId = currentChatId.value ?: return
        repository.listenToMessages(chatId) { messageList ->

            _messages.postValue(messageList)
        }
    }
}

