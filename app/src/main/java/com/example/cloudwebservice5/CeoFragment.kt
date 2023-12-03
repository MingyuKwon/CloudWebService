package com.example.cloudwebservice5

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloudwebservice5.Data.CeoData
import com.example.cloudwebservice5.Data.CeoStoreData
import com.example.cloudwebservice5.Dialog.CeoStorePopup
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.adapter.CeoAdapter
import com.example.cloudwebservice5.databinding.FragmentCeoBinding
import kotlinx.coroutines.launch


class CeoFragment : Fragment() {

    private lateinit var binding: FragmentCeoBinding
    private lateinit var adapter: CeoAdapter
    private var userList = listOf<CeoData>()
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString("userId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCeoBinding.inflate(inflater)

        getDataList()

        return binding.root
    }

    private fun getDataList() {

        lifecycleScope.launch{
            userList = RetrofitClient.getUsers(userId!!)
            if (userList.isNotEmpty()) {
                for (user in userList) {
                    Log.d("[CeoFragment]","userid: ${user.user_id}, name: ${user.name}, phoneNumber: ${user.phone_number}, career: ${user.career}")
                }
            } else {
                Log.e("getUser Error", "")
            }
            initRecycler()
        }
    }

    private fun initRecycler() {
        binding.apply {
            adapter = CeoAdapter(userList)
            recyclerView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter

            adapter.itemClickListener = object : CeoAdapter.OnItemClickListener {
                override fun onItemClick(data: CeoData, pos: Int) {
                    lifecycleScope.launch{
                        var ceoStoreData = RetrofitClient.getCeoStore(data.user_id)
                        if (ceoStoreData != null) {
                            Log.d("[UserActivity]","storeName: ${ceoStoreData.name}, description: ${ceoStoreData.description}, address: ${ceoStoreData.address}, annualRevenue: ${ceoStoreData.annual_revenue}")
                            showCeoStorePopup(ceoStoreData, data.user_id)
                        } else {
                            Log.e("getUser Error", "")
                        }
                        initRecycler()
                    }
                }
            }
        }
    }

    fun showCeoStorePopup(data : CeoStoreData, receiverId: String)
    {
        val dialogFragment = CeoStorePopup(data, userId!!, receiverId)
        dialogFragment.show(requireActivity().supportFragmentManager, "showCeoStorePopup")
    }
}