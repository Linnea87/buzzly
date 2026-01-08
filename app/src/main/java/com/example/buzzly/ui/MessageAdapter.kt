package com.example.buzzly.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.buzzly.R
import com.example.buzzly.data.Message

class MessageAdapter : ListAdapter<Message, MessageAdapter.MessageViewHolder>(
    object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(old: Message, new: Message) =
            old.timestamp == new.timestamp

        override fun areContentsTheSame(old: Message, new: Message) =
            old == new
    }
) {

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvMessage = view.findViewById<TextView>(R.id.tvMessage)

        fun bind(message: Message) {
            tvMessage.text = message.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}