package com.example.cathay_bank_hw.repository

import androidx.lifecycle.MutableLiveData
import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.model.SubActionModel
import com.example.cathay_bank_hw.network.ApiProvider
import com.example.cathay_bank_hw.network.ApiService
import com.example.cathay_bank_hw.network.NetworkResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AttractionsRepository {
    var apiService: ApiService? = null
    private lateinit var mCallback: NetworkResponseCallback
    private var mAttractionList: MutableLiveData<AttractionResponse?>? =
        MutableLiveData<AttractionResponse?>().apply { value = null }
    private lateinit var mAttractionResponseCall: Call<AttractionResponse?>

    

    fun getAttractionResults(lang: String?,page: String?, callback: NetworkResponseCallback, forceFetch : Boolean) : MutableLiveData<AttractionResponse?>?{
        mCallback = callback
        if (mAttractionList?.value !=null && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mAttractionList
        }
        mAttractionResponseCall = apiService!!.getResult(lang = lang?:"zh-tw", query = "1")
        mAttractionResponseCall.enqueue(object : Callback<AttractionResponse?> {

            override fun onResponse(call: Call<AttractionResponse?>, response: Response<AttractionResponse?>) {
                mAttractionList?.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<AttractionResponse?>, t: Throwable) {
                mAttractionList?.value = null
                mCallback.onNetworkFailure(t)
            }

        })


        return mAttractionList
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
        apiService =
            ApiProvider.createService(
                ApiService::class.java
            )
    }

}