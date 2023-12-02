package com.example.cloudwebservice5

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloudwebservice5.Data.CeoData
import com.example.cloudwebservice5.Data.CeoStoreData
import com.example.cloudwebservice5.Data.MessageData
import com.example.cloudwebservice5.Dialog.CeoStorePopup
import com.example.cloudwebservice5.Dialog.MessagePopup
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.adapter.CeoAdapter
import com.example.cloudwebservice5.adapter.MessageAdapter
import com.example.cloudwebservice5.databinding.FragmentCeoBinding
import com.example.cloudwebservice5.databinding.FragmentMessageBinding
import kotlinx.coroutines.launch

class MessageFragment : Fragment() {


    private lateinit var binding: FragmentMessageBinding
    private lateinit var adapter: MessageAdapter
    private var messages = listOf<MessageData>()
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
        binding = FragmentMessageBinding.inflate(inflater)

        getDataList()

        return binding.root
    }

    private fun getDataList() {

        lifecycleScope.launch{
            messages = RetrofitClient.getMessages(userId!!)
            if (messages.isNotEmpty()) {
                for (message in messages) {
                    Log.d("[MessageFragment]","name: ${message.name}, id:${message.id}, title: ${message.title}, content: ${message.content}")
                }
            } else {
                Toast.makeText(context, "받은 메시지가 없습니다.", Toast.LENGTH_SHORT).show()
                Log.e("getUser Error", "")
            }
            initRecycler()
        }
    }

    private fun initRecycler() {
        binding.apply {
            adapter = MessageAdapter(messages)
            recyclerView.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = adapter

            adapter.itemClickListener = object : MessageAdapter.OnItemClickListener {
                override fun onItemClick(data: MessageData, pos: Int) {
                    showMessagePopup(data, userId!!, data.id)
                }
            }
        }
    }

    fun showMessagePopup(data : MessageData,senderId: String, receiverId: String)
    {
        val dialogFragment = MessagePopup(data,senderId, receiverId)
        dialogFragment.show(requireActivity().supportFragmentManager, "showMessagePopup")
    }
}