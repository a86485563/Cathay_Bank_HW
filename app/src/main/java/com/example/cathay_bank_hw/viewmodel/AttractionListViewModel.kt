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
    var attractionLiveData: MutableLiveData<AttractionResponse?>? = null
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()


    fun getData(lang : String?,page : String? ,forceFetch : Boolean): MutableLiveData<AttractionResponse?>? {

        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            attractionLiveData = AttractionsRepository.getAttractionResults(lang = lang,page = page,object : NetworkResponseCallback {
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
        return attractionLiveData
    }
}