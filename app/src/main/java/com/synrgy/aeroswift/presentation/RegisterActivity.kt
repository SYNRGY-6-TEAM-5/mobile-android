package com.synrgy.aeroswift.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.synrgy.aeroswift.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var signInText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        emailEditText = findViewById(R.id.register_ti_email)
        passwordEditText = findViewById(R.id.register_ti_password)
        mAuth = FirebaseAuth.getInstance()

        val signUpButton: Button = findViewById(R.id.btn_register)
        signUpButton.setOnClickListener {
            // Panggil fungsi untuk memulai proses pendaftaran
            registerUser()
        }

        // Temukan TextView dengan ID yang sesuai
        signInText = findViewById(R.id.text_sign_in)

        // Tambahkan onClickListener untuk menanggapi klik pada teks
        signInText.setOnClickListener {
            // Panggil fungsi untuk beralih ke halaman pendaftaran (RegisterActivity)
            navigateToLogin()
        }

    }

    private fun registerUser() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Jika pendaftaran berhasil, navigasikan ke halaman verifikasi
                    navigateToVerification()
                } else {
                    // Handle kesalahan pendaftaran
                    val exception = task.exception
                    if (exception is FirebaseAuthException) {
                        // Jika pengecualian adalah FirebaseAuthException
                        Toast.makeText(this, "Pendaftaran Gagal: ${exception.message}", Toast.LENGTH_SHORT).show()
                    } else {
                        // Jika pengecualian bukan FirebaseAuthException
                        Toast.makeText(this, "Pendaftaran Gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }


    private fun navigateToVerification() {
        val intent = Intent(this, VerificationActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
