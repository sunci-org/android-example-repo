package com.example.finalapplication.data.repository

import com.example.finalapplication.data.model.Account
import com.example.finalapplication.data.model.User
import com.example.finalapplication.data.repository.resource.Listenner
import com.example.finalapplication.data.repository.resource.UserDataSource

class UserRepositoryIpml(val remote: UserDataSource.remote) : UserRepository {

    override suspend fun getCurrentUser(listen: Listenner<User>) {
        remote.getCurrentUser(listen)
    }

    override suspend fun loginUser(account: Account, listen: Listenner<Boolean>) {
        remote.loginUser(account, listen)
    }

    override suspend fun registerUser(user: User, listen: Listenner<Boolean>) {
        remote.registerUser(user, listen)
    }

    override suspend fun updateProfile(user: User, listen: Listenner<Boolean>) {
        remote.updateProfile(user, listen)
    }

    override suspend fun updatePassword(user: User, listen: Listenner<Boolean>) {
        remote.updatePassword(user, listen)
    }

    override suspend fun updateAvatar(user: User, listen: Listenner<Boolean>) {
        remote.updateAvatar(user, listen)
    }

    override suspend fun forgotPassword(eamil: String, listen: Listenner<Boolean>) {
        remote.forgotPassword(eamil, listen)
    }

    override fun logout() {
        remote.logout()
    }
}
