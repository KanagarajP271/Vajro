package com.example.vajronew.service

import com.example.vajronew.model.Root
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("v2/5def7b172f000063008e0aa2")
    suspend fun getAllProducts() : Response<Root>

    companion object {
        var BASE_URL = "http://www.mocky.io/"


        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if(BASE_URL.startsWith("http://")){
                BASE_URL = BASE_URL.replaceFirst("http","https")
            }
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}