package com.synrgy.aeroswift.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.synrgy.aeroswift.R

class SetupProfileActivity : AppCompatActivity() {

    private lateinit var fullnameEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var confirmButton: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    private var profileImageUri: Uri? = null

    // Activity Result Launcher untuk mengambil gambar dari galeri
    private val chooseImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            profileImageUri = uri

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_setup)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()

        fullnameEditText = findViewById(R.id.fullname)
        dobEditText = findViewById(R.id.date_of_birth)
        phoneNumberEditText = findViewById(R.id.nomor_telp)
        confirmButton = findViewById(R.id.btn_profilesetup)

        val changeImageButton: FloatingActionButton = findViewById(R.id.floatingActionButtonProfile)
        changeImageButton.setOnClickListener {
            // Panggil fungsi untuk memilih gambar dari galeri
            chooseImage()
        }

        confirmButton.setOnClickListener {
            // Panggil fungsi untuk menyimpan detail profil pengguna
            saveUserProfile()
        }
    }

    private fun chooseImage() {
        // Gunakan Activity Result Launcher untuk membuka galeri
        chooseImageLauncher.launch("image/*")
    }

    private fun saveUserProfile() {
        val fullname = fullnameEditText.text.toString()
        val dob = dobEditText.text.toString()
        val phoneNumber = phoneNumberEditText.text.toString()

        // Buat ID pengguna sebagai nama file gambar
        val userId = auth.currentUser?.uid
        val imageFileName = "$userId.jpg"

        // Upload gambar profil ke Firebase Storage
        if (profileImageUri != null) {
            val storageRef = storage.reference.child("profile_images/$imageFileName")
            storageRef.putFile(profileImageUri!!)
                .addOnSuccessListener {
                    // Dapatkan URL gambar yang diunggah
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Simpan detail profil pengguna beserta URL gambar ke Firestore
                        saveToFirestore(fullname, dob, phoneNumber, uri.toString())
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Jika pengguna tidak mengunggah gambar, simpan detail profil tanpa gambar
            saveToFirestore(fullname, dob, phoneNumber, "")
        }
    }

    private fun saveToFirestore(fullname: String, dob: String, phoneNumber: String, imageUrl: String) {
        val userId = auth.currentUser?.uid

        // Buat objek data pengguna
        val user = hashMapOf(
            "fullname" to fullname,
            "dob" to dob,
            "phoneNumber" to phoneNumber,
            "imageUrl" to imageUrl
        )

        // Simpan data pengguna ke Firestore
        if (userId != null) {
            firestore.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener {
                    // Jika penyimpanan berhasil, navigasikan ke halaman konfirmasi
                    navigateToConfirmation()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal menyimpan detail profil", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun navigateToConfirmation() {
        val intent = Intent(this, RedirectActivity::class.java)
        startActivity(intent)
        finish()
    }
}
