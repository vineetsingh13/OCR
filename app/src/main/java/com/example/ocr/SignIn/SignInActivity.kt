package com.example.ocr.SignIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.ocr.MainActivity
import com.example.ocr.R
import com.example.ocr.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class SignInActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth= FirebaseAuth.getInstance()

        binding.LoginButton.setOnClickListener {
            val email=binding.EmailInputText.text.toString()
            val new_pwd=binding.NewPasswordInputText.text.toString()
            val pwd=binding.ConfirmPasswordInputText.text.toString()

            if(pwd.length<=6){
                binding.ConfirmPasswordInputText.error="Minimum length is 7"
                binding.ConfirmPasswordInputText.requestFocus()
                return@setOnClickListener
            }else if(new_pwd.length<=6){
                binding.NewPasswordInputText.error="Minimum length is 7"
                binding.NewPasswordInputText.requestFocus()
                return@setOnClickListener
            }else if(new_pwd!=pwd){
                binding.NewPasswordInputText.error="Different password"
                binding.NewPasswordInputText.requestFocus()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {

                try {

                    val time= measureTimeMillis {
                        async {
                            signUp(email,pwd)
                        }
                    }
                }catch (e: Exception){
                    print(e)
                }
            }
        }


    }

    private fun signUp(email: String, pwd: String) {

        auth.createUserWithEmailAndPassword(email,pwd)
            .addOnCompleteListener(this) { task ->

                if(task.isSuccessful){

                    Toast.makeText(this@SignInActivity,"Created Successful",Toast.LENGTH_SHORT).show()
                    val i=Intent(this@SignInActivity,MainActivity::class.java)
                    startActivity(i)
                    finish()

                }else{

                    Toast.makeText(this@SignInActivity,"some error occurred",Toast.LENGTH_SHORT).show()
                }

            }
    }
}