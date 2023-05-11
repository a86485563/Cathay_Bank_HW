package com.example.cathay_bank_hw.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.ui.adapter.MainAdapter
import com.example.cathay_bank_hw.viewmodel.AttractionListViewModel


class AttractionListFragment : Fragment() {
    private lateinit var attractionListViewModel : AttractionListViewModel
    private lateinit var mAdapter : MainAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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

    private fun initializeRecyclerView() {
        mAdapter = MainAdapter {
            //開啟detail fragment
            val direction = AttractionListFragmentDirections.actionAttractionListFragmentToAttractionDetailFragment(
                it?.images?.get(0)?.src ?: "",
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

}