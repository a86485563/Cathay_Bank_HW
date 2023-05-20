package com.example.cathay_bank_hw.ui.fragment

import android.app.Activity.RESULT_OK
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
    private val langs = arrayOf("en","zh-tw","ja","ko","th")
    val IMAGE_DEFAULT_URL = "https://data.taipei/img/department.2fd5d7eb.png"
    private var currentLang = "zh-tw"


    private val subActionList : List<SubActionModel> = listOf(SubActionModel(R.drawable.ic_attraction,"Attraction"),
        SubActionModel(R.drawable.ic_calendar,"Calendar"),
        SubActionModel(R.drawable.ic_hotel,"Accommodation"),
        SubActionModel(R.drawable.ic_campaign,"Campaign"),
        SubActionModel(R.drawable.ic_traffic,"traffic")
    )


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
        initializeMainRecyclerView()
        initSubActionRecyclerView()
        MainScope().launch {
            initializeObservers()
        }

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
        attractionListViewModel.getData(lang = "zh-tw", page = "1",true)?.observe(requireActivity()){
            if(it != null){
                mAdapter.setData(it?.data!!)
            }
        }
        attractionListViewModel.mShowProgressBar?.observe(requireActivity()){
            progressbar.visibility = if(it) View.VISIBLE else View.GONE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_lang -> {
                //開啟dialog
                Dialog.createDialog(this.requireActivity() as MainActivity ,langs){ index ->
                    Toast.makeText(this.requireActivity() as MainActivity, langs[index], Toast.LENGTH_LONG).show()
                    attractionListViewModel.getData(lang = langs[index], page = "1",true)?.observe(requireActivity()){
                        if(it != null){
                            mAdapter.setData(it.data!!)
                        }
                    }
                    //切換語系。

                    if(currentLang != langs[index]){
                        val langFileName = getResourcesName(langs[index])
                        val context = LocaleHelper.setLocale(requireContext() as MainActivity, langFileName)
                        setActionBarTitle(context.resources.getString(R.string.app_title_list_fragment))
                    }
                    currentLang = langs[index]

                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
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