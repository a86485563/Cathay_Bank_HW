package com.example.cathay_bank_hw.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.model.SubActionModel
import com.example.cathay_bank_hw.ui.MainActivity
import com.example.cathay_bank_hw.ui.adapter.MainAdapter
import com.example.cathay_bank_hw.ui.adapter.SubActionAdapter
import com.example.cathay_bank_hw.util.Dialog
import com.example.cathay_bank_hw.util.ExtendFunction.setActionBarTitle
import com.example.cathay_bank_hw.util.LocaleHelper
import com.example.cathay_bank_hw.viewmodel.AttractionListViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class AttractionListFragment : Fragment() {
    private lateinit var attractionListViewModel : AttractionListViewModel
    private lateinit var mAdapter : MainAdapter
    private lateinit var subActionAdapter : SubActionAdapter
    private lateinit var subActionRecyclerView: RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressbar : ProgressBar
    private lateinit var langFileName : String
    private lateinit var langContext: Context

    private val langs = arrayOf("en","zh-tw","ja","ko","th")
    val IMAGE_DEFAULT_URL = "https://data.taipei/img/department.2fd5d7eb.png"



    private lateinit var subActionList : List<SubActionModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_attraction_list, container, false)
        findLayoutElement(view)
        attractionListViewModel = ViewModelProviders.of(this)[AttractionListViewModel::class.java]
        //call api
        attractionListViewModel.getData( page = "1",true)
        initializeMainRecyclerView()
        initSubActionRecyclerView()
        initializeObservers()


        return view
    }

    private fun findLayoutElement(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        progressbar = view.findViewById(R.id.progressBar)
        subActionRecyclerView = view.findViewById(R.id.circle_action_recycler)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setActionBarTitle(LocaleHelper.getDefaultRes(requireContext() as MainActivity).getString(R.string.app_title_list_fragment))
    }

    private fun initSubActionRecyclerView(){
        subActionList = createSubActionList(LocaleHelper.setLocale(requireContext() as MainActivity, "zh-tw"))

        subActionAdapter = SubActionAdapter().apply {
            setData(subActionList)
        }
        subActionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
            adapter = subActionAdapter
        }

    }

    private fun initializeMainRecyclerView() {
        mAdapter = MainAdapter {
            var imageUrl : String = if (it?.images?.isNotEmpty() == true) it?.images?.get(0)?.src ?: IMAGE_DEFAULT_URL else IMAGE_DEFAULT_URL
            //開啟detail fragment
            val direction = AttractionListFragmentDirections.actionAttractionListFragmentToAttractionDetailFragment(
                imageUrl,
                it?.name ?: "",
                it?.introduction ?: "",
                it?.url ?: ""
            )
            findNavController().navigate(direction)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }
    private fun initializeObservers() {
        attractionListViewModel.attractionLiveData?.observe(requireActivity()){
            mAdapter.setData(it?.data?: emptyList())
        }
        attractionListViewModel.mShowProgressBar?.observe(requireActivity()){
            progressbar.visibility = if(it) View.VISIBLE else View.GONE
        }

        attractionListViewModel.currentLang?.observe(requireActivity()){
            //切換resource
            langFileName = getResourcesName(it)
            langContext = LocaleHelper.setLocale(requireContext() as MainActivity, langFileName)
            //設定title
            setActionBarTitle(langContext?.resources.getString(R.string.app_title_list_fragment))
            //切換subAction
            subActionList = createSubActionList(langContext)
            subActionAdapter.setData(subActionList)
            //切換recyclerView
            attractionListViewModel.getData( page = "1",true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_lang -> {
                //開啟dialog
                Dialog.createDialog(langContext.getString(R.string.language_setting),this.requireActivity() as MainActivity ,langs){ index ->
                    Toast.makeText(this.requireActivity() as MainActivity, langs[index], Toast.LENGTH_LONG).show()
                    //切換語系。
                    attractionListViewModel.setLang( langs[index] )

                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createSubActionList(context: Context) =
        listOf(
            SubActionModel(
                R.drawable.ic_attraction,
                context.resources.getString(R.string.sub_action_attraction)){
                openWebView(
                    context.resources.getString(R.string.sub_action_attraction),
                    "https://www.travel.taipei/zh-tw/attraction/popular-area"
                )
            },
            SubActionModel(
                R.drawable.ic_calendar, context.resources.getString(R.string.sub_action_calendar)
            ){
                openWebView(
                    context.resources.getString(R.string.sub_action_calendar),
                    "https://www.travel.taipei/zh-tw/event-calendar/2023"
                )
            },
            SubActionModel(
                R.drawable.ic_hotel, context.resources.getString(R.string.sub_action_accom)
            ){
                openWebView(
                    context.resources.getString(R.string.sub_action_accom),
                    "https://taiwanstay.net.tw/legal-hotel-list?start=0&offset=50&search_keyword=&hoci_city=%E8%87%BA%E5%8C%97%E5%B8%82"
                )
            },
            SubActionModel(
                R.drawable.ic_campaign, context.resources.getString(R.string.sub_action_campaign)
            ){
                openWebView(
                    context.resources.getString(R.string.sub_action_campaign),
                    "https://www.travel.taipei/zh-tw/activity?page=1"
                )
            },
            SubActionModel(
                R.drawable.ic_traffic, context.resources.getString(R.string.sub_action_traffic)
            ){
                openWebView(
                    context.resources.getString(R.string.sub_action_traffic),
                    "https://www.travel.taipei/zh-tw/information/trafficlist"
                )
            }
        )

    private fun openWebView(title:String,url:String){
        val direction = AttractionListFragmentDirections.actionAttractionListFragmentToWebviewNavFragment(url,title)
        findNavController().navigate(direction)
    }

    private fun getResourcesName(lang : String) : String{
        return when(lang){
            "en" -> "en"
            "zh-tw" -> "zh-rTW"
            "ja" -> "ja"
            "ko" -> "ko"
            "th" -> "th"
            else -> "en"
        }

    }


}