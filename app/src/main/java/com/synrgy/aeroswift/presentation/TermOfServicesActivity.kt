package com.synrgy.aeroswift.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.core.widget.NestedScrollView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        val nestedScrollView: NestedScrollView = findViewById(R.id.nestedScrollView)
        val fabScrollDown: FloatingActionButton = findViewById(R.id.fab_scroll_down)

        nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (nestedScrollView.getChildAt(0).bottom <= nestedScrollView.height + nestedScrollView.scrollY) {
                fabScrollDown.visibility = View.GONE
            } else {
                fabScrollDown.visibility = View.VISIBLE
            }
        }

        fabScrollDown.setOnClickListener {
            nestedScrollView.fullScroll(View.FOCUS_DOWN)
        }
    }
}