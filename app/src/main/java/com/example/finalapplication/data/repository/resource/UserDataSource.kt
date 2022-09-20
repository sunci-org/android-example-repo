package com.example.finalapplication.data.repository.resource

import com.example.finalapplication.data.model.Account
import com.example.finalapplication.data.model.User

interface UserDataSource {

    interface remote {
        fun getCurrentUser(listen: Listenner<User>)
        fun loginUser(account: Account, listen: Listenner<Boolean>)
        fun registerUser(user: User, listen: Listenner<Boolean>)
        fun updateProfile(user: User, listen: Listenner<Boolean>)
        fun updatePassword(user: User, listen: Listenner<Boolean>)
        fun updateAvatar(user: User, listen: Listenner<Boolean>)
        fun forgotPassword(email: String, listen: Listenner<Boolean>)
        fun logout()
    }
}
