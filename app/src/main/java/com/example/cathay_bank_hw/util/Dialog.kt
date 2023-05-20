package com.example.cathay_bank_hw.util

import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cathay_bank_hw.ui.MainActivity

object Dialog {
    fun createDialog(title: String,activity: MainActivity,langs: Array<String>, clickAction :(Int)-> Unit){
        val builder = AlertDialog.Builder(activity)
        //點旁邊取消
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setItems(langs
        ) { _, index -> clickAction(index) }
        builder.create().show()

    }
}