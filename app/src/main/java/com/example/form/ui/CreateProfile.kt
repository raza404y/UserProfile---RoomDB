package com.example.form.ui

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.form.Constants
import com.example.form.database.Form
import com.example.form.databinding.ActivityCreateProfileBinding
import com.example.form.viewmodels.FormViewModel

class CreateProfile : AppCompatActivity() {

    private val binding: ActivityCreateProfileBinding by lazy {
        ActivityCreateProfileBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: FormViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@CreateProfile)[FormViewModel::class.java]

        binding.btnSave.setOnClickListener {
            createProfile()
        }

    }

    private fun createProfile() {
        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val phone = binding.etPhoneNumber.text.toString().trim()

        val selectedGenderId = binding.rgGender.checkedRadioButtonId
        val gender = findViewById<RadioButton>(selectedGenderId).text.toString()

        val interestList = mutableListOf<String>()
        if (binding.cbGaming.isChecked) interestList.add("Gaming")
        if (binding.cbReading.isChecked) interestList.add("Reading")
        if (binding.cbTraveling.isChecked) interestList.add("Traveling")

        val emailNotification = binding.swEmailNotifications.isChecked
        val smsNotification = binding.swSMSNotifications.isChecked

        val form = Form(
            id = Constants.ID,
            name = name,
            email = email,
            phone = phone,
            gender = gender,
            interests = interestList,
            emailNotification = emailNotification,
            smsNotification = smsNotification
        )
        viewModel.insert(form){
            Toast.makeText(this, "Profile Created", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}