package com.example.cathay_bank_hw.ui.fragment

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
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
import kotlinx.android.synthetic.main.fragment_attraction_detail.*

class AttractionDetailFragment : Fragment() {
    private val args : AttractionDetailFragmentArgs by navArgs()
    private var imageUrl = ""
    private var name = ""
    private var content = ""
    private var linkUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_attraction_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgument()

        Glide.with(requireContext()).load(imageUrl).into(detail_imageView)

        setActionBarTitle(name)

    }


    private fun getArgument() {
        imageUrl = args.imageUrl
        name = args.name
        content = args.content
        linkUrl = args.linkUrl
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val langIcon = menu.findItem(R.id.item_lang)
        langIcon?.isVisible = false
        super.onPrepareOptionsMenu(menu)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        detail_name_textView.text = name
        detail_content_textView.text = content
        detail_url_textView.text = linkUrl
        detail_url_textView.setOnClickListener{

            val direction = AttractionDetailFragmentDirections.actionAttractionDetailFragmentToWebviewNavFragment(
                linkUrl,
                name
            )
            findNavController().navigate(direction)
        }
    }



}