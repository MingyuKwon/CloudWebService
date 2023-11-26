package com.example.cloudwebservice5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.databinding.ActivityMainBinding
import com.example.cloudwebservice5.databinding.ActivityMapBinding
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            showGraphButton.setOnClickListener {
                lifecycleScope.launch {
//                    val recommendationData = RetrofitClient.getRecommendData("경남", "외식", "커피")
                    val recommendationData = RetrofitClient.getRecommendChargeData("이디야커피", "2022")
                }
            }
        }
    }
}