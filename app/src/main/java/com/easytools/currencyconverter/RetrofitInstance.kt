package com.easytools.currencyconverter

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api by lazy {
        //create instance of Retrofit
        val retrofitInstance = Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //create instance of RatesApi
        val RatesApiInstance = retrofitInstance.create(RatesApi::class.java)

        RatesApiInstance
    }

}