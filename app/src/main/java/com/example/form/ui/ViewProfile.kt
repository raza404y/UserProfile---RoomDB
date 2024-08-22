package com.example.form.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.form.Constants
import com.example.form.R
import com.example.form.database.Form
import com.example.form.databinding.ActivityViewProfileBinding
import com.example.form.viewmodels.FormViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ViewProfile : AppCompatActivity() {

    private val binding: ActivityViewProfileBinding by lazy {
        ActivityViewProfileBinding.inflate(layoutInflater)
    }
    private var formObj: Form? = null
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
            startActivity(Intent(this@ViewProfile, Update::class.java))
        }

        binding.btnCreate.setOnClickListener {
            startActivity(Intent(this@ViewProfile, CreateProfile::class.java))
        }

        binding.btnDelete.setOnClickListener {
            formObj?.let { it1 -> deleteProfile(it1) }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun deleteProfile(formObj: Form?) {
        formObj?.let {
            MaterialAlertDialogBuilder(this@ViewProfile, R.style.RoundShapeTheme)
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete Profile?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.delete(it) {
                        Toast.makeText(this@ViewProfile, "Profile Deleted", Toast.LENGTH_SHORT).show()
                        binding.apply {
                            tvName.text = "Name:"
                            tvEmail.text = "Email:"
                            tvPhoneNumber.text = "Phone:"
                            tvGender.text = "Gender:"
                            tvInterests.text = "Interests:"
                            tvNotifications.text = "Notification:"
                        }
                    }
                }.setNegativeButton("Cancel") { dialogInterface, _ ->
                    dialogInterface.cancel()
                }
                .show()
                .setCancelable(false)
        } ?: run {
            Toast.makeText(this@ViewProfile, "Profile not loaded", Toast.LENGTH_SHORT).show()
        }
    }


    @SuppressLint("SetTextI18n")
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