package com.example.cathay_bank_hw.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.model.*
import com.example.cathay_bank_hw.repository.AttractionsRepo
import com.example.cathay_bank_hw.ui.fragment.AttractionListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AttractionViewModel( ) : ViewModel() {
    private val _attractionList = MutableLiveData<AttractionStatus<AttractionResponse>>()
    val attractionList: LiveData<AttractionStatus<AttractionResponse>> = _attractionList

    private val _currentLang =  MutableLiveData<String>().apply {
        postValue("zh-tw")
    }

    val currentLang : LiveData<String> = _currentLang

    fun getList() {
        viewModelScope.launch(Dispatchers.IO) {
            _attractionList.postValue(AttractionStatus.Loading)
            _attractionList.postValue(AttractionsRepo.getAttractionListStatus(this@AttractionViewModel.currentLang.value))
        }

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

    fun getSubActionList(context: Context, fragment: AttractionListFragment):List<SubActionModel>{
        val urlList = AttractionsRepo.getSubList()
        var subActionList  = mutableListOf<SubActionModel>()
        val iconList = listOf<Int>(
            R.drawable.ic_attraction,
            R.drawable.ic_calendar,
            R.drawable.ic_hotel,
            R.drawable.ic_campaign,
            R.drawable.ic_traffic
        )
        for( i in urlList.indices){
            val title = context.resources.getString(R.string.sub_action_attraction)
            val icon = iconList[i]
            subActionList.add(SubActionModel(icon,title) {
                fragment.openWebView(
                    title,
                    urlList[i]
                )
            })
        }
        return  subActionList
    }

    fun setLang (lang: String?){
        this._currentLang.postValue(lang?:"zh-tw")
    }

}