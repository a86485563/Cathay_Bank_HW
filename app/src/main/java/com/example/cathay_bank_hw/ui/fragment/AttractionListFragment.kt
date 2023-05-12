package com.example.cathay_bank_hw.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.ui.MainActivity
import com.example.cathay_bank_hw.ui.adapter.MainAdapter
import com.example.cathay_bank_hw.util.Dialog
import com.example.cathay_bank_hw.util.ExtendFunction.setActionBarTitle
import com.example.cathay_bank_hw.viewmodel.AttractionListViewModel


class AttractionListFragment : Fragment() {
    private lateinit var attractionListViewModel : AttractionListViewModel
    private lateinit var mAdapter : MainAdapter
    private lateinit var recyclerView: RecyclerView
    private val langs = arrayOf("en","zh-tw","ja")
    val IMAGE_DEFAULT_URL = "https://data.taipei/img/department.2fd5d7eb.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

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
        recyclerView =  view.findViewById(R.id.recycler_view)
        attractionListViewModel = ViewModelProviders.of(this)[AttractionListViewModel::class.java]
        initializeRecyclerView()
        initializeObservers()
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setActionBarTitle("台北旅遊")
    }

    private fun initializeRecyclerView() {
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_lang -> {
                val test = "1234"
                //開啟dialog
                Dialog.createDialog(this.requireActivity() as MainActivity ,langs){
                    Toast.makeText(this.requireActivity() as MainActivity, langs[it], Toast.LENGTH_LONG).show()
                    attractionListViewModel.getData(lang = langs[it], page = "1",true)?.observe(requireActivity()){
                        if(it != null){
                            mAdapter.setData(it?.data!!)
                        }
                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}