package com.example.frnd_assignment.remote

import com.example.frnd_assignment.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {

   private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()
    }


}