package com.example.finalapplication.screen

import android.app.Application
import com.example.finalapplication.data.repository.UserRepositoryIpml
import com.example.finalapplication.data.repository.resource.UserDataSource
import com.example.finalapplication.data.repository.resource.remote.RemoteUser
import com.example.finalapplication.screen.createaccount.CreateAccountViewModel
import com.example.finalapplication.screen.launch.LaunchViewModel
import com.example.finalapplication.screen.login.LoginViewModel
import com.example.finalapplication.screen.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    viewModel {
        LaunchViewModel(get())
    }
    viewModel {
        CreateAccountViewModel(get())
    }
    viewModel {
        LoginViewModel(get())
    }
    viewModel {
        ProfileViewModel(get())
    }
    single<UserDataSource.remote> { RemoteUser() }
    single { UserRepositoryIpml(get()) }
}

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }
}
