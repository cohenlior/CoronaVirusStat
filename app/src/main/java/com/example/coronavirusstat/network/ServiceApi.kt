package com.example.coronavirusstat.network

import com.example.coronavirusstat.model.Country
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceApi {
    @GET("/v2/countries")
    suspend fun getCountries(@Query("yesterday") untilYesterday: Boolean): List<Country>
}