package com.example.todoapp.di

import com.example.todoapp.data.data_sources.networks.ToDoApi
import com.example.todoapp.utils.Constants.Companion.BASE_URL
import com.example.todoapp.utils.Constants.Companion.TOKEN
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor {chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer $TOKEN")
                .build()
            chain.proceed(request)
        }.build()
    }


    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideToDoApi(retrofit: Retrofit) : ToDoApi {
        return retrofit.create(ToDoApi::class.java)
    }

}