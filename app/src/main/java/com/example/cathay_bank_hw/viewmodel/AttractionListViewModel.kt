package com.example.cathay_bank_hw.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.model.GenericDataModel
import com.example.cathay_bank_hw.network.NetworkHelper
import com.example.cathay_bank_hw.network.NetworkResponseCallback
import com.example.cathay_bank_hw.repository.AttractionsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttractionListViewModel(private val app: Application) : AndroidViewModel(app) {
    var attractionLiveData: MutableLiveData<AttractionResponse?>? = MutableLiveData()
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    val currentLang = MutableLiveData<String>().apply {
        postValue("zh-tw")
    }


    fun getData(page : String? ,forceFetch : Boolean){

        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            this.attractionLiveData = AttractionsRepository.getAttractionResults(lang = this.currentLang.value,page = page,object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }
                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch)

        } else {
            mShowNetworkError.value = true
        }
    }
    fun setLang (lang: String?){
        this.currentLang.postValue(lang?:"zh-tw")
    }
}