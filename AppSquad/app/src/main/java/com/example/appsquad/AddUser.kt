package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import database.CompanyDatabase
import database.entities.Admin
import database.entities.User
import database.repositories.AdminRepository
import database.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUser : AppCompatActivity() {
    lateinit var uemail: EditText
    lateinit var upassword: EditText
    lateinit var uname:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        uemail = findViewById(R.id.edtEmailCreateUser)
        upassword = findViewById(R.id.edtPasswordCreateUser)
        uname = findViewById(R.id.edtNameCreateUser)

        val btngoback = findViewById<Button>(R.id.btnGoUserLogin)
        btngoback.setOnClickListener{
            var intent = Intent(this, UserLogin::class.java)
            startActivity(intent)
            finish()
        }

        val repository = UserRepository(CompanyDatabase.getInstance(this))

        val btnCreateUser = findViewById<Button>(R.id.btnCreateUser)
        btnCreateUser.setOnClickListener {
            addbtnClick(repository ,uemail , upassword , uname )
        }

    }

    fun addbtnClick(repository: UserRepository, uemail: EditText, upassword : EditText , uname :EditText){

        CoroutineScope(Dispatchers.IO).launch {

            val ue = uemail.text.toString()
            val up = upassword.text.toString()
            val un = uname.text.toString()
            repository.insert(User(ue,up,un))

        }
        var intent = Intent(this, UserLogin::class.java)
//        intent.putExtra("answer" , ans)
        startActivity(intent)
        finish()
    }


}