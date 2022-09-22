package com.example.finalapplication.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalapplication.utils.base.BaseViewModel
import com.example.finalapplication.data.model.Account
import com.example.finalapplication.data.repository.UserRepositoryIpml
import com.example.finalapplication.data.repository.resource.Listenner

class LoginViewModel(private val userRepository: UserRepositoryIpml) : BaseViewModel() {
    private val _isLoginSucces = MutableLiveData<Boolean>()
    val isLoginSuccess : LiveData<Boolean>
    get() = _isLoginSucces

    private val _resetSuccess = MutableLiveData<Boolean>()
    val isResetSuccess : LiveData<Boolean>
    get() = _resetSuccess

    init {
        _isLoginSucces.value = false
        _resetSuccess.value = false
    }

    fun login(account: Account){
        launchTask<Boolean>(onRequest = {
            userRepository.loginUser(account, object : Listenner<Boolean> {

                override fun onError(msg: String) {
                    message.value = msg
                    hideLoading(true)
                }

                override fun onSuccess(result: Boolean) {
                    _isLoginSucces.value = result
                    hideLoading(true)
                }
            })
        })
    }

    fun forgotPassword(email : String){
        launchTask<Boolean>(onRequest = {
            userRepository.forgotPassword(email, object : Listenner<Boolean>{
                override fun onSuccess(result: Boolean) {
                    _resetSuccess.value = result
                    hideLoading(true)
                }

                override fun onError(msg: String) {
                    message.value = msg
                    hideLoading(true)
                }
            })
        })
    }
}
