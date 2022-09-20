package com.example.finalapplication.Utils

import java.util.*

object Constant {
    const val ERROE_EMAIL_EMPTY = "enter your  email to reset password"
    const val NO_INTERNET = "Không có kết nối mạng, vui lòng kiểm tra kết nối"
    const val ROLE_CLIENT = "client"
    const val ROLE_STAFF = "staff"
    const val ERROR_ = "Đã xảy ra lỗi vui lòng thử lại sau"
    const val ERROR_EMAIL_EXTIST = "Tài khoản đã tồn tại"
    const val ERROR_PASSWORD_SHORT = "Password cần nhiều hơn 8 kí tự"
    const val ERROR_CONFIRM_PASSWORD = "Confirm password khác password"
    const val ERROR_ACCOUNT = "Sai tài khoản mật khẩu"
    const val ERROR_USER = "Chưa đăng nhập"
    const val ERROR_NAME_EMPTY = "Name cannot be empty"
    const val ERROR_VALIDATE_MAIL = "Email is not validate"
    const val ERROR_INCORECT_PASSWORD = "Incorrect password"
    const val ERROR_ACCOUNT_EXIST = "Account Existed"
    const val MSG_SIGN_IN = "Sign in ...."
    const val MSG_CHECK_MAIL = "Please check your mail to get link reset password"
    const val MSG_UPDATE = "Update..."
}

fun getNewid() = UUID.randomUUID()
