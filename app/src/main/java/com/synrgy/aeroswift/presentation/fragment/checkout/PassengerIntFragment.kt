package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.atwa.filepicker.core.FilePicker
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPassengerIntBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.adapter.InputDocAdapter
import com.synrgy.aeroswift.presentation.viewholder.InputDocViewHolder
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.DocumentData
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class PassengerIntFragment : Fragment() {
    companion object {
        const val KEY_PASSENGER_CATEGORY = "key_passenger_category"
    }

    private lateinit var binding: FragmentPassengerIntBinding

    private lateinit var loadingDialog: LoadingDialog

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate = Calendar.getInstance()

    private val viewModel: PassengerDetailsViewModel by viewModels()

    private lateinit var inputDocAdapter: InputDocAdapter

    private var timestamp = Date().time.toString()

    private var docList = mutableListOf(
        DocumentData(
            id = timestamp,
            passengerId = timestamp
        )
    )

    private val filePicker = FilePicker.getInstance(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPassengerIntBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())
        inputDocAdapter = InputDocAdapter(::handleFilePicker)

        observeViewModel()

        handleSetAdapter()
        handleTextSpan()
        handleInputChange()

        binding.tiBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(requireContext(), selectedDate, ::updateBirthInput)
        }

        binding.btnSave.setOnClickListener { handleSavePassenger() }
        binding.toolbarPassengerInt.setNavigationOnClickListener { handleNavigate() }
        binding.chipAddOtherDoc.setOnClickListener {
            val newTimestamp = Date().time.toString()
            val updatedData = DocumentData(
                id = newTimestamp,
                passengerId = timestamp
            )
            val updatedList = ArrayList(this.inputDocAdapter.currentList)

            updatedList.add(updatedData)
            docList.add(updatedData)

            this.inputDocAdapter.submitList(updatedList)
            binding.rvInputDoc.scrollToPosition(0)
        }
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        viewModel.success.observe(viewLifecycleOwner, ::handleSuccess)
    }

    private fun handleSetAdapter() {
        binding.rvInputDoc.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvInputDoc.adapter = this.inputDocAdapter
        this.inputDocAdapter.submitList(docList)
    }

    private fun handleTextSpan() {
        val covidText: Spannable =
            SpannableString(resources.getString(R.string.covid19_details))
        val startIndex = covidText.indexOf("Covid-19 infopage")
        val endIndex = startIndex + "Covid-19 infopage".length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val action = PassengerIntFragmentDirections
                    .actionPassengerIntFragmentToCovidFragment()
                findNavController().navigate(action)
            }
        }
        covidText.setSpan(
            clickableSpan,
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        covidText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_500)),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvCovid19Details.text = covidText
        binding.tvCovid19Details.movementMethod = android.text.method.LinkMovementMethod.getInstance()
    }

    private fun updateBirthInput() {
        binding.tiBirth.setText(dateFormatter.format(selectedDate.time))
    }

    private fun handleNavigate() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) loadingDialog.startLoadingDialog() else loadingDialog.dismissDialog()
    }

    private fun handleSuccess(success: Boolean) {
        if (success) {
            loadingDialog.dismissDialog()
            Helper.showToast(requireActivity(), requireContext(), "Save data success!", true)
            handleNavigate()
        }
    }

    private fun handleSavePassenger() {
        val nik = binding.tiNik.text.toString()
        val name = binding.tiName.text.toString()
        val dob = binding.tiBirth.text.toString()
        val surname = when (binding.rgSurname.checkedRadioButtonId) {
            R.id.rb_mr -> binding.rbMr.hint.toString()
            R.id.rb_mrs -> binding.rbMrs.hint.toString()
            R.id.rb_ms -> binding.rbMs.hint.toString()
            else -> ""
        }

        val data = PassengerData(
            id = timestamp,
            nik = nik,
            name = name,
            dob = dob,
            category = Constant.PassengerType.ADULT.value,
            surname = surname
        )

        if (Helper.isValidPassenger(data) && Helper.isValidDocument(docList)) {
            viewModel.addPassenger(data, docList)
        } else {
            handleSetInputMessage(data)
            handleValidateDocumentInput()
        }
    }

    private fun handleSetInputMessage(data: PassengerData) {
        if (data.nik.isEmpty() || data.nik.isBlank()) {
            binding.tilNik.error = "NIK is required."
        }
        if (data.name.isEmpty() || data.name.isBlank()) {
            binding.tilName.error = "Full name is required."
        }
        if (data.dob.isEmpty() || data.dob.isBlank()) {
            binding.tilBirth.error = "Date of birth is required."
        }
    }

    private fun handleInputChange() {
        binding.tiNik.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) binding.tilNik.error = null
        }
        binding.tiName.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) binding.tilName.error = null
        }
        binding.tiBirth.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) binding.tilBirth.error = null
        }
    }

    private fun handleFilePicker(position: Int) {
        filePicker.pickFile {
            val name = it?.name

            if (name != null) {
                val viewHolder = binding.rvInputDoc.findViewHolderForAdapterPosition(position) as InputDocViewHolder
                val updatedList = ArrayList(this.inputDocAdapter.currentList)

                updatedList[position].file = name
                docList[position].file = name

                this.inputDocAdapter.submitList(updatedList)

                viewHolder.item.btnBrowseFile.text = name
            }
        }
    }

    private fun handleValidateDocumentInput() {
        for (i in 0 until binding.rvInputDoc.childCount) {
            val viewHolder = binding.rvInputDoc.findViewHolderForAdapterPosition(i) as InputDocViewHolder
            val docType = viewHolder.item.tilDocumentType
            val nationality = viewHolder.item.tilNationality
            val docNum = viewHolder.item.tilDocNum
            val expiry = viewHolder.item.tilExpiry

            if (docList[i].type.isEmpty() || docList[i].type.isBlank()) {
                docType.error = "Document type is required."
            }
            if (docList[i].nationality.isEmpty() || docList[i].nationality.isBlank()) {
                nationality.error = "Nationality is required."
            }
            if (docList[i].docNum.isEmpty() || docList[i].docNum.isBlank()) {
                docNum.error = "Document number is required."
            }
            if (docList[i].expiry.isEmpty() || docList[i].expiry.isBlank()) {
                expiry.error = "Expiry date is required."
            }
        }
    }
}