package com.synrgy.aeroswift.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import com.google.android.material.button.MaterialButton
import com.synrgy.aeroswift.R

class TermOfServicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_term_of_services)

        val checkBoxAgree: CheckBox = findViewById(R.id.checkBoxAgree)
        val btnAccept: MaterialButton = findViewById(R.id.btn_acc)

        checkBoxAgree.setOnCheckedChangeListener { _, isChecked ->
            btnAccept.isEnabled = isChecked
        }

    }
}