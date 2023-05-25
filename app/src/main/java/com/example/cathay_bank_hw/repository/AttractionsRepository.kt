package com.example.cathay_bank_hw.repository

import androidx.lifecycle.MutableLiveData
import com.example.cathay_bank_hw.model.AttractionResponse
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

    init {
        apiService =
            ApiProvider.createService(
                ApiService::class.java
            )
    }

}