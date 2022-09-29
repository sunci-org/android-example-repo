package com.example.finalapplication.screen.chatroom

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalapplication.BuildConfig
import com.example.finalapplication.data.model.Message
import com.example.finalapplication.data.model.User
import com.example.finalapplication.databinding.ActivityChatRoomBinding
import com.example.finalapplication.utils.Constant
import com.example.finalapplication.utils.NumberConstant
import com.example.finalapplication.utils.ScrollListenner
import com.example.finalapplication.utils.StatusConstant
import com.example.finalapplication.utils.base.BaseActivity
import com.example.finalapplication.utils.getNewid
import com.example.finalapplication.utils.loadImageByUrl
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class ChatRoomActivity : BaseActivity<ActivityChatRoomBinding>(ActivityChatRoomBinding::inflate) {

    private val chatViewModel: ChatViewModel by viewModel()
    private lateinit var currentUser: User
    private var adversaryUser: User? = null
    private val messages = mutableListOf<Message>()
    private lateinit var chatAdapter: ChatListAdapter
    private var isFisrtMessage = true
    private var isEndPage = false
    private var isLoading = false
    private var currentPath = ""
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val intent = result.data
                val uri = intent?.data
                val message = Message(
                    getNewid().toString(),
                    Message.chatbox,
                    System.currentTimeMillis(),
                    currentUser.id.toString(),
                    adversaryUser?.id.toString()
                )
                message.image = uri.toString()
                message.roomId = currentUser.id + adversaryUser?.id
                adversaryUser?.let { it1 -> chatViewModel.sendMessage(currentUser, it1, message) }
            }
        }
    private val activityResultCameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val message = Message(
                    getNewid().toString(),
                    Message.chatbox,
                    System.currentTimeMillis(),
                    currentUser.id.toString(),
                    adversaryUser?.id.toString()
                )
                message.image = Uri.fromFile(File(currentPath)).toString()
                message.roomId = currentUser.id + adversaryUser?.id
                adversaryUser?.let { it1 -> chatViewModel.sendMessage(currentUser, it1, message) }
            }
        }

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
            messages.add(0, data)
            binding.progressLoading.isVisible = false
            chatAdapter.submitList(messages)
            chatAdapter.notifyDataSetChanged()
            if (isFisrtMessage) {
                chatViewModel.getHistoryMessage(adversaryUser?.id.toString(), data.time)
                isFisrtMessage = false
            }
        }
        chatViewModel.historyMessage.observe(this) { data ->
            messages.addAll(data)
            chatAdapter.submitList(messages)
            chatAdapter.notifyDataSetChanged()
            if (data.size < NumberConstant.ITEM_PER_PAGE) {
                isEndPage = true
                chatAdapter.haveNextPage = false
            }
        }
        chatViewModel.isLoading.observe(this) { data ->
            this.isLoading = data
            binding.progressLoading.isVisible = data
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
                message.roomId = currentUser.id + adversaryUser?.id
                adversaryUser?.let { it1 -> chatViewModel.sendMessage(currentUser, it1, message) }
            }
            recyclerHistoryMessage.addOnScrollListener(object :
                ScrollListenner(recyclerHistoryMessage.layoutManager as LinearLayoutManager) {
                override fun loadMore() {
                    chatViewModel.getHistoryMessage(
                        adversaryUser?.id.toString(),
                        messages.last().time
                    )
                }

                override fun isLoading() = isLoading

                override fun isEndPage() = isEndPage
            })
            buttonImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = Constant.TYPE_IMAGE
                activityResultLauncher.launch(intent)
            }
            buttonCamera.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val store = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val imageFile =
                    File.createTempFile(Message.messages, Constant.EXTENSION_IMAGE, store)
                currentPath = imageFile.absolutePath
                val imageUri = FileProvider.getUriForFile(
                    applicationContext,
                    BuildConfig.APPLICATION_ID + Constant.PROVIDER,
                    imageFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                activityResultCameraLauncher.launch(intent)
            }
        }
    }

    override fun initData() {
        val id = intent.getStringExtra(Constant.RECIVER)
        chatViewModel.getReciver(id)
        chatViewModel.getNewMessage(id)
        chatAdapter = ChatListAdapter(id.toString())
        chatAdapter.submitList(messages)
        chatAdapter.notifyDataSetChanged()
        val layoutManager = binding.recyclerHistoryMessage.layoutManager as LinearLayoutManager
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.recyclerHistoryMessage.layoutManager = layoutManager
        binding.recyclerHistoryMessage.adapter = chatAdapter
    }

    override fun onPause() {
        chatViewModel.updateSeenMessage(currentUser.id + adversaryUser?.id)
        super.onPause()
    }
}
