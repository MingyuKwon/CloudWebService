package com.example.cloudwebservice5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.databinding.ActivityMainBinding
import com.example.cloudwebservice5.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            showGraphButton.setOnClickListener {
                RetrofitClient.getRecommendData("경남", "외식", "커피")
            }

        }
    }
}