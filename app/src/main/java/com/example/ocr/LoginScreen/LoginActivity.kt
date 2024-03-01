package com.example.ocr.LoginScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ocr.MainActivity
import com.example.ocr.R
import com.example.ocr.SignIn.SignInActivity
import com.example.ocr.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()

        binding.SignUPbutton.setOnClickListener {

            val i= Intent(this@LoginActivity,SignInActivity::class.java)
            startActivity(i)
            finish()
        }

        binding.LoginButton.setOnClickListener {

            val email=binding.EmailInputText.text.toString()
            val pwd=binding.PasswordInputText.text.toString()

            login(email,pwd)

        }
    }

    private fun login(email: String, pwd: String) {

        auth.signInWithEmailAndPassword(email,pwd)
            .addOnCompleteListener(this) { task ->

                if(task.isSuccessful){

                    Toast.makeText(this@LoginActivity,"Created Successful", Toast.LENGTH_SHORT).show()
                    val i=Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(i)
                    finish()

                }else{

                    Toast.makeText(this@LoginActivity,"Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }
}