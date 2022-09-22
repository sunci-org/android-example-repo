package com.example.finalapplication.screen.login

import android.app.ProgressDialog
import android.content.Intent
import android.widget.Toast
import com.example.finalapplication.utils.Constant
import com.example.finalapplication.utils.base.BaseActivity
import com.example.finalapplication.data.model.Account
import com.example.finalapplication.databinding.ActivityLoginBinding
import com.example.finalapplication.screen.createaccount.CreateAccountActivity
import com.example.finalapplication.screen.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity<ActivityLoginBinding>(ActivityLoginBinding::inflate) {

    private val loginViewModel: LoginViewModel by viewModel()

    private lateinit var loading: ProgressDialog

    override fun handleEvent() {
        binding.apply {
            textSignup.setOnClickListener {
                val intent = Intent(this@LoginActivity, CreateAccountActivity::class.java)
                startActivity(intent)
                finish()
            }
            buttonLogin.setOnClickListener {
                val email = textEmail.text.toString()
                val password = textPassword.text.toString()
                val account = Account(email, password)
                if (account.validateAccount()) {
                    Toast.makeText(
                        this@LoginActivity,
                        Constant.ERROR_ACCOUNT,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    loginViewModel.login(account)
                }
            }
            textForgotpassword.setOnClickListener {
                val email = textEmail.text.toString()
                if (email.isEmpty()) {
                    Toast.makeText(
                        applicationContext,
                        Constant.ERROE_EMAIL_EMPTY,
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                loginViewModel.forgotPassword(email)
            }
        }
    }

    override fun initData() {
        loading = ProgressDialog(this)
        loading.setMessage(Constant.MSG_SIGN_IN)
        loading.setCancelable(false)
    }

    override fun bindData() {
        loginViewModel.isLoginSuccess.observe(this@LoginActivity) { result ->
            if (result == true) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        loginViewModel.isLoading.observe(this) { data ->
            if (data) loading.show()
            else loading.hide()
        }
        loginViewModel.isResetSuccess.observe(this@LoginActivity) { result ->
            if (result)
                Toast.makeText(
                    applicationContext,
                    Constant.MSG_CHECK_MAIL,
                    Toast.LENGTH_SHORT
                ).show()
        }
        loginViewModel.errorMessage.observe(this@LoginActivity) { msg ->
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
