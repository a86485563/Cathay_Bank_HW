package com.example.cathay_bank_hw.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.viewmodel.AttractionListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var attractionListViewModel : AttractionListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        attractionListViewModel = ViewModelProviders.of(this)[AttractionListViewModel::class.java]
        attractionListViewModel.getData(lang = "zh-tw", page = "1",true)?.observe(this){
            val test = it
        }


    }
}