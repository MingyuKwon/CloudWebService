package com.example.cloudwebservice5

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.cloudwebservice5.databinding.ActivityFranchiseBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry


class franchiseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFranchiseBinding
    var context : Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_franchise)

        binding = ActivityFranchiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            val items = arrayOf("2022", "2021", "2020", "2019", "2018")

            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            yearSpinner.setAdapter(adapter)
        }

        updateGraph()


    }

    fun updateGraph()
    {
        binding.apply {
            val pieEntries = ArrayList<PieEntry>()
            pieEntries.add(PieEntry(30f, "Category 1"))
            pieEntries.add(PieEntry(20f, "Category 2"))

            val pieDataSet = PieDataSet(pieEntries, "가맹점 수")
            val colors = ArrayList<Int>()
            colors.add(Color.BLUE) // 첫 번째 항목에 적용될 색상
            colors.add(Color.RED) // 두 번째 항목에 적용될 색상
            pieDataSet.colors = colors

            val pieData = PieData(pieDataSet)
            storeCountChart.setData(pieData)
            storeCountChart.invalidate()
        }
    }

}