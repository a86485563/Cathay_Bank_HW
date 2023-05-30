package com.example.cathay_bank_hw.network

import com.example.cathay_bank_hw.model.AttractionResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiServiceCoroutine {
    companion object {
        const val BASE_URL: String = "https://www.travel.taipei/"
    }

    @Headers("accept: application/json")
    @GET("/open-api/{lang}/Attractions/All")
    suspend fun getResult(@Path("lang") lang : String?,
        @Query("page") query: String?): Response<AttractionResponse>
}