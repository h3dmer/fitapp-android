package com.sport.project.fitapp.utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter

class CustomArrayAdapter<String>(context: Context, layout: Int, var values: Array<String>) :
    ArrayAdapter<String>(context, layout, values) {
    private val customFilter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val results = FilterResults()
            results.values = values
            results.count = values.size
            return results
        }
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return customFilter
    }
}