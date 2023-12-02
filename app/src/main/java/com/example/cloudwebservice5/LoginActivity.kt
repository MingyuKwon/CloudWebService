package com.example.cloudwebservice5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.common.SharedPreferencesManager
import com.example.cloudwebservice5.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBinding()

    }

    private fun initBinding() {
        binding.apply {
            loginBtn.setOnClickListener {
                var userId = idEditText.text.toString()
                var password = passwordEditText.text.toString()

                if(!validateId(userId) || !validatePassword(password)) return@setOnClickListener

                lifecycleScope.launch{
                    val postLoginResponse = RetrofitClient.loginUser(userId, password)
                    if (postLoginResponse != null) {
                        SharedPreferencesManager.setLoginInfo(this@LoginActivity, userId)
                        Log.d("[LoginActivity]", "message: ${postLoginResponse.message}")
                    } else {
                        Log.e("login Error", "")
                    }
                }
            }

            signUpBtn.setOnClickListener {
                try {
                    val activityClass = Class.forName("com.example.cloudwebservice5.SignUpActivity").kotlin.java
                    val intent = Intent(this@LoginActivity, activityClass)
                    startActivity(intent)
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            passwordEditText.addTextChangedListener {
                if (passwordEditText.text.length < 8) {
                    loginBtn.isClickable = false
                    loginBtn.isEnabled = false
                } else if(idEditText.text.isNotEmpty()) {
                    loginBtn.isClickable = true
                    loginBtn.isEnabled = true
                }
            }
        }

    }

    private fun validatePassword(password: String): Boolean {
        // 대소문자 구분 없이 영어, 숫자, 특수문자를 적어도 하나씩 포함하고, 8자리에서 15자리까지의 문자열
        val regex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?i)[a-zA-Z\\d@#$%^&+=!]{8,15}$")
        if(regex.matches(password)) return true
        return false
    }

    private fun validateId(userId: String): Boolean {
        // 대소문자 구분 없이 영어와 숫자를 적어도 하나씩은 포함한 8자리에서 12자리까지의 문자열
        val regex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,12}$")
        if(regex.matches(userId)) return true
        return false
    }
}