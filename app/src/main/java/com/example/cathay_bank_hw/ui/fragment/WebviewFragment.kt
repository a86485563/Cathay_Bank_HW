package com.example.cathay_bank_hw.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import com.example.cathay_bank_hw.R


class WebviewFragment : Fragment() {
    val URL_STRING = "URL_STRING"

    private var url = ""
    private lateinit var webview : WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_webview, container, false)
        webview = view.findViewById<WebView?>(R.id.web_view).apply {
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
        url = arguments?.getString(URL_STRING)?:""

        webview.loadUrl(url)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {

        fun newInstance(url : String): WebviewFragment {
            val fragment = WebviewFragment()
            val bundle = Bundle()
            bundle.putString(WebviewFragment().URL_STRING,url)
            fragment.arguments = bundle
            return fragment
        }
    }


}