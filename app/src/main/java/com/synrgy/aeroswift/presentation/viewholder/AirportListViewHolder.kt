package com.synrgy.aeroswift.presentation.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.synrgy.aeroswift.R
import com.synrgy.domain.AirportList

class AirportListViewHolder(
    private val itemView: View
): RecyclerView.ViewHolder(itemView) {
    private var title: MaterialTextView? = null
    private var subtitle: MaterialTextView? = null
    private var category: MaterialTextView? = null

    fun bindData(data: AirportList) {
        title = itemView.findViewById(R.id.tv1_airport_list)
        subtitle = itemView.findViewById(R.id.tv2_airport_list)
        category = itemView.findViewById(R.id.tv3_airport_list)

        title?.text = data.title
        subtitle?.text = data.subtitle
        category?.text = data.category
    }
}