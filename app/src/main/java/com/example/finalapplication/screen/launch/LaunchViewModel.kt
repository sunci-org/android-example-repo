package com.example.finalapplication.screen.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finalapplication.Utils.base.BaseViewModel
import com.example.finalapplication.data.model.User
import com.example.finalapplication.data.repository.UserRepositoryIpml
import com.example.finalapplication.data.repository.resource.Listenner


/*class LaunchViewModel(private val userRepository: UserRepositoryIpml) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user


    init {
        viewModelScope.launch {
            userRepository.getCurrentUser(object : Listenner<User> {

                override fun onError(msg: String) {
                    _user.value = null
                }

                override fun onSuccess(result: User) {
                    _user.value = result
                }
            })
        }
    }
}*/

class LaunchViewModel(private val userRepository: UserRepositoryIpml) : BaseViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    init {
        launchTask<User>(
            onRequest = {
                userRepository.getCurrentUser(object : Listenner<User> {
                    override fun onSuccess(data: User) {
                        _user.value = data
                        hideLoading(true)
                    }

                    override fun onError(msg: String) {
                        _errorMessage.value = msg
                        _user.value = null
                        hideLoading(true)
                    }
                })
            },
        )
    }
}
