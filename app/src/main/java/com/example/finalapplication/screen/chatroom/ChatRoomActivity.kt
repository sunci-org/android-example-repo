package com.example.finalapplication.screen.chatroom

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import com.example.finalapplication.data.model.Message
import com.example.finalapplication.data.model.User
import com.example.finalapplication.databinding.ActivityChatRoomBinding
import com.example.finalapplication.utils.Constant
import com.example.finalapplication.utils.StatusConstant
import com.example.finalapplication.utils.base.BaseActivity
import com.example.finalapplication.utils.getNewid
import com.example.finalapplication.utils.loadImageByUrl
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChatRoomActivity : BaseActivity<ActivityChatRoomBinding>(ActivityChatRoomBinding::inflate) {

    private val chatViewModel: ChatViewModel by viewModel()
    private lateinit var currentUser: User
    private var adversaryUser: User? = null
    private val messages = mutableListOf<Message>()
    private lateinit var chatAdapter: ChatListAdapter

    override fun bindData() {
        chatViewModel.receiver.observe(this) { data ->
            adversaryUser = data
            binding.apply {
                imageAvatar.loadImageByUrl(data.avatar, applicationContext)
                textStatus.text = if (data.isOnline) StatusConstant.ONLINE
                else StatusConstant.OFFLINE
                textName.text = data.name
            }
        }
        chatViewModel.currentUser.observe(this) { data ->
            currentUser = data
        }
        chatViewModel.newMessage.observe(this) { data ->
            messages.add(data)
            binding.progressLoading.isVisible = false
            chatAdapter.submitList(messages)
            chatAdapter.notifyDataSetChanged()
        }
    }

    override fun handleEvent() {
        binding.apply {
            buttonBack.setOnClickListener {
                finish()
            }
            buttonSend.setOnClickListener {
                val messageText = textMessage.text?.trim().toString()
                textMessage.text?.clear()
                if (messageText.isNullOrEmpty()) return@setOnClickListener
                val message = Message(
                    getNewid().toString(),
                    Message.chatbox,
                    System.currentTimeMillis(),
                    currentUser.id.toString(),
                    adversaryUser?.id.toString()
                )
                message.text = messageText
                adversaryUser?.let { it1 -> chatViewModel.sendMessage(currentUser, it1, message) }
            }
        }
    }

    override fun initData() {
        val id = intent.getStringExtra(Constant.RECIVER)
        chatViewModel.getReciver(id)
        chatViewModel.getNewMessage(id)
        chatAdapter = ChatListAdapter(id.toString())
        binding.recyclerHistoryMessage.adapter = chatAdapter
    }
}
