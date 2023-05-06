package com.example.appsquad

import adapters.CompanyAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import database.CompanyDatabase
import database.entities.Company
import database.repositories.CompanyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.CookieStore

class EditCompanyProfile : AppCompatActivity() {
    lateinit var cname: EditText
    lateinit var caddress: EditText
    lateinit var cemail: EditText
    lateinit var cpassword: EditText
    lateinit var cphone: EditText
    lateinit var creg: EditText
    lateinit var cdescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_company_profile)



        val repository = CompanyRepository(CompanyDatabase.getInstance(this))

            cname = findViewById(R.id.edtEditCompanyName)
            caddress = findViewById(R.id.edtEditCompanyAddress)
            cemail = findViewById(R.id.edtEditCompanyEmail)
            cpassword = findViewById(R.id.edtEditCompanyPassword)
            cphone = findViewById(R.id.edtEditCompanyPhone)
            cdescription = findViewById(R.id.edtEditCompanyDescription)
            creg = findViewById(R.id.edtEditCompanyReg)
        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("company", null)

        GlobalScope.launch(Dispatchers.IO) {
            val data = cookies?.let { repository.getCompanyDetail(it.toInt()) }
            runOnUiThread {

                val tvName = findViewById<EditText>(R.id.edtEditCompanyName)
                val tvEmail = findViewById<EditText>(R.id.edtEditCompanyEmail)
                val tvAddress = findViewById<EditText>(R.id.edtEditCompanyAddress)
                val tvPassword = findViewById<EditText>(R.id.edtEditCompanyPassword)
                val tvPhone = findViewById<EditText>(R.id.edtEditCompanyPhone)
                val tvDesc = findViewById<EditText>(R.id.edtEditCompanyDescription)
                val tvReg = findViewById<EditText>(R.id.edtEditCompanyReg)
                if (data != null) {
                    tvName.setText(data.name)
                    tvEmail.setText(data.email)
                    tvAddress.setText(data.address)
                    tvPassword.setText(data.password)
                    tvReg.setText(data.regNo)
                    tvPhone.setText(data.phone)
                    tvDesc.setText(data.description)
                }
            }


        }

        val btnGoback = findViewById<Button>(R.id.btnGoBackCompanyProfile)
        btnGoback.setOnClickListener {
            var intent = Intent(this, CompanyProfile::class.java)
            startActivity(intent)
            finish()
        }

        val btnUpdate = findViewById<Button>(R.id.btnUpdateCompany)
        btnUpdate.setOnClickListener {
            if (cookies != null) {
                update(repository , cookies , cname, caddress, cemail, cpassword , cphone ,creg , cdescription)

                val btn = findViewById<Button>(R.id.btnUpdateCompany)
                btn.text = "Updated"
            }
        }
    }

    fun update(repository: CompanyRepository, cookies: String , cname :EditText, caddress:EditText, cemail:EditText, cpassword :EditText , cphone:EditText , creg: EditText , cdes:EditText) {

        CoroutineScope(Dispatchers.IO).launch {
            val data = cookies?.let { repository.getCompanyDetail(it.toInt()) }
            val id = data?.id?.toInt()
            val cm = cname.text.toString()
            val ca = caddress.text.toString()
            val ce = cemail.text.toString()
            val cp = cpassword.text.toString()
            val phn = cphone.text.toString()
            val reg = creg.text.toString()
            val des = cdes.text.toString()
            if (id != null) {
                repository.updateCompany(id,cm, ce , ca, cp , phn , reg, des)
            }

        }
//        val btn = findViewById<Button>(R.id.btnUpdateCompany)
//        btn.text = "Updated"

        var intent = Intent(this, CompanyProfile::class.java)
        intent.putExtra("updated" , "yes")
        startActivity(intent)
        finish()
    }
}