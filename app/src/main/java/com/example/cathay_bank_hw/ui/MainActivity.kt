package com.example.cathay_bank_hw.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.ui.adapter.MainAdapter
import com.example.cathay_bank_hw.viewmodel.AttractionListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var attractionListViewModel : AttractionListViewModel
    private lateinit var mAdapter : MainAdapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView =  findViewById<RecyclerView>(R.id.recycler_view)
        attractionListViewModel = ViewModelProviders.of(this)[AttractionListViewModel::class.java]

        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mAdapter = MainAdapter {
            Toast.makeText(applicationContext, "${it?.name?:""}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.apply {
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }
    private fun initializeObservers() {
        attractionListViewModel.getData(lang = "zh-tw", page = "1",true)?.observe(this){
            if(it != null){
                mAdapter.setData(it?.data!!)
            }
        }
    }
}