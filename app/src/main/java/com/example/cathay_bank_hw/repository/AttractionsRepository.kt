package com.example.cathay_bank_hw.repository

import androidx.lifecycle.MutableLiveData
import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.model.GenericDataModel
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

    fun getAttractionResultsMock(lang: String?, callback: NetworkResponseCallback, forceFetch : Boolean) : MutableLiveData<AttractionResponse?>?{
        mCallback = callback
//        if (mCountryList.value!!.isNotEmpty() && !forceFetch) {
//            mCallback.onNetworkSuccess()
//            return mCountryList
//        }
//        mAttractionResponseCall = RestClient.getInstance().getApiService().getCountries()
//        mAttractionResponseCall.enqueue(object : Callback<List<AttractionResponse>> {
//
//            override fun onResponse(call: Call<List<AttractionResponse>>, response: Response<List<AttractionResponse>>) {
//                mCountryList.value = response.body()
//                mCallback.onNetworkSuccess()
//            }
//
//            override fun onFailure(call: Call<List<AttractionResponse>>, t: Throwable) {
//                mCountryList.value = emptyList()
//                mCallback.onNetworkFailure(t)
//            }
//
//        })
        Thread.sleep(1000)
        val mockData =
                AttractionResponse(total = 15, data = listOf(
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。"),
                    AttractionResponse.Data(id = 370, name = "碧山露營場", introduction = "內湖「碧山露營場」位於內湖區大崙尾山麓之碧溪產業道路上，是一處可容納250人露宿的露營場，是臺北市僅有的2處露營場地之一，也是全國少有的免費露營場。碧山露營場大致分為三個區域：森林步道、露營區和遊憩活動區。碧山露營場森林步道區內，有長約1公里的棧道，來回行程約半小時，徜徉在碧山蒼翠的樹林內漫步，閒雲野鶴的樂趣，是一大幸福。\r\n\r\n碧山露營場因山區光害少，是臺北觀星最好的地方，山上夜景更是美麗，可遠望觀音山及士林北投夜色。內行人和一些鐵馬族多利用通往此區之金龍產業道路騎遊，一面賞景一面涼風吹來，是一趟美麗的路程。")))

        mAttractionList?.value = mockData
        return mAttractionList
    }

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