package com.example.weatherapics

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.weatherapics.data.SearchResultDto
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherDetailsFragment : Fragment() {

    private lateinit var result: SearchResultDto
    private lateinit var detailsImageView: ImageView
    private lateinit var webTitleTextView: TextView
    private lateinit var detailsBodyTextView: TextView
    private lateinit var webPublicationDateTextView: TextView
    private lateinit var seeWebView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        result = arguments?.let { WeatherDetailsFragmentArgs.fromBundle(it).result }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather_details, container, false)
        detailsImageView = view.findViewById(R.id.detailsImageView)
        webTitleTextView = view.findViewById(R.id.webTitleTextView)
        detailsBodyTextView = view.findViewById(R.id.detailsBodyTextView)
        webPublicationDateTextView = view.findViewById(R.id.webPublicationDateTextView)
        seeWebView = view.findViewById(R.id.seeWebView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        Glide.with(requireContext()).load(result.fields?.thumbnail).centerCrop().into(detailsImageView)
        webTitleTextView.text = result.webTitle
        detailsBodyTextView.text = Html.fromHtml(result.fields?.body, FROM_HTML_MODE_LEGACY)
        webPublicationDateTextView.text = result.webPublicationDate?.let { SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH).parse(it)?.toString() ?: return }
    }

    private fun setupListeners() {
        seeWebView.setOnClickListener {
            findNavController().navigate(WeatherDetailsFragmentDirections.actionToWeatherWebViewFragment(result))
        }
    }

}