package com.example.cathay_bank_hw.repository

import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.model.AttractionStatus
import com.example.cathay_bank_hw.model.Resource
import com.example.cathay_bank_hw.network.ApiProvider
import com.example.cathay_bank_hw.network.ApiServiceCoroutine

object AttractionsRepo : BaseRepo() {
    var service: ApiServiceCoroutine? = null

    suspend fun getAttractionListStatus(lang : String?) : AttractionStatus<AttractionResponse>{
        return when(val result = safeApiCall { service?.getResult(lang = lang?:"zh-tw", query = "1")!!}  ){
            is Resource.Loading -> AttractionStatus.Loading
            is Resource.Success -> {
               when (result.data?.total ){
                   0 -> AttractionStatus.NoContent
                   else -> AttractionStatus.Success(result.data!!)
                }
            }
            else -> AttractionStatus.Error
        }
    }

    fun getSubList() : List<String>{
        return listOf(
            "https://www.travel.taipei/zh-tw/attraction/popular-area",
            "https://www.travel.taipei/zh-tw/event-calendar/2023",
            "https://taiwanstay.net.tw/legal-hotel-list?start=0&offset=50&search_keyword=&hoci_city=%E8%87%BA%E5%8C%97%E5%B8%82",
            "https://www.travel.taipei/zh-tw/activity?page=1",
            "https://www.travel.taipei/zh-tw/information/trafficlist",
        )
    }

    init {
        AttractionsRepo.service =
            ApiProvider.createService(
                ApiServiceCoroutine::class.java
            )
    }
}