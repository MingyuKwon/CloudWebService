package com.example.cloudwebservice5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloudwebservice5.Data.CeoData
import com.example.cloudwebservice5.Data.CeoStoreData
import com.example.cloudwebservice5.Data.RecommendationChargeData
import com.example.cloudwebservice5.Dialog.CeoStorePopup
import com.example.cloudwebservice5.Dialog.ChargePopup
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.adapter.ChatAdapter
import com.example.cloudwebservice5.common.SharedPreferencesManager
import com.example.cloudwebservice5.databinding.ActivityChatBinding
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter: ChatAdapter
    private var userList = listOf<CeoData>()
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataList()
    }

    private fun getDataList() {

        userId = intent.getStringExtra("userId").toString()

        lifecycleScope.launch{
            userList = RetrofitClient.getUsers(userId)
            if (userList.isNotEmpty()) {
                for (user in userList) {
                    Log.d("[ChatActivity]","userid: ${user.user_id}, name: ${user.name}, phoneNumber: ${user.phone_number}, career: ${user.career}")
                }
            } else {
                Log.e("getUser Error", "")
            }
            initRecycler()
        }
    }

    private fun initRecycler() {
        binding.apply {
            adapter = ChatAdapter(userList)
            recyclerView.layoutManager =
                LinearLayoutManager(this@ChatActivity, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter

            adapter.itemClickListener = object : ChatAdapter.OnItemClickListener {
                override fun onItemClick(data: CeoData, pos: Int) {
                    lifecycleScope.launch{
                        var ceoStoreData = RetrofitClient.getCeoStore(data.user_id)
                        if (ceoStoreData != null) {
                            Log.d("[ChatActivity]","storeName: ${ceoStoreData.name}, description: ${ceoStoreData.description}, address: ${ceoStoreData.address}, annualRevenue: ${ceoStoreData.annual_revenue}")
                            showCeoStorePopup(ceoStoreData)
                        } else {
                            Log.e("getUser Error", "")
                        }
                        initRecycler()
                    }
                }
            }
        }
    }

    fun showCeoStorePopup(data : CeoStoreData)
    {
        val dialogFragment = CeoStorePopup(data)
        dialogFragment.show(supportFragmentManager, "showCeoStorePopup")
    }
}
