package com.example.todoapp.networks

import com.example.todoapp.Constants.Companion.BASE_URL
import com.example.todoapp.Constants.Companion.TOKEN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val httpClient by lazy {
        OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer $TOKEN")
                .build()
            chain.proceed(request)
        }.build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

    }
    val api: ToDoApi by lazy {
        retrofit.create(ToDoApi::class.java)
    }
}
