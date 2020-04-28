package com.example.coronavirusstat.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://corona.lmao.ninja/"

class CountriesApi {
    companion object {
        val instanceServiceApi: ServiceApi by lazy {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            retrofit.create(ServiceApi::class.java)
        }
    }
}