package com.example.weatherapics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView

class WeatherWebViewFragment : Fragment() {

    private lateinit var webView: WebView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather_web_view, container, false)
        webView = view.findViewById(R.id.webView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
69
        setupViews()
        setupListeners()
    }

    private fun setupViews(){
        val result = arguments?.let { WeatherWebViewFragmentArgs.fromBundle(it).result }
        result?.webUrl?.let { webView.loadUrl(it) }

        webView.webViewClient = WebViewManager.client
    }

    private fun setupListeners(){
        webView.settings.javaScriptEnabled= true
        WebViewManager.setOnKeyListener(webView)
    }
}