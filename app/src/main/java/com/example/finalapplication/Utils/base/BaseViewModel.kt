package com.example.finalapplication.Utils.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    protected val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    private var countLoading = 0

    protected fun <T> launchTask(
        onRequest: suspend CoroutineScope.() -> Unit = {},
        isShowLoading: Boolean = true,
    ) = viewModelScope.launch {
        showLoading(isShowLoading)
        onRequest(this)
    }

    private fun showLoading(isShowLoading: Boolean) {
        if (!isShowLoading) return
        countLoading++
        if (_isLoading.value != true) _isLoading.value = true
    }

    protected fun hideLoading(isShowLoading: Boolean) {
        if (!isShowLoading) return
        countLoading--
        if (countLoading <= 0) {
            countLoading = 0
            _isLoading.value = false
        }
    }
}
