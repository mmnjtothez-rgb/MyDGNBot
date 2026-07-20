package com.mydgnbot.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {


    private const val BASE_URL =
        "https://www.mydgn.com/"


    private val loggingInterceptor =

        HttpLoggingInterceptor().apply {

            level =
                HttpLoggingInterceptor.Level.BODY

        }


    private val client =

        OkHttpClient.Builder()

            .addInterceptor(
                loggingInterceptor
            )

            .build()



    val api: MyDgnApiService =

        Retrofit.Builder()

            .baseUrl(BASE_URL)

            .client(client)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()

            .create(
                MyDgnApiService::class.java
            )

}
