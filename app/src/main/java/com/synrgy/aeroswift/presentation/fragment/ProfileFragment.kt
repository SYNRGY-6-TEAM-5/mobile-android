package com.synrgy.aeroswift.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentProfileBinding
import com.synrgy.aeroswift.dialog.AuthDialog
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.dialog.ConfirmationDialog
import com.synrgy.aeroswift.presentation.AuthActivity
import com.synrgy.aeroswift.presentation.EditProfileActivity
import com.synrgy.aeroswift.presentation.FaqActivity
import com.synrgy.aeroswift.presentation.FlightOrderActivity
import com.synrgy.aeroswift.presentation.NotifSetActivity
import com.synrgy.aeroswift.presentation.PassengerActivity
import com.synrgy.aeroswift.presentation.adapter.ProfileMenuAdapter
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.domain.local.ProfileMenu
import com.synrgy.presentation.constant.ProfileMenuConstant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var confirmationDialog: ConfirmationDialog
    private lateinit var authDialog: AuthDialog

    private val adapter = ProfileMenuAdapter(::handleClickMenu)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authDialog = AuthDialog(requireActivity())
        loadingDialog = LoadingDialog(requireActivity())
        confirmationDialog = ConfirmationDialog(requireActivity()) { authViewModel.logout() }

        binding.rvProfileMenu.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvProfileMenu.adapter = this.adapter
        this.adapter.submitList(ProfileMenuConstant.getData())

        setupGso()

        authViewModel.getUser()
        authViewModel.checkAuth()

        observeViewModels()

        binding.ivEdit.setOnClickListener {
            EditProfileActivity.startActivity(requireActivity())
            requireActivity().finish()
        }
        binding.btnLogout.setOnClickListener {
            confirmationDialog.show(
                heading = "Log Out Confirmation",
                title = "Are you sure want to log out?",
                description = null
            )
        }
    }

    private fun setupGso() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun observeViewModels() {
        authViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        authViewModel.name.observe(viewLifecycleOwner, ::handleGetName)
        authViewModel.photo.observe(viewLifecycleOwner, ::handleLoadImage)
        authViewModel.logoutLoading.observe(viewLifecycleOwner, ::handleLogout)
        authViewModel.authentication.observe(viewLifecycleOwner, ::handleAuthAlert)
    }

    private fun handleAuthAlert(token: String) {
        if (token.isEmpty() || token.isBlank()) authDialog.show()
    }

    private fun handleLogout(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            mGoogleSignInClient.revokeAccess().addOnCompleteListener {
                loadingDialog.dismissDialog()
                confirmationDialog.dismiss()

                Helper.showToast(requireActivity(), requireActivity(), getString(R.string.message_logout), isSuccess = true)
                AuthActivity.startActivity(requireActivity())
                requireActivity().finish()
            }
        }
    }

    private fun handleClickMenu(data: ProfileMenu) {
        when (data.position) {
            1 -> {
                FlightOrderActivity.startActivity(requireActivity())
                requireActivity().finish()
            }
            2 -> {
                PassengerActivity.startActivity(requireActivity())
                requireActivity().finish()
            }
            3 -> {
                NotifSetActivity.startActivity(requireActivity())
                requireActivity().finish()
            }
            4 -> {
                FaqActivity.startActivity(requireActivity())
                requireActivity().finish()
            }
            else -> {}
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.tvName.loadSkeleton()
            binding.ivProfile.loadSkeleton()
        } else {
            binding.tvName.hideSkeleton()
            binding.ivProfile.hideSkeleton()
        }
    }

    private fun handleGetName(name: String) {
        authViewModel.setName(name)
        binding.tvName.text = name
    }

    private fun handleLoadImage(image: String) {
        if (image.isNotBlank() && image.isNotEmpty()) {
            authViewModel.setPhoto(image)
            Glide
                .with(this)
                .load(image)
                .centerCrop()
                .circleCrop()
                .into(binding.ivProfile)
        }
    }
}