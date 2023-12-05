package com.example.cloudwebservice5

import DownloadFileTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloudwebservice5.Data.Data
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.Tools.RetrofitClient.Companion.downloadAndSaveFile
import com.example.cloudwebservice5.adapter.DataAdapter
import com.example.cloudwebservice5.databinding.ActivityDataRoomBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDataRoomBinding
    private lateinit var adapter: DataAdapter
    private var dataList = listOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataList()


    }

    private fun getDataList() {

        lifecycleScope.launch{
            binding.loadingContainer.visibility = View.VISIBLE
            dataList = RetrofitClient.getDataList()!!
            if (dataList.isNotEmpty()) {
                for (data in dataList) {
                    Log.d("[DataRoomActivity]","name: $data")
                }
            } else {
                Toast.makeText(this@DataRoomActivity, "받은 data가 없습니다.", Toast.LENGTH_SHORT).show()
                Log.e("getDataList Error", "")
            }
            initRecycler()
            binding.loadingContainer.visibility = View.GONE

        }
    }

    private fun initRecycler() {
        binding.apply {
            adapter = DataAdapter(dataList)
            recyclerView.layoutManager =
                LinearLayoutManager(this@DataRoomActivity, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter

            adapter.itemClickListener = object : DataAdapter.OnItemClickListener {
                override fun onItemClick(data: String, pos: Int) {
                    lifecycleScope.launch {
                        val downloadFileTask = DownloadFileTask(this@DataRoomActivity, data)
                        downloadFileTask.execute()
                    }
                }
            }
        }
    }
}