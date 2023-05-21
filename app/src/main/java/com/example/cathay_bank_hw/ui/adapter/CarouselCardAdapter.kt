package com.example.cathay_bank_hw.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.model.CarouselCardModel
import com.example.cathay_bank_hw.model.SubActionModel

class CarouselCardAdapter :  RecyclerView.Adapter<CarouselCardAdapter.CarouselCardViewHolder>() {
    private var mList: List<CarouselCardModel>? = listOf()
    fun setData(list: List<CarouselCardModel>) {
        mList = list
        notifyDataSetChanged()
    }

    inner class CarouselCardViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        //把layout檔的元件們拉進來，指派給當地變數
        val image : ImageView? = itemView.findViewById<ImageView>(R.id.carousel_card_imageView)



        fun bind(item : CarouselCardModel){
            //綁定當地變數與dataModel中的每個值
            Glide
                .with(itemView)
                .load(item.imgPath)
                .centerCrop()
                .into(image!!)

            itemView.setOnClickListener{
                item.clickAction?.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselCardViewHolder {
        //載入項目模板
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.carousel_card_holder, parent, false)
        return CarouselCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselCardViewHolder, position: Int) {
        mList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
       return mList?.size?:0
    }

}