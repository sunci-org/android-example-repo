package com.example.finalapplication.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> VB
) : AppCompatActivity() {
    private var _binding: VB? = null
    val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        initData()
        handleEvent()
        bindData()
    }

    abstract fun bindData()

    abstract fun handleEvent()

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
