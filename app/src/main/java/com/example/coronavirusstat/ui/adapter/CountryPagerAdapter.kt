package com.example.coronavirusstat.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.coronavirusstat.R
import com.example.coronavirusstat.model.Country
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.page_item.view.*


class CountryPagerAdapter(
    private val countryList: List<Country>,
    private val clickListener: OnClickListener
) :
    RecyclerView.Adapter<CountryPagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.page_item,
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
            clickListener.onClickItem(countryList[position])
        }
        holder.onBind(countryList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(country: Country) {
            itemView.layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            itemView.countryName.text = "${country.country}"
            itemView.todayCases.text = "${country.todayCases}"
            itemView.recovered.text = "${country.recovered}"

            country.countryInfo?.flag.let {
                Picasso.get()
                    .load(it)
                    .centerCrop()
                    .fit()
                    .into(itemView.imageCountry)
            }
        }
    }

    interface OnClickListener {
        fun onClickItem(country: Country)
    }
}


