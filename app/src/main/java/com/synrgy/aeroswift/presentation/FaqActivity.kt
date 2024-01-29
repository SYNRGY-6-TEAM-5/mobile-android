package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.ActivityFaqBinding
import com.synrgy.aeroswift.presentation.adapter.FaqAdapter
import com.synrgy.presentation.constant.FaqConstant

class FaqActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, FaqActivity::class.java))
        }
    }

    private lateinit var binding: ActivityFaqBinding

    private val faqAdapter = FaqAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                HomeActivity.startProfileFragment(this@FaqActivity)
                this@FaqActivity.finish()
            }
        })

        handleSetAdapter()

        binding.toolbarFaq.setNavigationOnClickListener { onBackPressed() }
    }

    private fun handleSetAdapter() {
        binding.rvFaq.layoutManager = LinearLayoutManager(this)
        binding.rvFaq.adapter = faqAdapter
        faqAdapter.submitList(FaqConstant.getData())
    }
}