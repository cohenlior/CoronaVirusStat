package com.example.coronavirusstat.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.coronavirusstat.model.Country
import com.example.coronavirusstat.ui.main.MainFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class CountryPagerAdapter(activity: AppCompatActivity, private val countryList: List<Country>) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun createFragment(position: Int): Fragment {
        return MainFragment.newInstance(countryList[position])
    }
}