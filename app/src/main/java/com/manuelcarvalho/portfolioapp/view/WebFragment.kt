package com.manuelcarvalho.portfolioapp.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.manuelcarvalho.portfolioapp.R
import kotlinx.android.synthetic.main.fragment_web.*

private const val TAG = "WebFragment"
class WebFragment : Fragment() {

    private lateinit var cart: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Web View"

        arguments?.let {
            cart = WebFragmentArgs.fromBundle(it).search
        }


//        val url = "https://www.google.com/search?q=%22Commodore%22+%22VIC-20%22"
        var url = "https://www.google.com/search?q=Commodore+VIC20+"


        var wordArray = cart.split(" ")

        for (word in wordArray) {
            url += "$word+"
        }

        Log.d(TAG, "Array $wordArray")
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

        webView.loadUrl(url)
    }

}
