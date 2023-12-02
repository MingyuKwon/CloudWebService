package com.example.cloudwebservice5.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cloudwebservice5.Data.CeoStoreData
import com.example.cloudwebservice5.Data.MessageData
import com.example.cloudwebservice5.databinding.FragmentMessageBinding
import com.example.cloudwebservice5.databinding.FragmentMessagePopupBinding
import com.example.cloudwebservice5.databinding.FragmentStoreInfoPopupBinding

class MessagePopup(var data : MessageData, var senderId: String, var receiverId: String) : DialogFragment() {

    private lateinit var binding: FragmentMessagePopupBinding

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {

        binding = FragmentMessagePopupBinding.inflate(layoutInflater)

        binding.apply {

            nameTextView.text = nameTextView.text.toString() + data.name
            contentTextView.text = data.content
            titleTextView.text = titleTextView.text.toString() + data.title

            closeBtn.setOnClickListener {
                dismiss()
            }

            chatBtn.setOnClickListener {
                moveToOtherActivity("ChatActivity")
            }
        }



        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    fun moveToOtherActivity(activityName: String) {
        try {
            var realactivityName  = "com.example.cloudwebservice5." + activityName
            val activityClass = Class.forName(realactivityName).kotlin.java
            val intent = Intent(activity, activityClass)
            intent.putExtra("senderId", senderId)
            intent.putExtra("receiverId", receiverId)
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