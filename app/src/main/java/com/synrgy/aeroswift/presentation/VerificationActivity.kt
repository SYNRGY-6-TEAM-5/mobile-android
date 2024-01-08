package com.synrgy.aeroswift.presentation
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.synrgy.aeroswift.R

class VerificationActivity : AppCompatActivity() {

    private lateinit var otpEditText1: EditText
    private lateinit var otpEditText2: EditText
    private lateinit var otpEditText3: EditText
    private lateinit var otpEditText4: EditText

    private lateinit var mAuth: FirebaseAuth
    private lateinit var verificationId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_otp)

        otpEditText1 = findViewById(R.id.otpEditText1)
        otpEditText2 = findViewById(R.id.otpEditText2)
        otpEditText3 = findViewById(R.id.otpEditText3)
        otpEditText4 = findViewById(R.id.otpEditText4)

        mAuth = FirebaseAuth.getInstance()

        // Dapatkan verificationId dari intent sebelumnya atau storage yang sesuai
        verificationId = intent.getStringExtra("verificationId") ?: ""

        val confirmButton: Button = findViewById(R.id.btn_register)
        confirmButton.setOnClickListener {
            // Panggil fungsi untuk memverifikasi OTP
            verifyOTP()
        }
    }

    private fun verifyOTP() {
        val otp1 = otpEditText1.text.toString()
        val otp2 = otpEditText2.text.toString()
        val otp3 = otpEditText3.text.toString()
        val otp4 = otpEditText4.text.toString()

        val otpCode = "$otp1$otp2$otp3$otp4"

        // Buat objek PhoneAuthCredential dari verificationId dan kode OTP
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, otpCode)

        // Lakukan verifikasi dengan credential yang telah dibuat
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Jika verifikasi berhasil, navigasikan ke halaman setup profil
                    navigateToSetupProfile()
                } else {
                    // Handle kesalahan verifikasi
                    Toast.makeText(this, "Gagal verifikasi OTP", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToSetupProfile() {
        val intent = Intent(this, SetupProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}
