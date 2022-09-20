package com.example.finalapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User() : Parcelable {
    var id: String? = null
    var role: String? = null
    var name: String? = null
    var avatar: String? = null
    var phoneNumber: String? = null
    var isOnline: Boolean = false
    var userAccount: Account? = null
    var bankAccountName: String? = null
    var bankAccount: String? = null

    constructor(
        id: String,
        role: String,
        name: String,
        avatar: String,
        phoneNumber: String,
        isOnline: Boolean,
        userAccount: Account,
        bankAccountName: String,
        bankAccount: String
    ) : this() {
        this.id = id
        this.role = role
        this.name = name
        this.avatar = avatar
        this.phoneNumber = phoneNumber
        this.isOnline = isOnline
        this.userAccount = userAccount
        this.bankAccount = bankAccount
        this.bankAccountName = bankAccountName
    }

    companion object {
        const val user = "users"
        const val id = "id"
        const val role = "role"
        const val name = "name"
        const val avatar = "avatar"
        const val phoneNumber = "phoneNumber"
        const val isOnline = "isOnline"
        const val account = "userAccount"
        const val bankAccountName = "bankname"
        const val bankAccount = "bankAccount"
    }
}
