package com.devwarex.news.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devwarex.news.R
import com.devwarex.news.db.Country
import com.google.android.material.card.MaterialCardView

class CountriesAdapter(
    val listener: CountryListener
    ): RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {


    private var countries: List<Country> = arrayListOf()
    private var selectedCode = ""

    inner class CountriesViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view) , View.OnClickListener{
        private val image = view.findViewById<ImageView>(R.id.country_flag_iv)
        private val title = view.findViewById<TextView>(R.id.country_name_list_item)
        private val card = view.findViewById<MaterialCardView>(R.id.country_list_item_card)
        private val glide = Glide.with(itemView.context)
        init {
            view.setOnClickListener(this)
        }

        fun onBind(country: Country){
            glide.load(country.flagUrl).into(image)
            val lang = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                itemView.resources.configuration.locales[0].language
            } else {
                itemView.resources.configuration.locale.language
            }
            if (lang == "ar"){
                title.text = country.name_ar
            }else{
                title.text = country.name_en
            }
            if (country.code == selectedCode){
                card.strokeWidth = 2
            }else{
                card.strokeWidth = 0
            }
        }

        override fun onClick(v: View?) {
            listener.onCountryClick(countries[adapterPosition].code)
        }
    }


    interface CountryListener{
        fun onCountryClick(code: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder = CountriesViewHolder(
        view = LayoutInflater.from(parent.context).inflate(R.layout.country_list_item,parent,false)
    )

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.onBind(country = countries[position])
    }

    override fun getItemCount(): Int = countries.size

    @SuppressLint("NotifyDataSetChanged")
    fun setCountries(
        countries: List<Country>,
        code: String
    ){
        this.countries = countries
        this.selectedCode = code
        notifyDataSetChanged()
    }
}