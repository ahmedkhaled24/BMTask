package com.ahmedkhaled.banqmisrtask.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.presentation.ui.DataOtherCurrencies


class OtherCurrenciesAdapter : RecyclerView.Adapter<OtherCurrenciesAdapter.OtherCurrenciesViewHolder>() {


    class OtherCurrenciesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // init views
        private val date: TextView = itemView.findViewById(R.id.dateLast3Days)
        private val currency: TextView = itemView.findViewById(R.id.currencyLast3Days)

        @SuppressLint("SetTextI18n")
        fun bind(item: DataOtherCurrencies) {
            date.visibility = View.GONE
            currency.text = item.currency + " " + item.name
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<DataOtherCurrencies>() {
        override fun areItemsTheSame(oldItem: DataOtherCurrencies, newItem: DataOtherCurrencies): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: DataOtherCurrencies, newItem: DataOtherCurrencies): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherCurrenciesViewHolder {
        return OtherCurrenciesViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.last_days_item, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: OtherCurrenciesViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

}
