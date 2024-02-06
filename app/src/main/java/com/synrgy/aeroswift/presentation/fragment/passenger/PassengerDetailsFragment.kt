package com.synrgy.aeroswift.presentation.fragment.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.atwa.filepicker.core.FilePicker
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPassengerDetailsBinding
import com.synrgy.aeroswift.dialog.ConfirmationDialog
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.adapter.InputDocAdapter
import com.synrgy.aeroswift.presentation.viewholder.InputDocViewHolder
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
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
class PassengerDetailsFragment : Fragment() {
    companion object {
        const val KEY_PASSENGER_DETAIL_ID = "key_passenger_detail_id"
        const val KEY_IS_PASSENGER_OWNER = "key_is_passenger_owner"
    }

    private lateinit var binding: FragmentPassengerDetailsBinding

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var confirmationDialog: ConfirmationDialog

    private val authViewModel: AuthViewModel by viewModels()
    private val passengerViewModel: PassengerDetailsViewModel by viewModels()

    private lateinit var inputDocAdapter: InputDocAdapter

    private val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private var selectedDate = Calendar.getInstance()

    private var passengerId: String? = null
    private var isOwner = false
    private var timestamp = Date().time.toString()

    private var docList = mutableListOf<DocumentData>()

    private val filePicker = FilePicker.getInstance(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPassengerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passengerId = arguments?.getString(KEY_PASSENGER_DETAIL_ID)
        isOwner = arguments?.getBoolean(KEY_IS_PASSENGER_OWNER) ?: false

        loadingDialog = LoadingDialog(requireActivity())
        confirmationDialog = ConfirmationDialog(requireActivity()) { passengerViewModel.deletePassenger(passengerId!!) }
        inputDocAdapter = InputDocAdapter(::handleFilePicker)

        authViewModel.getUser()
        authViewModel.checkAuth()

        if (passengerId != null) {
            passengerViewModel.getPassenger(passengerId!!)
            passengerViewModel.getDocuments(passengerId!!)
            if (!isOwner) binding.btnDelete.visibility = View.VISIBLE
        } else {
            handleAddEmptyDoc()
        }

        observeViewModel()

        handleSetAdapter()
        handleInputChange()

        binding.btnDelete.setOnClickListener {
            confirmationDialog.show(
                heading = "Delete Confirmation",
                title = "Delete passenger data?",
                description = "When you book later, you need to manually enter the profile details"
            )
        }
        binding.btnSave.setOnClickListener { handleSavePassenger() }
        binding.toolbarPassenger.setNavigationOnClickListener { handleNavigate() }

        binding.tiBirth.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) Helper.showDatePicker(requireContext(), selectedDate, ::updateBirthInput)
        }
        binding.chipAddOtherDoc.setOnClickListener {
            val newTimestamp = Date().time.toString()
            val updatedData = DocumentData(
                id = newTimestamp,
                passengerId = passengerId ?: timestamp
            )
            val updatedList = ArrayList(this.inputDocAdapter.currentList)

            updatedList.add(updatedData)
            docList.add(updatedData)

            this.inputDocAdapter.submitList(updatedList)
            binding.rvInputDoc.scrollToPosition(0)
        }
    }

    private fun observeViewModel() {
        passengerViewModel.passenger.observe(viewLifecycleOwner, ::handleGetPassenger)
        passengerViewModel.documents.observe(viewLifecycleOwner, ::handleGetDocuments)
        passengerViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        passengerViewModel.success.observe(viewLifecycleOwner, ::handleSuccess)

        authViewModel.name.observe(viewLifecycleOwner) { binding.tiName.setText(it) }
        authViewModel.dateBirth.observe(viewLifecycleOwner) { binding.tiBirth.setText(Helper.convertTimestampToDate(it)) }
    }

    private fun handleSetAdapter() {
        binding.rvInputDoc.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvInputDoc.adapter = this.inputDocAdapter
        this.inputDocAdapter.submitList(docList)
    }

    private fun handleNavigate() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
            confirmationDialog.dismiss()
            handleNavigate()
        }
    }

    private fun updateBirthInput() {
        binding.tiBirth.setText(dateFormatter.format(selectedDate.time))
    }

    private fun handleGetPassenger(data: PassengerData) {
        binding.tiNik.setText(data.nik)
        binding.tiName.setText(data.name)
        binding.tiBirth.setText(data.dob)

        when (data.surname) {
            Constant.PassengerSurname.MR.value -> binding.rbMr.isChecked = true
            Constant.PassengerSurname.MRS.value -> binding.rbMrs.isChecked = true
            Constant.PassengerSurname.MS.value -> binding.rbMs.isChecked = true
        }
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
            id = passengerId ?: timestamp,
            nik = nik,
            name = name,
            dob = dob,
            category = Constant.PassengerType.ADULT.value,
            surname = surname
        )

        if (Helper.isValidPassenger(data) && Helper.isValidDocument(docList)) {
            passengerViewModel.addPassenger(data, docList)
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

    private fun handleGetDocuments(data: List<DocumentData>) {
        if (data.isEmpty()) handleAddEmptyDoc() else docList = data.toMutableList()
        this.inputDocAdapter.submitList(docList)
    }

    private fun handleAddEmptyDoc() {
        docList = mutableListOf(
            DocumentData(
                id = timestamp,
                passengerId = passengerId ?: timestamp
            )
        )
    }
}