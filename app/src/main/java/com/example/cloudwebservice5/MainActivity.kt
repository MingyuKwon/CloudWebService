package com.example.cloudwebservice5

import DownloadFileTask
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.common.SharedPreferencesManager
import com.example.cloudwebservice5.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            MapButton.setOnClickListener {
                moveToOtherActivity("MapActivity")
            }

            StaticButton.setOnClickListener {
                moveToOtherActivity("franchiseActivity")
            }

            DataRoomButton.setOnClickListener {
                moveToOtherActivity("DataRoomActivity")
            }

            mentoringButton.setOnClickListener {
                SharedPreferencesManager.clearPreferences(this@MainActivity)
                val loginInfo = SharedPreferencesManager.getLoginInfo(this@MainActivity);
                if(loginInfo["userId"].isNullOrEmpty()) {
                    moveToOtherActivity("LoginActivity")
                }
            }
        }

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

    override fun onDestroy() {
        SharedPreferencesManager.clearPreferences(this)
        super.onDestroy()
    }


}