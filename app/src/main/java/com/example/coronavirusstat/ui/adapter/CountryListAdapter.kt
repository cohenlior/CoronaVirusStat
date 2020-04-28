package com.example.coronavirusstat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coronavirusstat.R
import com.example.coronavirusstat.model.Country
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.country_list_item.view.*

class CountryListAdapter(private val countryList: List<Country>, private val clickListener: OnClickListener) :
    RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.country_list_item,
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            clickListener.onClickItem()
        }
        holder.onBind(countryList[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(country: Country) {
            itemView.countryName.text = "${country.country}"
            itemView.cases.text = "${country.cases}"
            itemView.todayCases.text = "${country.todayCases}"
            itemView.recovered.text = "${country.recovered}"
            itemView.deaths.text = "${country.deaths}"

            country.countryInfo?.flag.let {
                Picasso.get()
                    .load(it)
                    .centerCrop()
                    .fit()
                    .into(itemView.imageCountry)
            }
        }
    }

    interface OnClickListener{
        fun onClickItem()
    }
}


