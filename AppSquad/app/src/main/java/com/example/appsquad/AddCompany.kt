package com.example.appsquad

import adapters.CompanyAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import database.CompanyDatabase
import database.entities.Company
import database.repositories.CompanyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCompany : AppCompatActivity() {
    lateinit var cname: EditText
    lateinit var caddress: EditText
    lateinit var cemail: EditText
    lateinit var cpassword: EditText
    lateinit var cphone: EditText
    lateinit var cregno: EditText
    lateinit var cdescritpion: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)

        cname = findViewById(R.id.edtCompanyName)
        caddress = findViewById(R.id.edtCompanyAddress)
        cemail = findViewById(R.id.edtCompanyEmail)
        cpassword = findViewById(R.id.edtCompanyPassword)
        cphone = findViewById(R.id.edtCompanyPhoneNo)
        cregno = findViewById(R.id.edtCompanyRegNo)
        cdescritpion = findViewById(R.id.edtCompanyDescription)


        val adapter = CompanyAdapter()
        val repository = CompanyRepository(CompanyDatabase.getInstance(this))

        val btnAddCompany = findViewById<Button>(R.id.btnAddComp)
        var btnGoBack = findViewById<Button>(R.id.btnGoBackfromCreateComp)
        btnAddCompany.setOnClickListener {
            addbtnClick(repository , adapter , cname , caddress , cemail , cpassword ,cphone , cregno , cdescritpion  )
        }

        btnGoBack.setOnClickListener {
            var intent = Intent(this, LoginCompany::class.java)
//        intent.putExtra("answer" , ans)
            startActivity(intent)
            finish()
        }


    }

    fun addbtnClick(repository: CompanyRepository, adapter: CompanyAdapter , cname :EditText , caddress:EditText , cemail:EditText , cpassword :EditText , cphone:EditText , cregno : EditText , cdesc:EditText){

        CoroutineScope(Dispatchers.IO).launch {
            val cm = cname.text.toString()
            val ca = caddress.text.toString()
            val ce = cemail.text.toString()
            val cp = cpassword.text.toString()
            val ap = "pending"
            val phone = cphone.text.toString()
            val regNo = cregno.text.toString()
            val desc = cdesc.text.toString()

            repository.insert(Company(cm, cp , ca ,ce , ap , regNo , phone, desc ))
            val data = repository.getAllCompanies()
            runOnUiThread {
                adapter.setData(data, this@AddCompany)
            }
        }

        var intent = Intent(this, LoginChoose::class.java)
//        intent.putExtra("answer" , ans)
        startActivity(intent)
        finish()
    }

}