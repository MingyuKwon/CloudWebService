package com.example.cloudwebservice5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.common.SharedPreferencesManager
import com.example.cloudwebservice5.databinding.ActivityChatBinding
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding

    private var titleFlag = false
    private var contentFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBinding()
    }

    private fun initBinding() {
        binding.apply {
            titleTextInputLayout.editText?.addTextChangedListener(titleListener)
            contentTextInputLayout.editText?.addTextChangedListener(contentListener)

            sendMsgBtn.setOnClickListener {
                var senderId = intent.getStringExtra("senderId")
                var receiverId = intent.getStringExtra("receiverId")
                var title = titleTextInputEditText.text.toString()
                var content = contentTextInputEditText.text.toString()

                lifecycleScope.launch{
                    val response = RetrofitClient.sendMessage(title, content, senderId!!, receiverId!!)
                    if (response != null) {
                        Log.d("[ChatActivity]", "message: ${response.message}")
                        Toast.makeText(this@ChatActivity, "메시지가 전송되었습니다.", Toast.LENGTH_SHORT)
                        finish()
                    } else {
                        Log.e("Send Message Error", "")
                    }
                }
            }
        }
    }

    private val titleListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.titleTextInputLayout.error = "제목을 입력해주세요."
                        titleFlag = false
                    }
                    else -> {
                        binding.titleTextInputLayout.error = null
                        titleFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private val contentListener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                when {
                    s.isEmpty() -> {
                        binding.contentTextInputLayout.error = "문의 내용을 입력해주세요."
                        contentFlag = false
                    }
                    else -> {
                        binding.contentTextInputLayout.error = null
                        contentFlag = true
                    }
                }
                flagCheck()
            }
        }
    }

    private fun flagCheck() {
        binding.sendMsgBtn.isEnabled = titleFlag && contentFlag
        binding.sendMsgBtn.isClickable = titleFlag && contentFlag
    }
}