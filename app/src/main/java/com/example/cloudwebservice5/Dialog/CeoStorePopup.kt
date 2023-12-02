package com.example.cloudwebservice5.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.cloudwebservice5.Data.CeoStoreData
import com.example.cloudwebservice5.databinding.FragmentStoreInfoPopupBinding

class CeoStorePopup(var data : CeoStoreData) : DialogFragment() {
    private lateinit var binding: FragmentStoreInfoPopupBinding

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        binding = FragmentStoreInfoPopupBinding.inflate(layoutInflater)

        binding.apply {

            storeNameTextView.text = storeNameTextView.text.toString() + data.name
            descriptionTextView.text = descriptionTextView.text.toString() + data.description
            addressTextView.text = addressTextView.text.toString() + data.address
            annualRevenueTextView.text = annualRevenueTextView.text.toString() + data.annual_revenue.toString()+ " 원"
        }



        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }


}