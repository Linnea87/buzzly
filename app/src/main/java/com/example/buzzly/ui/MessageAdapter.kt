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
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Locale

class MessageAdapter : ListAdapter<Message, MessageAdapter.MessageViewHolder>(
    object : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(old: Message, new: Message): Boolean =
            old.timestamp == new.timestamp

        override fun areContentsTheSame(old: Message, new: Message): Boolean =
            old == new
    }
) {

    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2

    inner class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvMessage = view.findViewById<TextView>(R.id.tvMessage)
        private val tvTimestamp = view.findViewById<TextView?>(R.id.tvTimestamp)
        private val tvSenderName = view.findViewById<TextView?>(R.id.tvSenderName)

        fun bind(message: Message) {
            tvMessage.text = message.text
            tvSenderName?.text = message.senderName

            val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
            tvTimestamp?.text = message.timestamp?.toDate()?.let {
                formatter.format(it)
            } ?: ""
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

        return if (message.senderId == currentUserId) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutRes = if (viewType == VIEW_TYPE_SENT) {
            R.layout.item_message_sent
        } else {
            R.layout.item_message_received
        }

        val view = LayoutInflater.from(parent.context)
            .inflate(layoutRes, parent, false)

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}