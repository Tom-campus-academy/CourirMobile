package com.formationandroid.courir.ws

import retrofit2.Call
import retrofit2.http.*


interface WSInterface {
    @GET("Models")
    fun getModels(): Call<MutableList<ReturnWSModel>>

    @POST("Models")
    fun postModel(@Body body: ReturnWSModel): Call<ReturnWSModel>

    @HTTP(method = "PUT", hasBody = true)
    fun putModel(@Body body: ReturnWSModel, @Url url :String): Call<ReturnWSModel>

    @HTTP(method = "DELETE", hasBody = true)
    fun deleteModel(@Body body: ReturnWSModel, @Url url :String): Call<ReturnWSModel>

    @HTTP(method = "GET")
    fun filter(@Url url :String): Call<MutableList<ReturnWSModel>>

    @GET("Stocks")
    fun getStocks(): Call<MutableList<ReturnWSStock>>

    @POST("Stocks")
    fun postStock(@Body body: ReturnWSStock): Call<ReturnWSStock>

    @HTTP(method = "PUT", hasBody = true)
    fun putStock(@Body body: ReturnWSStock, @Url url :String): Call<ReturnWSStock>

    @HTTP(method = "DELETE", hasBody = true)
    fun deleteStock(@Body body: ReturnWSStock, @Url url :String): Call<ReturnWSStock>

    @HTTP(method = "GET")
    fun filterStock(@Url url :String): Call<MutableList<ReturnWSStock>>
}