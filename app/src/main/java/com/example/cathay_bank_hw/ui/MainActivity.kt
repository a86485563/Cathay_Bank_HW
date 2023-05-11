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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}