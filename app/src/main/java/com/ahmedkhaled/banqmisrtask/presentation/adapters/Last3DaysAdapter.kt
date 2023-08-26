package com.ahmedkhaled.banqmisrtask.presentation.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.ahmedkhaled.banqmisrtask.R
import com.ahmedkhaled.banqmisrtask.presentation.ui.DataLast3Days
import com.ahmedkhaled.banqmisrtask.utils.ConvertDateFormat


class Last3DaysAdapter : RecyclerView.Adapter<Last3DaysAdapter.Last3DaysViewHolder>() {

    private lateinit var context: Context

    class Last3DaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // init views
        private val date: TextView = itemView.findViewById(R.id.dateLast3Days)
        private val currency: TextView = itemView.findViewById(R.id.currencyLast3Days)

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(context: Context, item: DataLast3Days) {
            date.text = ConvertDateFormat.date(item.date)
            currency.text = context.getString(R.string.currencyAndName, item.currency + " " + item.name)
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<DataLast3Days>() {
        override fun areItemsTheSame(oldItem: DataLast3Days, newItem: DataLast3Days): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: DataLast3Days, newItem: DataLast3Days): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Last3DaysViewHolder {
        context = parent.context
        return Last3DaysViewHolder(LayoutInflater.from(context).inflate(R.layout.last_days_item, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Last3DaysViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(context, item)
    }

}
