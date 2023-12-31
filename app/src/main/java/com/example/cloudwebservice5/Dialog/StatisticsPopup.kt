package com.example.cloudwebservice5.Dialog

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Data.RecommendationChargeData
import com.example.cloudwebservice5.Data.RecommendationData
import com.example.cloudwebservice5.Data.StoreData
import com.example.cloudwebservice5.R
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.databinding.FragmentChargePopupBinding
import com.example.cloudwebservice5.databinding.FragmentStatisticsPopupBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.launch

class StatisticsPopup(
    var MidArea : String?, var SmallArea : String? , var CategoryBig : String? , var CategoryMid : String? ,var CategorySmall : String? ,
    var bigData : List<StoreData>?, var midData : List<StoreData>? , var smallData : List<StoreData>?) : DialogFragment() {
    private lateinit var binding: FragmentStatisticsPopupBinding

    override fun onCreateDialog(savedInstanceState: Bundle?) : Dialog {
        binding = FragmentStatisticsPopupBinding.inflate(layoutInflater)

        binding.apply {
            categoryBig.text = CategoryBig
            categoryMid.text = CategoryMid
            categorySmall.text = CategorySmall

            areaName.text = MidArea
            areasmallName.text = SmallArea

            showGraph()
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }


    fun showGraph()    {
        binding.apply {
            val bigMap = mutableMapOf <String, Int>()
            for(data in bigData!!)
            {
                val count = bigMap.get(data.indsLclsNm)
                if(count == null)
                {
                    bigMap.put(data.indsLclsNm!!, 1)
                }else
                {
                    bigMap.set(data.indsLclsNm!!, count!! + 1)
                }
            }

            val entries = ArrayList<PieEntry>()
            for(data in bigMap)
            {
                entries.add(PieEntry(data.value.toFloat(), data.key + " " +(data.value.toFloat() / bigData!!.size * 100).toInt() + "%"))
                if(data.key == CategoryBig)
                {
                    categoryBig.text = categoryBig.text.toString() + " (" + (data.value.toFloat() / bigData!!.size * 100).toInt() + "%)"
                }
            }
            // 파이 데이터셋 생성
            val dataSet = PieDataSet(entries, "대분류")

            // 데이터셋 스타일링
            val colors = ArrayList<Int>()
            colors.add(Color.RED) // 첫 번째 항목에 적용될 색상
            colors.add(Color.BLUE)
            colors.add(Color.GREEN)
            colors.add(Color.GRAY)
            colors.add(Color.YELLOW)

            dataSet.colors = colors


            dataSet.sliceSpace = 3f // 각 조각 사이의 공간
            dataSet.valueTextColor = Color.BLACK // 값의 텍스트 색상
            dataSet.valueTextSize = 10f // 값의 텍스트 크기
            dataSet.setDrawValues(false)

            PieChart1.setDrawEntryLabels(false)

            // 파이 데이터 생성 및 설정
            // %로 표시되도록 값 포맷터 설정
            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter(PieChart1))
            data.setValueTextSize(11f)
            data.setValueTextColor(Color.WHITE)

            PieChart1.data = data
            // 차트 스타일링
            PieChart1.description.isEnabled = false
            PieChart1.isDrawHoleEnabled = true
            PieChart1.setTransparentCircleColor(Color.WHITE)
            PieChart1.setHoleColor(Color.WHITE)

            // 차트 크기 조절
            PieChart1.description.isEnabled = false
            PieChart1.isDrawHoleEnabled = true
            PieChart1.holeRadius = 70f
            PieChart1.transparentCircleRadius = 30f

            PieChart1.setExtraOffsets(50f, 0f, 0f, 0f)

            val legend = PieChart1.legend
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.setDrawInside(false)
            // 차트 갱신
            PieChart1.invalidate()
      }

        if(midData != null)
        {
            binding.apply {
                val midMap = mutableMapOf <String, Int>()
                for(data in midData!!)
                {
                    val count = midMap.get(data.indsMclsNm)
                    if(count == null)
                    {
                        midMap.put(data.indsMclsNm!!, 1)
                    }else
                    {
                        midMap.set(data.indsMclsNm!!, count!! + 1)
                    }
                }

                val entries = ArrayList<PieEntry>()
                for(data in midMap)
                {
                    entries.add(PieEntry(data.value.toFloat(), data.key + " " +(data.value.toFloat() / midData!!.size * 100).toInt() + "%"))
                    if(data.key == CategoryMid)
                    {
                        categoryMid.text = categoryMid.text.toString() + " (" + (data.value.toFloat() / midData!!.size * 100).toInt() + "%)"
                    }
                }
                // 파이 데이터셋 생성
                val dataSet = PieDataSet(entries, "중분류")

                // 데이터셋 스타일링
                val colors = ArrayList<Int>()
                colors.add(Color.RED) // 첫 번째 항목에 적용될 색상
                colors.add(Color.BLUE)
                colors.add(Color.GREEN)
                colors.add(Color.GRAY)
                colors.add(Color.YELLOW)

                dataSet.colors = colors
                dataSet.sliceSpace = 3f // 각 조각 사이의 공간
                dataSet.valueTextColor = Color.BLACK // 값의 텍스트 색상
                dataSet.valueTextSize = 10f // 값의 텍스트 크기
                dataSet.setDrawValues(false)

                PieChart2.setDrawEntryLabels(false)

                // 파이 데이터 생성 및 설정
                // %로 표시되도록 값 포맷터 설정
                val data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter(PieChart2))
                data.setValueTextSize(11f)
                data.setValueTextColor(Color.WHITE)

                PieChart2.data = data
                // 차트 스타일링
                PieChart2.description.isEnabled = false
                PieChart2.isDrawHoleEnabled = true
                PieChart2.setTransparentCircleColor(Color.WHITE)
                PieChart2.setHoleColor(Color.WHITE)

                // 차트 크기 조절
                PieChart2.description.isEnabled = false
                PieChart2.isDrawHoleEnabled = true
                PieChart2.holeRadius = 70f
                PieChart2.transparentCircleRadius = 30f

                PieChart2.setExtraOffsets(50f, 0f, 0f, 0f)


                val legend = PieChart2.legend
                legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                legend.orientation = Legend.LegendOrientation.VERTICAL
                legend.setDrawInside(false)
                // 차트 갱신
                PieChart2.invalidate()
            }

        }

        if(smallData != null)
        {
            binding.apply {
                val smallMap = mutableMapOf <String, Int>()
                for(data in smallData!!)
                {
                    val count = smallMap.get(data.indsSclsNm)
                    if(count == null)
                    {
                        smallMap.put(data.indsSclsNm!!, 1)
                    }else
                    {
                        smallMap.set(data.indsSclsNm!!, count!! + 1)
                    }
                }

                val entries = ArrayList<PieEntry>()
                for(data in smallMap)
                {
                    entries.add(PieEntry(data.value.toFloat(), data.key + " " + (data.value.toFloat() / smallData!!.size * 100).toInt() + "%"))
                    if(data.key == CategorySmall)
                    {
                        categorySmall.text = categorySmall.text.toString() + " (" + (data.value.toFloat() / smallData!!.size * 100).toInt() + "%)"
                    }
                }

                // 파이 데이터셋 생성
                val dataSet = PieDataSet(entries, "소분류")

                // 데이터셋 스타일링
                val colors = ArrayList<Int>()
                colors.add(Color.RED) // 첫 번째 항목에 적용될 색상
                colors.add(Color.BLUE)
                colors.add(Color.GREEN)
                colors.add(Color.GRAY)
                colors.add(Color.YELLOW)

                dataSet.colors = colors
                dataSet.sliceSpace = 3f // 각 조각 사이의 공간
                dataSet.valueTextColor = Color.BLACK // 값의 텍스트 색상
                dataSet.valueTextSize = 10f // 값의 텍스트 크기
                dataSet.setDrawValues(false)

                PieChart3.setDrawEntryLabels(false)

                // 파이 데이터 생성 및 설정
                // %로 표시되도록 값 포맷터 설정
                val data = PieData(dataSet)
                data.setValueFormatter(PercentFormatter(PieChart3))
                data.setValueTextSize(11f)
                data.setValueTextColor(Color.WHITE)

                PieChart3.data = data
                // 차트 스타일링
                PieChart3.description.isEnabled = false
                PieChart3.isDrawHoleEnabled = true
                PieChart3.setTransparentCircleColor(Color.WHITE)
                PieChart3.setHoleColor(Color.WHITE)

                // 차트 크기 조절
                PieChart3.description.isEnabled = false
                PieChart3.isDrawHoleEnabled = true
                PieChart3.holeRadius = 70f
                PieChart3.transparentCircleRadius = 30f

                PieChart3.setExtraOffsets(50f, 0f, 0f, 0f)


                val legend = PieChart3.legend
                legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
                legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                legend.orientation = Legend.LegendOrientation.VERTICAL
                legend.setDrawInside(false)
                // 차트 갱신
                PieChart3.invalidate()
            }

        }

    }


}