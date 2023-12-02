package com.example.cloudwebservice5.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cloudwebservice5.Data.CeoStoreData
import com.example.cloudwebservice5.databinding.FragmentStoreInfoPopupBinding

class CeoStorePopup(var data : CeoStoreData, var senderId: String, var receiverId: String) : DialogFragment() {

    private lateinit var binding: FragmentStoreInfoPopupBinding

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {

        binding = FragmentStoreInfoPopupBinding.inflate(layoutInflater)

        binding.apply {

            storeNameTextView.text = storeNameTextView.text.toString() + data.name
            descriptionTextView.text = descriptionTextView.text.toString() + data.description
            addressTextView.text = addressTextView.text.toString() + data.address
            annualRevenueTextView.text = annualRevenueTextView.text.toString() + data.annual_revenue.toString()+ " 원"

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