package com.example.cloudwebservice5.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.cloudwebservice5.Data.RecommendationChargeData
import com.example.cloudwebservice5.R
import com.example.cloudwebservice5.databinding.ActivityMainBinding
import com.example.cloudwebservice5.databinding.FragmentChargePopupBinding
import java.time.LocalDateTime

class ChargePopup(var data : RecommendationChargeData) : DialogFragment() {
    private lateinit var binding: FragmentChargePopupBinding

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        binding = FragmentChargePopupBinding.inflate(layoutInflater)

        binding.apply {
            franchiseTitle.text = data.brandNm
            franchiseBig.text = data.indutyLclasNm
            franchiseMid.text = data.indutyMlsfcNm

            jngBzmnJngAmt.text = jngBzmnJngAmt.text.toString() + data.jngBzmnJngAmt + " (천원)"
            jngBzmnEduAmt.text = jngBzmnEduAmt.text.toString() + data.jngBzmnEduAmt+ " (천원)"
            jngBzmnAssrncAmt.text = jngBzmnAssrncAmt.text.toString() + data.jngBzmnAssrncAmt+ " (천원)"
            jngBzmnEtcAmt.text = jngBzmnEtcAmt.text.toString() + data.jngBzmnEtcAmt+ " (천원)"
            smtnAmt.text = smtnAmt.text.toString() + data.smtnAmt+ " (천원)"
        }



        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }


}