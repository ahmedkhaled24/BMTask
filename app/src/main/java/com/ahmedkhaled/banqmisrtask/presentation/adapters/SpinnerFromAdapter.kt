package com.ahmedkhaled.banqmisrtask.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems

class SpinnerFromAdapter(context: Context, data: MutableList<CurrenciesItems>)
    : ArrayAdapter<CurrenciesItems?>(context, 0, data as List<CurrenciesItems?>) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = convertView
        if (convertView == null) { convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_layout, parent, false) }
        val item: CurrenciesItems? = getItem(position)
        val spinnerTV: TextView = convertView!!.findViewById(R.id.tvSpinnerLayout)
        spinnerTV.text = item!!.name
        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = convertView
        if (convertView == null) { convertView = LayoutInflater.from(context).inflate(R.layout.custom_dropdown_layout, parent, false) }
        val item: CurrenciesItems? = getItem(position)
        val dropDownTV: TextView = convertView!!.findViewById(R.id.tvDropDownLayout)
        dropDownTV.text = item!!.name
        return convertView
    }
}



















/*
package com.ahmedkhaled.banqmisrtask.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.data.model.custom.CurrenciesItems
import com.squareup.picasso.Picasso

class SpinnerFromAdapter(context: Context, strings: MutableList<String>, val data: MutableList<CurrenciesItems>)
    : ArrayAdapter<String?>(context, 0, strings as List<String?>) {

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_layout, parent, false)
        }
        val item: String? = getItem(position)
        val spinnerTV: TextView = convertView!!.findViewById(R.id.tvSpinnerLayout)
        spinnerTV.text = data[position].name
        return convertView
    }

    @SuppressLint("SetTextI18n")
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_dropdown_layout, parent, false)
        }
//        val item: String? = getItem(position-1)
        val dropDownTV: TextView = convertView!!.findViewById(R.id.tvDropDownLayout)
        dropDownTV.text = data[position].name
        return convertView
    }
}
 */