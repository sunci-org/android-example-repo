package com.example.finalapplication.screen.chatroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import com.example.finalapplication.data.model.Message
import com.example.finalapplication.databinding.ItemLoadMoreBinding
import com.example.finalapplication.databinding.ItemReciveBinding
import com.example.finalapplication.databinding.ItemSendBinding
import com.example.finalapplication.utils.base.BaseViewHolder
import com.example.finalapplication.utils.loadImageByUrl

class ChatListAdapter(private val revicerid: String) :
    ListAdapter<Message, BaseViewHolder<Any>>(Message.diffCallback) {

    var haveNextPage = true

    override fun getItemViewType(position: Int): Int {
        if (currentList.size > 0 && position == currentList.size && haveNextPage) return TYPE_LOAD
        return when (currentList[position].senderId) {
            revicerid -> TYPE_RECEIVE_DATA
            else -> TYPE_SEND_DATA
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_LOAD -> LoadViewHolder(ItemLoadMoreBinding.inflate(layoutInflater, parent, false))
            TYPE_SEND_DATA -> SendMessageViewHolder(
                ItemSendBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> ReceiveMessageViewHolder(
                ItemReciveBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        if (currentList.size <= position) return
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        if (haveNextPage && currentList.size > 0) return currentList.size + 1
        return super.getItemCount()
    }

    inner class SendMessageViewHolder(val binding: ItemSendBinding) : BaseViewHolder<Any>(binding) {

        override fun bind(item: Any) {
            val message = item as Message
            when (message.chatType) {
                Message.chatbox -> {
                    binding.apply {
                        containerCall.isVisible = false
                        if (message.text.isNullOrEmpty()) {
                            textSend.isVisible = false
                            imageSend.loadImageByUrl(message.image, binding.root.context)
                        } else {
                            imageSend.isVisible = false
                            textSend.text = message.text
                        }
                    }
                }
                Message.call -> {
                    // TODO later
                }
                Message.video -> {
                    // TODO later
                }
            }
        }
    }

    inner class ReceiveMessageViewHolder(val binding: ItemReciveBinding) :
        BaseViewHolder<Any>(binding) {

        override fun bind(item: Any) {
            // TODO later
        }
    }

    inner class LoadViewHolder(val binding: ItemLoadMoreBinding) : BaseViewHolder<Any>(binding) {

        override fun bind(item: Any) {
            // TODo no-ip
        }
    }

    companion object {
        const val TYPE_SEND_DATA = 1
        const val TYPE_RECEIVE_DATA = 2
        const val TYPE_LOAD = 3
    }
}
