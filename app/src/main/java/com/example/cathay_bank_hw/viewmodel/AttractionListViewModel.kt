package com.example.cathay_bank_hw.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.model.CarouselCardModel
import com.example.cathay_bank_hw.network.NetworkHelper
import com.example.cathay_bank_hw.network.NetworkResponseCallback
import com.example.cathay_bank_hw.repository.AttractionsRepository
import com.example.cathay_bank_hw.ui.MainActivity
import com.example.cathay_bank_hw.ui.fragment.AttractionListFragment
import com.example.cathay_bank_hw.ui.fragment.AttractionListFragmentDirections

class AttractionListViewModel(private val app: Application) : AndroidViewModel(app) {
    var attractionLiveData: MutableLiveData<AttractionResponse?>? = MutableLiveData()
    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()

    val currentLang = MutableLiveData<String>().apply {
        postValue("zh-tw")
    }

    fun getCarouselData(fragment: AttractionListFragment) : List<CarouselCardModel>{
        return listOf(
            //加上最後一個
            CarouselCardModel("https://www.travel.taipei/content/images/banner/373386/compressed_banner-image-agkpbptsjemio0oakxegrg.jpg"){
                fragment.openWebView("","https://www.youtube.com/@taipeitravelofficial")
            },

            CarouselCardModel("https://www.travel.taipei/content/images/banner/178795/compressed_banner-image-1twnqnspwuwpbeob8dj4gq.jpg"){
                fragment.openWebView("","https://www.travel.taipei/zh-tw/souvenir")
            },
            CarouselCardModel("https://www.travel.taipei/content/images/banner/224195/compressed_banner-image-6yzumrtw-egqonfakxr9qg.jpg"){
                fragment.openWebView("","https://www.travel.taipei/immersive/#/intro")
            },
            CarouselCardModel("https://www.travel.taipei/content/images/banner/373335/compressed_banner-image-9chsu2mliuyo59a3z7p1cw.jpg"){
                fragment.openWebView("","https://www.travel.taipei/zh-tw/news/details/46594")
            },
            CarouselCardModel("https://www.travel.taipei/content/images/banner/374257/compressed_banner-image-vtu09hzc8uubmzvnux3ahg.jpg"){
                fragment.openWebView("","https://2023letsgotaipei.taipei/")
            },
            CarouselCardModel("https://www.travel.taipei/content/images/banner/373386/compressed_banner-image-agkpbptsjemio0oakxegrg.jpg"){
                fragment.openWebView("","https://www.youtube.com/@taipeitravelofficial")
            },
            //第一個
            CarouselCardModel("https://www.travel.taipei/content/images/banner/178795/compressed_banner-image-1twnqnspwuwpbeob8dj4gq.jpg"){
                fragment.openWebView("","https://www.travel.taipei/zh-tw/souvenir")
            }
        )
    }


    fun getData(page : String? ,forceFetch : Boolean) {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true
            attractionLiveData = AttractionsRepository.getAttractionResults(lang = this.currentLang.value,page = page,object : NetworkResponseCallback {
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