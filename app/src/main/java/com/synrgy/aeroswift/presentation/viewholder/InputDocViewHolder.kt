package com.synrgy.aeroswift.presentation.viewholder

import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemInputDocumentBinding
import com.synrgy.domain.local.DocumentData
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class InputDocViewHolder(
    private val binding: ItemInputDocumentBinding
): RecyclerView.ViewHolder(binding.root) {
    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate = Calendar.getInstance()

    val item = binding
    fun bindData(data: DocumentData) {
        setAdapter()

        binding.tiDocumentType.setText(data.type)
        binding.tiNationality.setText(data.nationality)
        binding.tiDocNum.setText(data.docNum)
        binding.tiExpiry.setText(data.expiry)

        if (data.file.isNotEmpty() && data.file.isNotBlank()) {
            binding.btnBrowseFile.text = data.file
        }

        handleInputChange(data)
    }

    fun setDocName(position: Int) {
        val context = binding.root.context
        binding.docName.text = context.getString(R.string.your_travel_documents, (position + 1).toString())
    }

    private fun setAdapter() {
        val context = binding.root.context
        val documentTypes = arrayOf(
            Constant.DocumentType.PASSPORT.value,
            Constant.DocumentType.VISA.value,
            Constant.DocumentType.RESIDENCE_PERMIT.value,
        )
        val adapter = ArrayAdapter(
            context,
            R.layout.item_dropdown_spinner,
            documentTypes
        )
        binding.tiDocumentType.setAdapter(adapter)
    }

    private fun handleInputChange(data: DocumentData) {
        val context = binding.root.context

        binding.tiExpiry.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(context, selectedDate, ::updateDateInput)
        }
        binding.tiDocumentType.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) binding.tilDocumentType.error = null
            data.type = text.toString()
        }
        binding.tiNationality.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) binding.tilNationality.error = null
            data.nationality = text.toString()
        }
        binding.tiDocNum.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) binding.tilDocNum.error = null
            data.docNum = text.toString()
        }
        binding.tiExpiry.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) binding.tilExpiry.error = null
            data.expiry = text.toString()
        }
    }

    private fun updateDateInput() {
        binding.tiExpiry.setText(dateFormatter.format(selectedDate.time))
    }
}