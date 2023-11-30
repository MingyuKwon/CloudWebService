package com.example.cloudwebservice5

import android.app.ProgressDialog.show
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Data.RecommendationChargeData
import com.example.cloudwebservice5.Data.RecommendationData
import com.example.cloudwebservice5.Dialog.ChargePopup
import com.example.cloudwebservice5.Tools.RetrofitClient.Companion.getRecommendChargeData
import com.example.cloudwebservice5.Tools.RetrofitClient.Companion.getRecommendData
import com.example.cloudwebservice5.databinding.ActivityFranchiseBinding
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
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.coroutines.launch


class franchiseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFranchiseBinding
    var context : Context = this

    var region: String? = "경남"
    var largeBusiness: String? = "외식"
    var mdBusiness: String? = "커피"
    var year: String? = "2022"

    val BigCategoryArray = listOf<String>("외식", "서비스", "도소매")

    val MidCategoryOutEatArray = listOf<String>("커피", "분식", "서양식", "일식", "주점", "중식", "한식", "피자", "치킨")
    val MidCategoryServiceArray = listOf<String>("오락", "기타 서비스", "숙박", "스포츠 관련", "안경", "이미용", "자동차 관련")
    val MidCategorySaleArray = listOf<String>("(건강)식품", "기타도소매", "농수산물", "의류 / 패션", "종합소매점", "편의점", "화장품")

    val AreaArray = listOf<String>("강원", "경기", "경남", "경북", "광주", "대구", "대전", "부산", "서울", "울산", "인천", "전남", "전북", "충남", "충북" ,"제주","전체", "세종")
    val yearitems = listOf<String>("2022", "2021", "2020", "2019", "2018")
    val emptyArray = listOf<String>("")


    lateinit var yearAdapter: ArrayAdapter<String>
    lateinit var areaAdapter: ArrayAdapter<String>
    lateinit var BigCategoryAdapter: ArrayAdapter<String>
    lateinit var MidCategoryAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_franchise)

        binding = ActivityFranchiseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        yearAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(yearitems))
        areaAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(AreaArray))
        BigCategoryAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(BigCategoryArray))
        MidCategoryAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(emptyArray))

        binding.apply {

            yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            yearSpinner.setAdapter(yearAdapter)

            areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            areaSpinner.setAdapter(areaAdapter)

            BigCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            BigSpinner.setAdapter(BigCategoryAdapter)
            BigSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    // 항목이 선택되었을 때의 동작
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    when(selectedItem)
                    {
                        "외식" -> {
                            MidCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            MidCategoryAdapter.addAll(MidCategoryOutEatArray) // 새 데이터 추가
                            MidCategoryAdapter.notifyDataSetChanged()
                        }

                        "서비스" -> {
                            MidCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            MidCategoryAdapter.addAll(MidCategoryServiceArray ) // 새 데이터 추가
                            MidCategoryAdapter.notifyDataSetChanged()
                        }

                        "도소매" -> {
                            MidCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            MidCategoryAdapter.addAll(MidCategorySaleArray ) // 새 데이터 추가
                            MidCategoryAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

            MidCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            MidSpinner.setAdapter(MidCategoryAdapter)

            applyButton.setOnClickListener {
                updateData()
            }

        }


    }

    fun updateData()
    {
        region = binding.areaSpinner.selectedItem.toString()
        largeBusiness = binding.BigSpinner.selectedItem.toString()
        mdBusiness = binding.MidSpinner.selectedItem.toString()
        year = binding.yearSpinner.selectedItem.toString()

        lifecycleScope.launch {
            binding.loadingContainer.visibility = View.VISIBLE
            val recommendationData = getRecommendData(region, largeBusiness, mdBusiness, year)
            if (recommendationData != null) {
                // recommendationData 처리
                updateGraph(recommendationData)
            } else {
                Log.e("recommendationData Error", "")
            }
            binding.loadingContainer.visibility = View.GONE
        }

    }

    fun removeEnglishPart(input: String): String {
        val regex = "\\s*\\(.*?\\)".toRegex() // 괄호와 괄호 안의 내용에 대한 정규 표현식
        return input.replace(regex, "") // 정규 표현식에 해당하는 부분을 빈 문자열로 대체
    }

    fun ShowBrandPopUp(data : RecommendationChargeData)
    {
        val dialogFragment = ChargePopup(data)
        dialogFragment.show(supportFragmentManager, "ShowBrandPopUp")
    }


    fun updateGraph(data : RecommendationData)    {
        binding.apply {
            val barEntries = ArrayList<BarEntry>()
            for ((index, count) in data.CountData!!.withIndex()) {
                // X축 위치를 인덱스로, Y축 값을 count로 설정
                barEntries.add(BarEntry(index.toFloat(), count.count!!.toFloat(), count.brandNm))
                if(index > 3) break
            }

            val xAxisLabels = data.CountData!!.map { it.brandNm!! }.take(5).map { removeEnglishPart(it) }
            storeCountChart.xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(xAxisLabels)
                granularity = 1f
                position = XAxis.XAxisPosition.BOTTOM
            }

            val barDataSet = BarDataSet(barEntries, "")
            val colors = ArrayList<Int>()
            colors.add(Color.RED) // 첫 번째 항목에 적용될 색상
            colors.add(Color.BLUE)
            colors.add(Color.GREEN)
            colors.add(Color.GRAY)
            colors.add(Color.YELLOW)

            barDataSet.colors = colors
            barDataSet.valueTextColor = Color.WHITE // 텍스트 색상 설정
            barDataSet.valueTextSize = 10f // 텍스트 크기 설정

            storeCountChart.axisRight.isEnabled = false // 오른쪽 Y축 비활성화

            storeCountChart.description.isEnabled = false

            val barData = BarData(barDataSet)
            storeCountChart.setData(barData)
            storeCountChart.invalidate()


            storeCountChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    // 여기에서 팝업 로직을 구현합니다
                    e?.let {
                        val name = it.data as? String // 데이터 객체를 String으로 변환
                        lifecycleScope.launch {
                            val chargeData = getRecommendChargeData(name, year)
                            if (chargeData != null) {
                                // recommendationData 처리
                                ShowBrandPopUp(chargeData)
                            } else {
                                Log.e("chargeData Error", "")
                            }
                        }
                    }
                }

                override fun onNothingSelected() {
                    // 아무것도 선택되지 않았을 때의 로직 (필요한 경우)
                }
            })
        }

        binding.apply {
            val barEntries = ArrayList<BarEntry>()
            for ((index, sale) in data.SaleData!!.withIndex()) {
                // X축 위치를 인덱스로, Y축 값을 count로 설정
                barEntries.add(BarEntry(index.toFloat(), sale.arUnitAvrgSlsAmt!!.toFloat(), sale.brandNm))
                if(index > 3) break
            }

            val xAxisLabels = data.SaleData!!.map { it.brandNm!! }.take(5).map { removeEnglishPart(it) }

            storeSaleChart.xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(xAxisLabels)
                granularity = 1f
                position = XAxis.XAxisPosition.BOTTOM
            }

            storeSaleChart.axisLeft.apply {
                granularity = 3000f // Y축의 최소 간격 설정
                labelCount = 5 // Y축에 표시될 레이블의 수
                axisMinimum = 0f // Y축의 최소값
                // 필요에 따라 axisMaximum 설정도 가능
            }

            val barDataSet = BarDataSet(barEntries, "")
            val colors = ArrayList<Int>()
            colors.add(Color.RED) // 첫 번째 항목에 적용될 색상
            colors.add(Color.BLUE)
            colors.add(Color.GREEN)
            colors.add(Color.GRAY)
            colors.add(Color.YELLOW)
            barDataSet.colors = colors

            storeSaleChart.description.isEnabled = false

            storeSaleChart.axisRight.isEnabled = false // 오른쪽 Y축 비활성화

            val barData = BarData(barDataSet)
            storeSaleChart.setData(barData)
            storeSaleChart.invalidate()

            storeSaleChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    // 여기에서 팝업 로직을 구현합니다
                    e?.let {
                        val name = it.data as? String // 데이터 객체를 String으로 변환
                        lifecycleScope.launch {
                            val chargeData = getRecommendChargeData(name, year)
                            if (chargeData != null) {
                                // recommendationData 처리
                                ShowBrandPopUp(chargeData)
                            } else {
                                Log.e("chargeData Error", "")
                            }
                        }
                    }
                }

                override fun onNothingSelected() {
                    // 아무것도 선택되지 않았을 때의 로직 (필요한 경우)
                }
            })
        }

        binding.apply {
            val barEntries = ArrayList<BarEntry>()
            barEntries.add(BarEntry(0f, data.GrowthData!!.get(0).assetsIncRt!!.toFloat()))
            barEntries.add(BarEntry(1f, data.GrowthData!!.get(0).slsIncRt!!.toFloat()))
            barEntries.add(BarEntry(2f, data.GrowthData!!.get(0).bsnProfitIncRt!!.toFloat()))

            val xAxisLabels = arrayOf("자산증가율", "판매증가율", "영업이익증가율")
            storeGrowthChart.xAxis.apply {
                valueFormatter = IndexAxisValueFormatter(xAxisLabels)
                granularity = 1f
                position = XAxis.XAxisPosition.BOTTOM
            }

            val barDataSet = BarDataSet(barEntries, "")
            val colors = ArrayList<Int>()
            colors.add(Color.RED) // 첫 번째 항목에 적용될 색상
            colors.add(Color.BLUE)
            colors.add(Color.GREEN)
            barDataSet.colors = colors

            storeGrowthChart.description.isEnabled = false

            storeGrowthChart.axisRight.isEnabled = false // 오른쪽 Y축 비활성화


            val barData = BarData(barDataSet)
            storeGrowthChart.setData(barData)
            storeGrowthChart.invalidate()
        }
    }

}