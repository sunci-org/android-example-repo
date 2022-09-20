package com.example.finalapplication.screen.launch

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.example.finalapplication.Utils.Constant
import com.example.finalapplication.Utils.base.BaseActivity
import com.example.finalapplication.databinding.ActivityLaunchBinding
import com.example.finalapplication.screen.login.LoginActivity
import com.example.finalapplication.screen.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LaunchActivity : BaseActivity<ActivityLaunchBinding>(ActivityLaunchBinding::inflate) {
    private val launchViewModel: LaunchViewModel by viewModel()

    override fun handleEvent() {
        lifecycleScope.launch {
            delay(2000L)
            launchViewModel.user.observe(this@LaunchActivity) { user ->
                if (user == null) {
                    val intent = Intent(this@LaunchActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    if (user.role.equals(Constant.ROLE_CLIENT)) {
                        val intent = Intent(this@LaunchActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    override fun initData() {
        // TODO("Not yet implemented")
    }

    override fun initView() {
        // TODO("Not yet implemented")
    }
}
