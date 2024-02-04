package com.synrgy.aeroswift.presentation.viewholder

import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemInputDocumentBinding
import com.synrgy.domain.local.DocumentData

class InputDocViewHolder(
    private val binding: ItemInputDocumentBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: DocumentData) {
        binding.tiDocNum.doOnTextChanged { text, _, _, _ -> data.docNum = text.toString() }
    }
}