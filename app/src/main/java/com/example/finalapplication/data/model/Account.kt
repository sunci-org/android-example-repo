package com.example.finalapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Account() : Parcelable {
    var password: String = ""
    var email: String = ""

    constructor(email: String, password: String) : this() {
        this.email = email
        this.password = password
    }

    companion object {
        const val password = "password"
        const val email = "email"
    }
}
