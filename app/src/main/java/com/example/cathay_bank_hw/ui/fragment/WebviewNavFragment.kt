package com.example.cathay_bank_hw.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.util.ExtendFunction.setActionBarTitle
import kotlinx.android.synthetic.main.fragment_webview_nav.*

class WebviewNavFragment : Fragment() {
    private val args : WebviewNavFragmentArgs by navArgs()
    private var linkUrl = ""
    private var actionBarTitle = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_webview_nav, container, false)
        return view
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val langIcon = menu.findItem(R.id.item_lang)
        langIcon?.isVisible = false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nav_webview.apply {
            // Force links and redirects to open in the WebView instead of in a browser
            webViewClient = WebViewClient()
        }
        // Enable Javascript
        nav_webview.settings.apply {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            //支持屏幕缩放
            supportZoom()
            //不显示webview缩放按钮
            builtInZoomControls = true
        }
        //get Args
        linkUrl = args.url
        actionBarTitle = args.actionBarTitle

        nav_webview.loadUrl(linkUrl)
        setActionBarTitle(actionBarTitle)
    }

}