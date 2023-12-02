package com.example.cloudwebservice5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.common.SharedPreferencesManager
import com.example.cloudwebservice5.databinding.ActivityCeoSignUpBinding
import kotlinx.coroutines.launch

class CeoSignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCeoSignUpBinding
    private lateinit var userId: String

    private var nameFlag = false
    private var descriptionFlag = false
    private var addressFlag = false
    private var annualRevenueFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCeoSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userId = intent.getStringExtra("userId").toString()

        initBinding()
    }

    private fun initBinding() {3
        binding.apply {
            nameTextInputLayout.editText?.addTextChangedListener(nameListener)
            descriptionTextInputLayout.editText?.addTextChangedListener(descriptionListener)
            addressTextviewInputLayout.editText?.addTextChangedListener(addressListener)
            annualRevenueTextInputLayout.editText?.addTextChangedListener(annualRevenueListener)

            ceoSignUpBtn.setOnClickListener {
                var name = nameTextInputEditText.text.toString()
                var description = descriptionTextInputEditText.text.toString()
                var address = addressTextInputEditText.text.toString()
                var annualRevenue = (annualRevenueTextInputEditText.text.toString() + "000000").toInt()

                Log.d("[CeoSignUpActivity]","name: $name, description: $description, address: $address, annualRevenue: $annualRevenue, userId: $userId")

                lifecycleScope.launch{
                    val postSignUpStoreResponse = RetrofitClient.signUpStore(name,description,address,annualRevenue,userId)
                    if (postSignUpStoreResponse != null) {
                        Log.d("[SignUpActivity]", "message: ${postSignUpStoreResponse.message}")
                        moveToOtherActivity("UserActivity", userId)
                    } else {
                        Log.e("signup Error", "")
                    }
                }
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
                        binding.nameTextInputLayout.error = "가게 이름을 입력해주세요."
                        nameFlag = false
                    }
                    else -> {
                        binding.nameTextInputLayout.error = null
                        nameFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val descriptionListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.descriptionTextInputLayout.error = "가게 설명을 입력해주세요."
                        descriptionFlag = false
                    }
                    else -> {
                        binding.descriptionTextInputLayout.error = null
                        descriptionFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val addressListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.addressTextviewInputLayout.error = "주소를 입력해주세요."
                        addressFlag = false
                    }
                    else -> {
                        binding.addressTextviewInputLayout.error = null
                        addressFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val annualRevenueListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.annualRevenueTextInputLayout.error = "주소를 입력해주세요."
                        annualRevenueFlag = false
                    }
                    else -> {
                        binding.annualRevenueTextInputLayout.error = null
                        annualRevenueFlag = true
                    }
                }
                flagCheck()
            }
        }
    }
    private fun flagCheck() {
        binding.ceoSignUpBtn.isEnabled = nameFlag && descriptionFlag && addressFlag && annualRevenueFlag
        binding.ceoSignUpBtn.isClickable = nameFlag && descriptionFlag && addressFlag && annualRevenueFlag
    }

    fun moveToOtherActivity(activityName: String, userId: String) {
        try {
            var realactivityName  = "com.example.cloudwebservice5." + activityName
            val activityClass = Class.forName(realactivityName).kotlin.java
            val intent = Intent(this, activityClass)
            intent.putExtra("userId", userId)
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