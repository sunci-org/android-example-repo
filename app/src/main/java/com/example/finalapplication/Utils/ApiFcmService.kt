package com.example.finalapplication.utils

import com.example.finalapplication.data.model.Message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface ApiFcmService {

    @POST("send")
    fun sendRemoteMessage(
        @HeaderMap headers: HashMap<String, String>,
        @Body body: Message
    ): Call<Message>
}
