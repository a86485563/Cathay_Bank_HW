package com.example.cathay_bank_hw.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.util.ExtendFunction.setActionBarTitle


class AttractionDetailFragment : Fragment() {
    private val args : AttractionDetailFragmentArgs by navArgs()
    private var imageUrl = ""
    private var name = ""
    private var content = ""
    private var linkUrl = ""

    private lateinit var mainImage: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var linkTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_attraction_detail, container, false)
        mainImage = view.findViewById(R.id.detail_imageView)
        nameTextView = view.findViewById(R.id.detail_name_textView)
        contentTextView = view.findViewById(R.id.detail_content_textView)
        linkTextView = view.findViewById(R.id.detail_url_textView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()

        Glide.with(requireContext()).load(imageUrl).into(mainImage)
        nameTextView.text = name
        contentTextView.text = content
        linkTextView.text = linkUrl
        linkTextView.setOnClickListener{
//            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
//            transaction.replace(R.id.navHost, WebviewFragment.newInstance(linkUrl))
//            transaction.commit()
            val direction = AttractionDetailFragmentDirections.actionAttractionDetailFragmentToWebviewNavFragment(
                linkUrl
            )
            findNavController().navigate(direction)
        }
        setActionBarTitle(name)

    }

    private fun getArgument() {
        imageUrl = args.imageUrl
        name = args.name
        content = args.content
        linkUrl = args.linkUrl
    }

    fun changeFragment(){

    }

}