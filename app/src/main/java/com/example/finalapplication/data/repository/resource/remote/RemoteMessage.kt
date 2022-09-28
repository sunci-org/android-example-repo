package com.example.finalapplication.data.repository.resource.remote

import android.util.Log
import com.example.finalapplication.data.model.Contact
import com.example.finalapplication.data.model.Message
import com.example.finalapplication.data.model.User
import com.example.finalapplication.data.repository.resource.ContactDataSource
import com.example.finalapplication.data.repository.resource.Listenner
import com.example.finalapplication.data.repository.resource.MessageDataSource
import com.example.finalapplication.utils.NumberConstant
import com.example.finalapplication.utils.getNewid
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RemoteMessage(private val contactRemote: ContactDataSource.Remote) : MessageDataSource.Remote {
    val auth = FirebaseAuth.getInstance()
    val database = Firebase.firestore
    val storage = Firebase.storage

    override suspend fun sendMessage(
        currentUser: User,
        adversaryUser: User,
        message: Message,
        listen: Listenner<Boolean>
    ) {
        database.collection(Message.messages)
            .document(getNewid().toString())
            .set(message)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    listen.onSuccess(true)
                    val myContact = Contact(
                        auth.uid + message.reciverId,
                        auth.uid.toString(),
                        adversaryUser,
                        message
                    )
                    val adversaryContact = Contact(
                        message.reciverId + auth.uid,
                        message.reciverId.toString(),
                        currentUser,
                        message
                    )
                    contactRemote.addNewContact(myContact)
                    contactRemote.addNewContact(adversaryContact)
                } else listen.onError(task.exception.toString())
            }
    }

    override suspend fun getHistoryMessage(
        reciverId: String,
        lastIndex: String,
        listen: Listenner<List<Message>>
    ) {
        database.collection(Message.messages)
            .whereIn(Message.reciverId, listOf(reciverId, auth.uid))
            .whereIn(Message.senderId, listOf(reciverId, auth.uid))
            .orderBy(Message.time, Query.Direction.DESCENDING)
            .whereLessThan(Message.time, lastIndex)
            .limit(NumberConstant.MESSAGE_PER_PAGE.toLong())
            .addSnapshotListener { value, error ->
                if (error != null) {
                    listen.onError(error.toString())
                    return@addSnapshotListener
                }
                
            }
    }
}
