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
import java.time.LocalDateTime

class ChargePopup(data : RecommendationChargeData) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_charge_popup, null)

        return AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
    }


}