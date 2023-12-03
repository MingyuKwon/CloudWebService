package com.example.cloudwebservice5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cloudwebservice5.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigationView()

        // 앱 초기 실행 시 홈화면으로 설정
        if (savedInstanceState == null) {
            binding.bottomNavigationView.selectedItemId = R.id.fragment_search
        }

    }

    private fun setBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_search -> {
                    var ceoFragment = CeoFragment()
                    var userId = intent.getStringExtra("userId")
                    var bundle = Bundle()
                    bundle.putString("userId",userId)
                    ceoFragment.arguments = bundle

                    this.supportFragmentManager!!.beginTransaction()
                        .replace(R.id.main_container, ceoFragment)
                        .commit()
                    true
                }
                R.id.fragment_message -> {
                    var messageFragment = MessageFragment()
                    var userId = intent.getStringExtra("userId")
                    var bundle = Bundle()
                    bundle.putString("userId",userId)
                    messageFragment.arguments = bundle

                    this.supportFragmentManager!!.beginTransaction()
                        .replace(R.id.main_container, messageFragment)
                        .commit()
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        try {
            var realactivityName  = "com.example.cloudwebservice5." + "LoginActivity"
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
