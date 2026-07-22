package com.mydgnbot.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val MYDGN_BASE_URL =
        "https://api.mydgn.com/"

    private const val FUTGG_BASE_URL =
        "https://www.fut.gg/"

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


    private val myDgnRetrofit =

        Retrofit.Builder()

            .baseUrl(MYDGN_BASE_URL)

            .client(client)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()


    private val futGgRetrofit =

        Retrofit.Builder()

            .baseUrl(FUTGG_BASE_URL)

            .client(client)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()


    val api: MyDgnApiService =

        myDgnRetrofit.create(
            MyDgnApiService::class.java
        )


    val futGgApi: FutGgApiService =

        futGgRetrofit.create(
            FutGgApiService::class.java
        )

}
