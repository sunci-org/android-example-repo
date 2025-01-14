package com.example.finalapplication.data.model

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import kotlinx.parcelize.Parcelize

@Parcelize
class User() : Parcelable {
    var id: String? = null
    var role: String? = null
    var name: String? = null
    var avatar: String? = null
    var phoneNumber: String? = null
    var isOnline: Boolean = false
    var userAccount: Account? = Account()
    var bankAccountName: String? = null
    var bankAccount: String? = null

    constructor(
        role: String,
        name: String,
        userAccount: Account,
    ) : this() {
        this.role = role
        this.name = name
        this.userAccount = userAccount
    }

    fun validateName(): Boolean {
        if (this.name.isNullOrEmpty()) return false
        return true
    }

    companion object {
        const val users = "users"
        const val id = "id"
        const val role = "role"
        const val name = "name"
        const val avatar = "avatar"
        const val phoneNumber = "phoneNumber"
        const val isOnline = "isOnline"
        const val account = "userAccount"
        const val bankAccountName = "bankname"
        const val bankAccount = "bankAccount"
        val diffCallBack = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}

