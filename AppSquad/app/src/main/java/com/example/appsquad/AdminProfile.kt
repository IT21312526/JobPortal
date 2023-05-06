package com.example.appsquad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import database.CompanyDatabase
import database.repositories.AdminRepository
import database.repositories.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AdminProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_profile)

        val repository = AdminRepository(CompanyDatabase.getInstance(this))

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("admin", null)

        GlobalScope.launch(Dispatchers.IO) {
            val data = cookies?.let { repository.getAdminDetail(it.toInt()) }

            val tvName = findViewById<TextView>(R.id.tvAdminEmail)
            val tvEmail = findViewById<TextView>(R.id.tvAdmnPassword)
            if (data != null) {
                tvName.text = data.email
                tvEmail.text = data.password
            }


        }

        val btnLogoutAdmin = findViewById<Button>(R.id.btnLogoutAdmin)
        btnLogoutAdmin.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("MySession", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("admin", null)
            editor.apply()

            var intent = Intent(this, LoginChoose::class.java)

            startActivity(intent)
            finish()
        }


        val btnGoback = findViewById<Button>(R.id.btnGoMainFromAdminProfile)
        btnGoback.setOnClickListener {
            var intent = Intent(this, AdminDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}