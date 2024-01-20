package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityTermOfServicesBinding

class TermOfServicesActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, TermOfServicesActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityTermOfServicesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermOfServicesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.checkBoxAgree.setOnCheckedChangeListener { _, isChecked ->
            binding.btnAcc.isEnabled = isChecked
        }

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (binding.nestedScrollView.getChildAt(0).bottom <= binding.nestedScrollView.height + binding.nestedScrollView.scrollY) {
                binding.fabScrollDown.visibility = View.GONE
            } else {
                binding.fabScrollDown.visibility = View.VISIBLE
            }
        }

        binding.fabScrollDown.setOnClickListener {
            binding.nestedScrollView.fullScroll(View.FOCUS_DOWN)
        }

        binding.toolbarTermAndServ.setNavigationOnClickListener { handleNavigateToAuth() }

        binding.btnAcc.setOnClickListener {
            if (binding.btnAcc.isEnabled) handleNavigateToAuth()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { handleNavigateToAuth() }
        })
    }

    private fun handleNavigateToAuth() {
        val bundle = Bundle()
        bundle.putInt(AuthActivity.KEY_TAB_INDEX, intent.extras?.getInt(AuthActivity.KEY_TAB_INDEX) ?: 0)

        AuthActivity.startActivity(this@TermOfServicesActivity, bundle)
        this@TermOfServicesActivity.finish()
    }
}