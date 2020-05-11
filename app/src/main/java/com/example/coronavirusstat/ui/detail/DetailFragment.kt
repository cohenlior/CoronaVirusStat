package com.example.coronavirusstat.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.coronavirusstat.model.Country

class DetailFragment : Fragment() {

    private var countryDetail: Country? = null

    companion object {
        private const val ARG_COUNTRY_DETAIL = "countryDetail"

        fun newInstance(country: Country) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_COUNTRY_DETAIL, country)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            countryDetail = it.getParcelable(ARG_COUNTRY_DETAIL)
        }
    }
}