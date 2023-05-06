package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class EditApplication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_application)

        val btnGoback = findViewById<Button>(R.id.btnGoBackFromEditAppl)
        btnGoback.setOnClickListener {
            var intent = Intent(this, JobApplications::class.java)
            startActivity(intent)
            finish()
        }
    }
}