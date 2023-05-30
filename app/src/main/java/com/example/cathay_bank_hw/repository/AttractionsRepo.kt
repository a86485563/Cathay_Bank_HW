package com.example.cathay_bank_hw.repository

import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.model.Resource
import com.example.cathay_bank_hw.network.ApiProvider
import com.example.cathay_bank_hw.network.ApiService
import com.example.cathay_bank_hw.network.ApiServiceCoroutine

object AttractionsRepo : BaseRepo() {
    var service: ApiServiceCoroutine? = null
    suspend fun getAttractionList(lang : String?) : Resource<AttractionResponse>?{
        return safeApiCall {
            service?.getResult(lang = lang?:"zh-tw", query = "1")!!
        }
    }

    init {
        AttractionsRepo.service =
            ApiProvider.createService(
                ApiServiceCoroutine::class.java
            )
    }
}