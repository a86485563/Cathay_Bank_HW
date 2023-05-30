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
import com.example.cathay_bank_hw.model.Resource
import com.example.cathay_bank_hw.model.SubActionModel
import com.example.cathay_bank_hw.ui.MainActivity
import com.example.cathay_bank_hw.ui.adapter.CarouselCardAdapter
import com.example.cathay_bank_hw.ui.adapter.MainAdapter
import com.example.cathay_bank_hw.ui.adapter.SubActionAdapter
import com.example.cathay_bank_hw.util.Dialog
import com.example.cathay_bank_hw.util.ExtendFunction.setActionBarTitle
import com.example.cathay_bank_hw.util.LocaleHelper
import com.example.cathay_bank_hw.viewmodel.AttractionViewModel
import kotlinx.android.synthetic.main.fragment_attraction_list.*


class AttractionListFragment : Fragment() {
    private lateinit var mAdapter : MainAdapter
    private lateinit var subActionAdapter : SubActionAdapter
    private lateinit var attractionViewModel: AttractionViewModel

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

        attractionViewModel = ViewModelProviders.of(this)[AttractionViewModel::class.java]

        //set lang context
        langFileName = getResourcesName(attractionViewModel.currentLang.value?:"zh-tw")
        langContext = LocaleHelper.setLocale(requireContext() as MainActivity, langFileName)

        return view
    }

    private fun initCarouselCard(
        cardAdapter: CarouselCardAdapter,
        listSize: Int
    ) {
        carousel_viewPager.apply {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            adapter = cardAdapter.apply {
                val recyclerView = carousel_viewPager.getChildAt(0) as RecyclerView
                val totalItemCount = listSize
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setActionBarTitle( langContext?.resources.getString(R.string.app_title_list_fragment))
        initializeMainRecyclerView()
        initSubActionRecyclerView()
        val cardAdapter = CarouselCardAdapter()
        val carouselList = attractionViewModel.getCarouselData(this)
        cardAdapter.setData(
            carouselList
        )
        initCarouselCard(cardAdapter, carouselList.size)
        //call api
//        attractionListViewModel.getData( page = "1",true)
        attractionViewModel.getList()

        initializeObservers()



    }

    private fun initSubActionRecyclerView(){
        subActionList = attractionViewModel.getSubActionList(LocaleHelper.setLocale(requireContext() as MainActivity, "zh-tw"),this)

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

        attractionViewModel.currentLang?.observe(requireActivity()){
            //切換resource
            langFileName = getResourcesName(it)
            langContext = LocaleHelper.setLocale(requireContext() as MainActivity, langFileName)
            //設定title
            setActionBarTitle(langContext?.resources.getString(R.string.app_title_list_fragment))
            //切換subAction
            subActionList =  attractionViewModel.getSubActionList(LocaleHelper.setLocale(requireContext() as MainActivity, it),this)
            subActionAdapter.setData(subActionList)
            //切換recyclerView
            attractionViewModel.getList()
        }

        attractionViewModel.attractionList.observe(requireActivity()){
           when(it) {
               is  Resource.Loading ->{
                   progressBar.visibility = View.VISIBLE
                   mAdapter.setData(emptyList())
               }
               is Resource.Success -> {
                   progressBar.visibility = View.GONE
                   mAdapter.setData(it?.data?.data ?: emptyList())
               }
               else ->{
                   progressBar.visibility = View.GONE
                   Toast.makeText(this.requireActivity() as MainActivity,"Oops Something Wrong",Toast.LENGTH_SHORT).show()
                   mAdapter.setData(emptyList())
               }
           }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_lang -> {
                //開啟dialog
                Dialog.createDialog(langContext.getString(R.string.language_setting),this.requireActivity() as MainActivity ,langs){ index ->
                    Toast.makeText(this.requireActivity() as MainActivity, langs[index], Toast.LENGTH_LONG).show()
                    //切換語系。
                    attractionViewModel.setLang( langs[index] )

                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



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