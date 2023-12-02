package com.example.cloudwebservice5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.common.SharedPreferencesManager
import com.example.cloudwebservice5.databinding.ActivitySignUpBinding
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    private var idFlag = false
    private var passwordFlag = false
    private var phoneNumberFlag = false
    private var nameFlag = false
    private var careerFlag = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBinding()
    }

    private fun initBinding() {
        binding.apply {

            idTextInputLayout.editText?.addTextChangedListener(idListener)
            idTextInputEditText.hint = resources.getString(R.string.id_hint)
            idTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.idTextInputEditText.hint = ""
                } else {
                    binding.idTextInputEditText.hint = resources.getString(R.string.id_hint)
                }
            }

            passwordTextInputLayout.editText?.addTextChangedListener(passwordListener)
            passwordTextInputEditText.hint = resources.getString(R.string.id_hint)
            passwordTextInputEditText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.passwordTextInputEditText.hint = ""
                } else {
                    binding.passwordTextInputEditText.hint = resources.getString(R.string.password_hint)
                }
            }

            phoneNumberTextInputLayout.editText?.addTextChangedListener(phoneNumberListener)
            nameTextviewInputLayout.editText?.addTextChangedListener(nameListener)
            careerTextInputLayout.editText?.addTextChangedListener(careerListener)

            ceoCheckBox.setOnClickListener {
                careerFlag = !careerFlag
                if(ceoCheckBox.isChecked) {
                    careerTextInputEditText.isEnabled = true
                }else {
                    careerTextInputEditText.text?.clear()
                    careerTextInputEditText.isEnabled = false
                    careerFlag = true
                }
                flagCheck()
            }

            signUpBtn.setOnClickListener {
                var userId = idTextInputEditText.text.toString()
                var password = passwordTextInputEditText.text.toString()
                var name = nameTextInputEditText.text.toString()
                var phoneNumber = phoneNumberTextInputEditText.text.toString()
                var isCeo = ceoCheckBox.isChecked

                if(isCeo) {
                    var career = careerTextInputEditText.text.toString().toInt()
                    lifecycleScope.launch{
                        val postSignUpResponse = RetrofitClient.signUpUser(userId,password,name,phoneNumber,isCeo,career)
                        if (postSignUpResponse != null) {
                            SharedPreferencesManager.setLoginInfo(this@SignUpActivity, userId)
                            Log.d("[SignUpActivity]", "message: ${postSignUpResponse.message}")
                        } else {
                            Log.e("signup Error", "")
                        }
                    }
                    moveToOtherActivity("CeoSignUpActivity")
                } else {
                    lifecycleScope.launch{
                        val postSignUpResponse = RetrofitClient.signUpUser(userId,password,name,phoneNumber,isCeo, null)
                        if (postSignUpResponse != null) {
                            SharedPreferencesManager.setLoginInfo(this@SignUpActivity, userId)
                            Log.d("[SignUpActivity]", "message: ${postSignUpResponse.message}")
                        } else {
                            Log.e("signup Error", "")
                        }
                    }
                    moveToOtherActivity("ChatActivity")
                }
            }
        }
    }

    private val idListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.idTextInputLayout.error = "아이디를 입력해주세요."
                        idFlag = false
                    }
                    !idRegex(s.toString()) -> {
                        binding.idTextInputLayout.error = "아이디 양식이 일치하지 않습니다."
                        idFlag = false
                    }
                    else -> {
                        binding.idTextInputLayout.error = null
                        idFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val passwordListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.passwordTextInputLayout.error = "비밀번호를 입력해주세요."
                        passwordFlag = false
                    }
                    !passwordRegex(s.toString()) -> {
                        binding.passwordTextInputLayout.error = "비밀번호 양식이 일치하지 않습니다."
                        passwordFlag = false
                    }
                    else -> {
                        binding.passwordTextInputLayout.error = null
                        passwordFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val nameListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.nameTextviewInputLayout.error = "이름을 입력해주세요."
                        nameFlag = false
                    }
//                    !passwordRegex(s.toString()) -> {
//                        binding.passwordTextInputLayout.error = "비밀번호 양식이 일치하지 않습니다."
//                        passwordFlag = false
//                    }
                    else -> {
                        binding.nameTextviewInputLayout.error = null
                        nameFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val phoneNumberListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.phoneNumberTextInputLayout.error = "전화번호를 입력해주세요."
                        phoneNumberFlag = false
                    }
                    !phoneNumberRegex(s.toString()) -> {
                        binding.phoneNumberTextInputLayout.error = "전화번호 양식이 일치하지 않습니다."
                        phoneNumberFlag = false
                    }
                    else -> {
                        binding.phoneNumberTextInputLayout.error = null
                        phoneNumberFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val careerListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.careerTextInputLayout.error = "경력을 입력해주세요."
                        careerFlag = false
                    }
                    else -> {
                        binding.careerTextInputLayout.error = null
                        careerFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private fun passwordRegex(password: String): Boolean {
        // 대소문자 구분 없이 영어, 숫자, 특수문자를 적어도 하나씩 포함하고, 8자리에서 15자리까지의 문자열
        val regex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?i)[a-zA-Z\\d@#$%^&+=!]{8,15}$")
        return regex.matches(password)
    }

    // 위의 두 메서드를 포함하여 종합적으로 id 정규식을 확인하는 메서드
    private fun idRegex(id: String): Boolean {
        // 대소문자 구분 없이 영어와 숫자를 적어도 하나씩은 포함한 8자리에서 12자리까지의 문자열
        val regex = Regex("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,12}$")
        return regex.matches(id)
    }

    private fun phoneNumberRegex(phoneNumber: String): Boolean{
        val regex = Regex("^010[0-9]{8}$")
        return regex.matches(phoneNumber)
    }

    fun flagCheck() {
        binding.signUpBtn.isEnabled = idFlag && passwordFlag && phoneNumberFlag && nameFlag && careerFlag
        binding.signUpBtn.isClickable = idFlag && passwordFlag && phoneNumberFlag && nameFlag && careerFlag
    }

    fun moveToOtherActivity(activityName: String) {
        try {
            var realactivityName  = "com.example.cloudwebservice5." + activityName
            val activityClass = Class.forName(realactivityName).kotlin.java
            val intent = Intent(this, activityClass)
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            // 클래스가 찾을 수 없는 경우의 처리
            e.printStackTrace()
        } catch (e: Exception) {
            // 기타 예외 처리
            e.printStackTrace()
        }
    }
}