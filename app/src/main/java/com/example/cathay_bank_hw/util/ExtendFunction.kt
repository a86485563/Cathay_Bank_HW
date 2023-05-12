package com.example.cathay_bank_hw.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

object ExtendFunction {
    fun Fragment.setActionBarTitle( title : String ){
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        actionBar?.title = title
    }
}