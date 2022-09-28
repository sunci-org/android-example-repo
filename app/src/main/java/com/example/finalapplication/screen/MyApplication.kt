package com.example.finalapplication.screen

import android.app.Application
import com.example.finalapplication.data.model.Contact
import com.example.finalapplication.data.repository.ContactRepository
import com.example.finalapplication.data.repository.ContactRepositoryImpl
import com.example.finalapplication.data.repository.MessageRepository
import com.example.finalapplication.data.repository.MessageRepositoryImpl
import com.example.finalapplication.data.repository.UserRepository
import com.example.finalapplication.data.repository.UserRepositoryIpml
import com.example.finalapplication.data.repository.resource.ContactDataSource
import com.example.finalapplication.data.repository.resource.MessageDataSource
import com.example.finalapplication.data.repository.resource.UserDataSource
import com.example.finalapplication.data.repository.resource.remote.RemoteContact
import com.example.finalapplication.data.repository.resource.remote.RemoteMessage
import com.example.finalapplication.data.repository.resource.remote.RemoteUser
import com.example.finalapplication.screen.chatroom.ChatViewModel
import com.example.finalapplication.screen.createaccount.CreateAccountViewModel
import com.example.finalapplication.screen.historycontact.HistoryContactViewModel
import com.example.finalapplication.screen.launch.LaunchViewModel
import com.example.finalapplication.screen.login.LoginViewModel
import com.example.finalapplication.screen.profile.ProfileViewModel
import com.example.finalapplication.screen.search.SearchViewModel
import com.example.finalapplication.utils.ApiFcmService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
    viewModel {
        SearchViewModel(get())
    }
    fun provideRetrofit(factory: Gson) = Retrofit.Builder()
        .baseUrl(Contact.BASE_URL_FCM)
        .addConverterFactory(GsonConverterFactory.create(factory))
        .build()

    fun provideGson() = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
        .create()

    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiFcmService::class.java)
    single { provideApiService(get()) }
    single { provideGson() }
    single { provideRetrofit(get()) }
    single<UserDataSource.Remote> { RemoteUser() }
    single<UserRepository> { UserRepositoryIpml(get()) }
}

val chatModule = module {
    viewModel {
        ChatViewModel(get(), get(), get())
    }
    viewModel {
        HistoryContactViewModel(get())
    }
    single<ContactRepository> { ContactRepositoryImpl(get()) }
    single<ContactDataSource.Remote> { RemoteContact() }
    single<MessageDataSource.Remote> { RemoteMessage(get()) }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<UserDataSource.Remote> { RemoteUser() }
    single<UserRepository> { UserRepositoryIpml(get()) }
}

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule, chatModule)
        }
    }
}
