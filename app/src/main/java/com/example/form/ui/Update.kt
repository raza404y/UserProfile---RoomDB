package com.example.form.ui

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.form.Constants
import com.example.form.R
import com.example.form.database.Form
import com.example.form.databinding.ActivityUpdateBinding
import com.example.form.viewmodels.FormViewModel

class Update : AppCompatActivity() {
    private val binding: ActivityUpdateBinding by lazy {
        ActivityUpdateBinding.inflate(layoutInflater)
    }
    private lateinit var formObj: Form
    private lateinit var viewModel: FormViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@Update)[FormViewModel::class.java]

        viewModel.getById(Constants.ID).observe(this, Observer { form ->
            if (form != null) {
                formObj = form
                setOldData(formObj)
            }
        })

        binding.btnUpdate.setOnClickListener {
            updateData(formObj)
        }

    }

    private fun updateData(formObj: Form) {
        formObj.apply {
            name = binding.etName.text.toString().trim()
            email = binding.etEmail.text.toString().trim()
            phone = binding.etPhoneNumber.text.toString().trim()
            gender = findViewById<RadioButton>(binding.rgGender.checkedRadioButtonId)?.text.toString()
            val interestList = mutableListOf<String>()
            if (binding.cbReading.isChecked) interestList.add("Reading")
            if (binding.cbTraveling.isChecked) interestList.add("Traveling")
            if (binding.cbGaming.isChecked) interestList.add("Gaming")

            interests = interestList

            val isEmailNotificationEnabled = binding.swEmailNotifications.isChecked
            val isSmsNotificationEnabled = binding.swSMSNotifications.isChecked

            emailNotification = isEmailNotificationEnabled
            smsNotification = isSmsNotificationEnabled

            viewModel.update(formObj){
                Toast.makeText(this@Update, "Profile Updated", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }

    private fun setOldData(formObj: Form) {
        formObj.apply {
            binding.etName.setText(name)
            binding.etEmail.setText(email)
            binding.etPhoneNumber.setText(phone)

            when (gender) {
                "Male" -> binding.rgGender.check(R.id.rbMale)
                "Female" -> binding.rgGender.check(R.id.rbFemale)
                "Other" -> binding.rgGender.check(R.id.rbOther)
                else -> binding.rgGender.clearCheck()
            }
            binding.cbGaming.isChecked = interests.contains("Gaming")
            binding.cbTraveling.isChecked = interests.contains("Traveling")
            binding.cbReading.isChecked = interests.contains("Reading")

            binding.swEmailNotifications.isChecked = emailNotification
            binding.swSMSNotifications.isChecked = smsNotification

        }
    }


}