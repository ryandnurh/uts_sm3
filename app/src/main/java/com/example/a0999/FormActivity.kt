package com.example.a0999

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FormActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.form_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        findViewById<ImageView>(R.id.back_button).setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
        sharedPreferences = getSharedPreferences("User  Data", MODE_PRIVATE)

        val nameInput = findViewById<EditText>(R.id.form_name_input)
        val ageInput = findViewById<EditText>(R.id.form_age_input)
        val genderInput = findViewById<RadioGroup>(R.id.form_gender_input)

        val submit = findViewById<Button>(R.id.submit)


        // Memuat data yang disimpan
        nameInput.setText(sharedPreferences.getString("name", ""))
        ageInput.setText(sharedPreferences.getString("age", ""))

        // Mengatur listener untuk tombol kirim
        submit.setOnClickListener {
            val name = nameInput.text.toString()
            val age = ageInput.text.toString()
            val selectedGenderId = genderInput.checkedRadioButtonId

            // Validasi input
            if (name.isEmpty() || age.isEmpty() || selectedGenderId == -1) {
                Toast.makeText(this, "Semua bidang harus diisi.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi usia
            val ageInt = age.toIntOrNull()
            if (ageInt == null || ageInt < 1 || ageInt > 100) {
                Toast.makeText(this, "Usia harus berupa bilangan bulat antara 1 dan 100.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Mendapatkan jenis kelamin
            val gender = findViewById<RadioButton>(selectedGenderId).text.toString()

            // Menyimpan data ke SharedPreferences
            with(sharedPreferences.edit()) {
                putString("name", name)
                putString("age", age)
                putString("gender", gender)
                apply()
            }

            // Navigasi ke Activity untuk menampilkan informasi
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("name", name)
                putExtra("age", age)
                putExtra("gender", gender)
            }
            startActivity(intent)
        }
    }
}