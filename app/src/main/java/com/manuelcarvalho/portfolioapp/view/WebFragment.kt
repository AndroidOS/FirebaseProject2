package com.manuelcarvalho.portfolioapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.manuelcarvalho.portfolioapp.R
import kotlinx.android.synthetic.main.fragment_web.*

/**
 * A simple [Fragment] subclass.
 */
class WebFragment : Fragment() {

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

        webView.loadUrl("https://www.lemon64.com")
    }

}
