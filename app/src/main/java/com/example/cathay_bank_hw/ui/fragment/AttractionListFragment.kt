package com.example.cathay_bank_hw.ui.fragment

import android.content.Context
import android.gesture.GestureOverlayView.ORIENTATION_HORIZONTAL
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.model.CarouselCardModel
import com.example.cathay_bank_hw.model.SubActionModel
import com.example.cathay_bank_hw.ui.MainActivity
import com.example.cathay_bank_hw.ui.adapter.CarouselCardAdapter
import com.example.cathay_bank_hw.ui.adapter.MainAdapter
import com.example.cathay_bank_hw.ui.adapter.SubActionAdapter
import com.example.cathay_bank_hw.util.Dialog
import com.example.cathay_bank_hw.util.ExtendFunction.setActionBarTitle
import com.example.cathay_bank_hw.util.LocaleHelper
import com.example.cathay_bank_hw.viewmodel.AttractionListViewModel
import kotlinx.android.synthetic.main.fragment_attraction_list.*


class AttractionListFragment : Fragment() {
    private lateinit var attractionListViewModel : AttractionListViewModel
    private lateinit var mAdapter : MainAdapter
    private lateinit var subActionAdapter : SubActionAdapter

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
//        findLayoutElement(view)
        attractionListViewModel = ViewModelProviders.of(this)[AttractionListViewModel::class.java]
        //set lang context
        langFileName = getResourcesName(attractionListViewModel.currentLang.value?:"zh-tw")
        langContext = LocaleHelper.setLocale(requireContext() as MainActivity, langFileName)

        return view
    }

    private fun initCarouselCard(
        cardAdapter: CarouselCardAdapter,
        cardList: List<CarouselCardModel>
    ) {
        carousel_viewPager.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            adapter = cardAdapter.apply {
                val recyclerView = carousel_viewPager.getChildAt(0) as RecyclerView
                val totalItemCount = cardList.size
                recyclerView.apply {
                    addOnScrollListener(
                        InfiniteScrollBehaviour(
                            totalItemCount,
                            layoutManager as LinearLayoutManager
                        )
                    )
                }
            }
        }

        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
        val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
        carousel_viewPager.setPageTransformer { page, position ->
            val viewPager = page.parent.parent as ViewPager2
            val offset = position * -(2 * offsetPx + pageMarginPx)
            if (viewPager.orientation == ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -offset
                } else {
                    page.translationX = offset
                }
            } else {
                page.translationY = offset
            }
        }
        carousel_viewPager.setCurrentItem(1, false)
    }

//    private fun findLayoutElement(view: View) {
//        recyclerView = view.findViewById(R.id.recycler_view)
//        progressbar = view.findViewById(R.id.progressBar)
//        subActionRecyclerView = view.findViewById(R.id.circle_action_recycler)
//        viewPager2 = view.findViewById(R.id.carousel_viewPager)
//    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setActionBarTitle( langContext?.resources.getString(R.string.app_title_list_fragment))
        initializeMainRecyclerView()
        initSubActionRecyclerView()
        val cardAdapter = CarouselCardAdapter()
        initCarouselCard(cardAdapter, attractionListViewModel.getCarouselData(this))
        //call api
        attractionListViewModel.getData( page = "1",true)
        initializeObservers()



        cardAdapter.setData(
            attractionListViewModel.getCarouselData(this)
        )


    }

    private fun initSubActionRecyclerView(){
        subActionList = createSubActionList(LocaleHelper.setLocale(requireContext() as MainActivity, "zh-tw"))

        subActionAdapter = SubActionAdapter().apply {
            setData(subActionList)
        }
        circle_action_recycler.apply {
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
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
    }
    private fun initializeObservers() {
        attractionListViewModel.attractionLiveData?.observe(requireActivity()){
            mAdapter.setData(it?.data?: emptyList())
        }
        attractionListViewModel.mShowProgressBar?.observe(requireActivity()){
            progressBar.visibility = if(it) View.VISIBLE else View.GONE
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

    fun openWebView(title:String,url:String){
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

class InfiniteScrollBehaviour(
    private val itemCount: Int,
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val firstItemVisible = layoutManager.findFirstVisibleItemPosition()
        val lastItemVisible = layoutManager.findLastVisibleItemPosition()

        if (firstItemVisible == (itemCount - 1) && dx > 0) {
            recyclerView.scrollToPosition(1)
        } else if (lastItemVisible == 0 && dx < 0) {
            recyclerView.scrollToPosition(itemCount-2)
        }
    }
}