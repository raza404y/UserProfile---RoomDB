package com.example.form.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.form.Constants
import com.example.form.database.Form
import com.example.form.databinding.ActivityViewProfileBinding
import com.example.form.viewmodels.FormViewModel

class ViewProfile : AppCompatActivity() {

    private val binding: ActivityViewProfileBinding by lazy {
        ActivityViewProfileBinding.inflate(layoutInflater)
    }
    private lateinit var formObj: Form
    private lateinit var viewModel: FormViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@ViewProfile)[FormViewModel::class.java]

        viewModel.getById(Constants.ID).observe(this, Observer { form ->
            if (form != null) {
                formObj = form
                setData(form)
            }
        })

        binding.btnUpdate.setOnClickListener {
            startActivity(Intent(this@ViewProfile,Update::class.java))
        }

        binding.btnCreate.setOnClickListener {
            startActivity(Intent(this@ViewProfile,CreateProfile::class.java))
        }

        binding.btnDelete.setOnClickListener {
            deleteProfile(formObj)
        }

    }

    private fun deleteProfile(formObj: Form) {
        viewModel.delete(formObj){
            Toast.makeText(this@ViewProfile, "Profile Deleted", Toast.LENGTH_SHORT).show()
            binding.apply {
                tvName.setText("Name:")
                tvEmail.setText("Email:")
                tvPhoneNumber.setText("Phone:")
                tvGender.setText("Gender:")
                tvInterests.setText("Interests:")
                tvNotifications.setText("Notification:")
            }
        }
    }

    private fun setData(form: Form) {
        form.apply {
            binding.tvName.text = "Name: ${form.name}"
            binding.tvEmail.text = "Email: ${form.email}"
            binding.tvPhoneNumber.text = "Phone: ${form.phone}"
            binding.tvGender.text = "Gender: ${form.gender}"
            binding.tvInterests.text = "Interests: ${interests.joinToString(",")}"
            val t1 = if (form.emailNotification) "Enabled" else "Disabled"
            val t2 = if (form.smsNotification) "Enabled" else "Disabled"
            binding.tvNotifications.text = "Email Notification : -> $t1 \nSms Notification: -> $t2"
        }
    }
}