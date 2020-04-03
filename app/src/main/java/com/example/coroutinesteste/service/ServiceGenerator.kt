package com.example.coroutinesteste.service

import com.example.coroutinesteste.BuildConfigHelper
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceGenerator {

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(
                    HttpLoggingInterceptor.Level.BODY
                )
            )
            .build()
    }

    inline fun <reified C> provideRetrofit(okHttpClient: OkHttpClient): C {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfigHelper.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(C::class.java)

    }
}
