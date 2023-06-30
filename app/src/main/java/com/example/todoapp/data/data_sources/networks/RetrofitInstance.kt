package com.example.todoapp.data.data_sources.networks

import com.example.todoapp.utils.Constants.Companion.BASE_URL
import com.example.todoapp.utils.Constants.Companion.TOKEN
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
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
    val api: ToDoApi by lazy {
        retrofit.create(ToDoApi::class.java)
    }
}
