package com.example.finalapplication.data.repository.resource.remote

import androidx.core.net.toUri
import com.example.finalapplication.Utils.Constant
import com.example.finalapplication.Utils.getNewid
import com.example.finalapplication.data.model.Account
import com.example.finalapplication.data.model.User
import com.example.finalapplication.data.repository.resource.Listenner
import com.example.finalapplication.data.repository.resource.UserDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class RemoteUser : UserDataSource.remote {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = Firebase.firestore
    private val storage = FirebaseStorage.getInstance().reference

    override fun getCurrentUser(listen: Listenner<User>) {
        val currentUser = auth.currentUser
        if (currentUser == null) listen.onError(Constant.ERROR_USER)
        else {
            database.collection(User.user)
                .document(auth.uid.toString())
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        listen.onError(e.toString())
                        return@addSnapshotListener
                    }
                    snapshot?.let {
                        val user = it.toObject(User::class.java)
                        if (user != null) {
                            listen.onSuccess(user)
                        } else listen.onError(Constant.ERROR_USER)
                    }
                }
        }
    }

    override fun loginUser(account: Account, listen: Listenner<Boolean>) {
        auth.signInWithEmailAndPassword(account.email, account.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    database.collection(User.user)
                        .document(auth.uid.toString())
                        .update(mapOf("${User.account}.${Account.password}" to account.password))
                    listen.onSuccess(true)
                } else listen.onError(task.exception.toString())
            }
    }

    override fun registerUser(user: User, listen: Listenner<Boolean>) {
        user.userAccount?.also {
            auth.createUserWithEmailAndPassword(it.email, it.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        auth.signInWithEmailAndPassword(it.email, it.password)
                        user.id = auth.uid
                        database.collection(User.user)
                            .document(auth.uid.toString())
                            .set(user)
                            .addOnCompleteListener { tasks ->
                                if (tasks.isSuccessful) listen.onSuccess(true)
                                else listen.onSuccess(false)
                            }
                    } else listen.onError(task.exception.toString())
                }
        }
    }

    override fun updateProfile(user: User, listen: Listenner<Boolean>) {
        database.collection(User.user)
            .document(auth.uid.toString())
            .set(user)
            .addOnCompleteListener { tasks ->
                if (tasks.isSuccessful) listen.onSuccess(true)
                else listen.onSuccess(false)
            }
    }

    override fun updatePassword(user: User, listen: Listenner<Boolean>) {
        user.userAccount?.let {
            auth.currentUser?.updatePassword(it.password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        database.collection(User.user)
                            .document(auth.uid.toString())
                            .set(user)
                            .addOnCompleteListener { tasks ->
                                if (tasks.isSuccessful) listen.onSuccess(true)
                                else listen.onSuccess(false)
                            }
                    } else listen.onError(task.exception.toString())
                }
        }
    }

    override fun updateAvatar(user: User, listen: Listenner<Boolean>) {
        val newid = getNewid()
        user.avatar?.let {
            storage
                .child("image/$newid")
                .putFile(it.toUri())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        storage.child("image/$newid")
                            .downloadUrl
                            .addOnSuccessListener { uri ->
                                user.avatar = uri.toString()
                            }
                        database.collection(User.user)
                            .document(auth.uid.toString())
                            .set(user)
                            .addOnCompleteListener { tasks ->
                                if (tasks.isSuccessful) listen.onSuccess(true)
                                else listen.onSuccess(false)
                            }
                    } else listen.onError(task.exception.toString())
                }
        }


    }

    override fun forgotPassword(email: String, listen: Listenner<Boolean>) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) listen.onSuccess(true)
                else listen.onError(task.exception.toString())
            }
    }

    override fun logout() {
        auth.signOut()
    }
}
