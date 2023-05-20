package com.example.cathay_bank_hw.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.model.AttractionResponse
import com.example.cathay_bank_hw.model.SubActionModel

class SubActionAdapter: RecyclerView.Adapter<SubActionAdapter.SubViewHolder>()  {
    private var mList: List<SubActionModel>? = listOf()
    fun setData(list: List<SubActionModel>) {
        if(!mList.isNullOrEmpty()) mList = listOf()
        mList = list
        notifyDataSetChanged()
    }

    inner class SubViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        //把layout檔的元件們拉進來，指派給當地變數
        val image : ImageView? = itemView.findViewById<ImageView>(R.id.sub_action_image)
        val title: TextView? = itemView.findViewById<TextView>(R.id.sub_name_text)


        fun bind(item : SubActionModel,backgroundColor: Int){
            //綁定當地變數與dataModel中的每個值
            Glide.with(itemView).load(item.image).into(image!!)
            title?.text = item.title
            image.setBackgroundResource(backgroundColor)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubActionAdapter.SubViewHolder {
        //載入項目模板
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.sub_action_card_holder, parent, false)
        return SubViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubActionAdapter.SubViewHolder, position: Int) {
        if((position % 2) == 1){
            mList?.get(position)?.let { holder.bind(it,R.color.background_circle_image_light ) }
        }else{
            mList?.get(position)?.let { holder.bind(it,R.color.background_circle_image_dark) }
        }

    }

    override fun getItemCount() = mList?.size ?: 0

}