package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemPassengerRequirementBinding
import com.synrgy.domain.local.PassengerReq
import com.synrgy.aeroswift.presentation.diffutil.PassengerReqDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.PassengerReqViewHolder

class PassengerReqAdapter: ListAdapter<PassengerReq, PassengerReqViewHolder>(
    PassengerReqDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerReqViewHolder {
        return PassengerReqViewHolder(
            ItemPassengerRequirementBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PassengerReqViewHolder, position: Int) {
        if (position == itemCount - 1) {
            holder.item.separator.visibility = View.GONE
        }

        holder.bindData(getItem(position))
    }
}