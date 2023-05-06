package com.example.appsquad

import adapters.CompanyAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import database.CompanyDatabase
import database.repositories.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CompanyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_profile)

        val adapter = CompanyAdapter()
        val repository = CompanyRepository(CompanyDatabase.getInstance(this))

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("company", null)

        GlobalScope.launch(Dispatchers.IO) {
            val data = cookies?.let { repository.getCompanyDetail(it.toInt()) }

            val tvName = findViewById<TextView>(R.id.tvCompanyNameProfile)
            val tvEmail = findViewById<TextView>(R.id.tvCompanyEmailProfile)
            val tvAddress = findViewById<TextView>(R.id.tvCompanyAddressProfile)
            val tvPassword = findViewById<TextView>(R.id.tvCompanyPasswordProfile)
            val tvPhone = findViewById<TextView>(R.id.tvCompanyPhoneProfile)
            val tvReg = findViewById<TextView>(R.id.tvCompanyRegProfile)
            val tvDescription = findViewById<TextView>(R.id.tvCompanyDescriptionProfile)
            val tvApproved = findViewById<TextView>(R.id.tvCompanyApprovedProfile)
            if (data != null) {
                runOnUiThread {
                    tvName.text = data.name
                    tvEmail.text = data.email
                    tvAddress.text =data.address
                    tvPassword.text =data.password
                    tvDescription.text = data.description
                    tvReg.text = data.regNo
                    tvPhone.text = data.phone
                    tvApproved.text = "Status :" + data.approved
                }

            }


        }

        val btngoBack = findViewById<Button>(R.id.btnGoBack)

        btngoBack.setOnClickListener {
            var intent = Intent(this, CompanyDashboard::class.java)

            startActivity(intent)
            finish()
        }

        val btnLogout = findViewById<Button>(R.id.btnLogoutProfile)
        btnLogout.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("MySession", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("company", null)
            editor.apply()


            var intent = Intent(this, LoginChoose::class.java)
            startActivity(intent)
            finish()
        }


        val btnGoEditProfile = findViewById<Button>(R.id.btnGoEditProfile)

        btnGoEditProfile.setOnClickListener {
            var intent = Intent(this, EditCompanyProfile::class.java)
            startActivity(intent)
            finish()
        }
    }
}