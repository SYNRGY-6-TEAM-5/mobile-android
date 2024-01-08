package com.synrgy.aeroswift.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.R

class RedirectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setupemail_succes)

        // Delay beberapa detik sebelum beralih ke halaman beranda atau halaman lainnya
        Handler(Looper.getMainLooper()).postDelayed({
            // Panggil fungsi untuk beralih ke halaman beranda atau halaman lainnya
            redirectToHome()
        }, 3000)
    }

    private fun redirectToHome() {
        // Halaman Home Activity
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish() // Menutup activity
    }
}
