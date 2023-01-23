package com.easytools.currencyconverter

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface RatesApi {

    @GET(BuildConfig.API_KEY)
    fun getRates(): Call<JsonObject>

}