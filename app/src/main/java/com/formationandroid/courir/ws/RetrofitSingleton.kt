package com.formationandroid.courir.ws

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitSingleton {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.5:5000/V1/Courir/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}