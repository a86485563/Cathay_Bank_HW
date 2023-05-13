package com.example.cathay_bank_hw.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.cathay_bank_hw.R
import com.example.cathay_bank_hw.util.ExtendFunction.setActionBarTitle

class WebviewNavFragment : Fragment() {
    private val args : WebviewNavFragmentArgs by navArgs()
    private var linkUrl = ""
    private var actionBarTitle = ""
    private lateinit var webview : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_webview_nav, container, false)
        webview = view.findViewById<WebView?>(R.id.nav_webview).apply {
            // Force links and redirects to open in the WebView instead of in a browser
            webViewClient = WebViewClient()
        }
        // Enable Javascript
        webview.settings.apply {
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

        webview.loadUrl(linkUrl)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle(actionBarTitle)
    }
}