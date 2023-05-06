package com.example.appsquad

import adapters.CompanyAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import database.AppSquadDatabase
import database.CompanyDatabase
import database.entities.Admin
import database.entities.Company
import database.repositories.AdminRepository
import database.repositories.CompanyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddAdmin : AppCompatActivity() {
    lateinit var aemail: EditText
    lateinit var apassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_admin)

        aemail = findViewById(R.id.edtAdminEmailCreate)
        apassword = findViewById(R.id.edtAdminPasswordCreate)

        val repository = AdminRepository(CompanyDatabase.getInstance(this))

        val btnCreateAdmin = findViewById<Button>(R.id.btnCreateAdmin)
        btnCreateAdmin.setOnClickListener {
            addbtnClick(repository ,aemail , apassword )
        }

        val btnGoAdminLogin = findViewById<Button>(R.id.btnGoAdminLogin)
        btnGoAdminLogin.setOnClickListener {
            var intent = Intent(this,LoginAdmin::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun addbtnClick(repository: AdminRepository, aemail: EditText, apassword : EditText){

        CoroutineScope(Dispatchers.IO).launch {

            val ae = aemail.text.toString()
            val ap = apassword.text.toString()
            repository.insert(Admin(ae,ap))
//            val data = repository.getAllAdmin()
//            runOnUiThread {
//                adapter.setData(data, this@AddCompany)
//            }
        }

        var intent = Intent(this, LoginAdmin::class.java)
//        intent.putExtra("answer" , ans)
        startActivity(intent)
        finish()
    }
}