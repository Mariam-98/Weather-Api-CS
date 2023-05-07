package com.example.weatherapics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapics.data.SearchResultDto
import com.example.weatherapics.model.OrderByEnum
import com.example.weatherapics.net.GuardianApiCallback
import com.example.weatherapics.net.GuardianRepositoryImpl
import com.example.weatherapics.net.RetrofitClient.retrofit

class WeatherMainPageFragment : Fragment(), GuardianApiCallback<List<SearchResultDto>> {

    private lateinit var weatherRecyclerView: RecyclerView
    private val guardianRepo: GuardianRepositoryImpl by lazy { GuardianRepositoryImpl(retrofit) }

    private val weatherAdapter = WeatherAdapter {
        navigateToDetailsFragment(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        guardianRepo.search("weather", OrderByEnum.NEWEST, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather_main_page, container, false)
        weatherRecyclerView = view.findViewById(R.id.weatherRecyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherRecyclerView.adapter = weatherAdapter
    }

    private fun navigateToDetailsFragment(result: SearchResultDto) {
        findNavController().navigate(WeatherMainPageFragmentDirections.actionToWeatherDetailsFragment(result))
    }

    override fun onSuccess(t: List<SearchResultDto>) {
        weatherAdapter.setWeatherList(t)
    }

    override fun onError(msg: String) {
        // TODO: show message dialog
    }

    override fun onFailure(e: Throwable) {

    }
}