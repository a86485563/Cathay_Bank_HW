package com.example.cathay_bank_hw.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.signature.ObjectKey
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.util.GlideApp
import java.net.CookieManager

class MainAdapter(val clickAction : (item : AttractionResponse.Data?)->Unit) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private var mList: List<AttractionResponse.Data>? = listOf()
    fun setData(list: List<AttractionResponse.Data>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class MainViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        //把layout檔的元件們拉進來，指派給當地變數
        val image = itemView.findViewById<ImageView>(R.id.main_image)
        val name = itemView.findViewById<TextView>(R.id.name_text)
        val content = itemView.findViewById<TextView>(R.id.content_text)

        fun bind(attraction: AttractionResponse.Data){
            //綁定當地變數與dataModel中的每個值
            if(attraction?.images?.isNotEmpty() == true){

                val path =  attraction.images?.get(0)?.src?:""

                GlideApp.with(itemView)
                    .load(path)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .centerCrop()
                    .into(image)
            }else{
                GlideApp.with(itemView)
                    .load(android.R.drawable.ic_menu_gallery)
                    .centerCrop()
                    .into(image)
            }
            name.text = attraction.name
            content.text = attraction.introduction

            itemView.setOnClickListener {
                clickAction(attraction)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        //載入項目模板
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.main_holder_item, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        mList?.get(position)?.let { holder.bind(it) }

    }

    override fun getItemCount() = mList?.size ?: 0
}