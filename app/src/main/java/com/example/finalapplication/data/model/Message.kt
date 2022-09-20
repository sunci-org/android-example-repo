package com.example.finalapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class Message() : Parcelable {
    var id: String? = null
    var text: String? = null
    var image: String? = null
    var time: Long = 0
    var chatType: String? = null
    var callTime: Long = 0
    var senderId: String? = null
    var reciverId: String? = null
    var seen: Boolean = false

    constructor(
        id: String,
        text: String,
        image: String,
        time: Long,
        chatType: String,
        callTime: Long,
        senderId: String,
        reciverId: String,
        seen: Boolean
    ) : this() {
        this.id = id
        this.text = text
        this.image = image
        this.time = time
        this.chatType = chatType
        this.callTime = callTime
        this.senderId = senderId
        this.reciverId = reciverId
        this.seen = seen
    }

    companion object {
        const val reciverId = " reciverId"
        const val senderId = "senderId"
        const val seen = "seen"
        const val time = "time"
    }
}
