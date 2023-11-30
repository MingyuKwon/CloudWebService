package com.example.cloudwebservice5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Data.StoreData
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.databinding.ActivityMainBinding
import com.example.cloudwebservice5.databinding.ActivityMapBinding
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding

    val BigCategoryArray = listOf<String>("소매업", "숙박업", "음식점업", "예술, 스포츠 및 여가관련 서비스업", "수리 및 개인 서비스업")

    val Category1tArray = listOf<String>("자동차 부품 및 내장품 소매업", "종합 소매업", "음료 소매업", "가전제품 및 정보 통신장비 소매업")
    val Category2tArray = listOf<String>("일반 및 생활 숙박시설 운영업",)
    val Category3tArray = listOf<String>("한식 음식점업", "중식 음식점업", "일식 음식점업", "서양식 음식점업", "기타 간이 음식점업",)
    val Category4tArray = listOf<String>("스포츠 서비스업", "유원지 및 기타 오락관련 서비스업",)
    val Category5tArray = listOf<String>("이용 및 미용업",)

    val emptyArray = listOf<String>("")

    val Category1tArray1 = listOf<String>("타이어 소매업","자동차 부품 소매업")
    val Category1tArray2 = listOf<String>("편의점",)
    val Category1tArray3 = listOf<String>("얼음 소매업","주류 소매업","생수/음료 소매업", "우유 소매업")
    val Category1tArray4 = listOf<String>("컴퓨터/소프트웨어 소매업","핸드폰 소매업","가전제품 소매업")

    val Category2tArray1 = listOf<String>("호텔/리조트","여관/모텔","펜션","캠핑/글램핑",)

    val Category3tArray1 = listOf<String>("국/탕/찌개류","족발/보쌈","전/부침개","국수/칼국수","횟집")
    val Category3tArray2 = listOf<String>("중국집","마라탕/훠궈",)
    val Category3tArray3 = listOf<String>("일식 회/초밥","일식 카레/돈가스/덮밥","기타 일식 음식점",)
    val Category3tArray4 = listOf<String>("경양식","파스타/스테이크","패밀리레스토랑","기타 서양식 음식점",)
    val Category3tArray5 = listOf<String>("피자","치킨")

    val Category4tArray1 = listOf<String>("헬스장","볼링장","당구장")
    val Category4tArray2 = listOf<String>("PC방","노래방",)

    val Category5tArray1 = listOf<String>("미용실","피부 관리실","네일숍",)


    val areaMidArray = listOf<String>("강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
        "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구",
        "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구")
    val areaSmallArray = listOf<String>("")

    var context : Context = this

    var indsLclsNm : String? = "음식"
    var indsMclsNm : String? = "동남아시아"
    var indsSclsNm : String? = "베트남식 전문"

    var signguNm: String? = "광진구"
    var adongNm: String? = "자양3동"


    lateinit var areaMidAdapter: ArrayAdapter<String>
    lateinit var areaSmallAdapter: ArrayAdapter<String>

    lateinit var BigCategoryAdapter: ArrayAdapter<String>
    lateinit var MidCategoryAdapter: ArrayAdapter<String>
    lateinit var SmallCategoryAdapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        areaMidAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(areaMidArray))
        areaSmallAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(emptyArray))

        BigCategoryAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(BigCategoryArray))
        MidCategoryAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(emptyArray))
        SmallCategoryAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(emptyArray))


        binding.apply {
            applyButton.setOnClickListener {
                updateData()
            }

            areaMidAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            areaMidSpinner.setAdapter(areaMidAdapter)

            areaSmallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            areaSmallSpinner.setAdapter(areaSmallAdapter)

            BigCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categoryBigSpinner.setAdapter(BigCategoryAdapter)

            MidCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categoryMidSpinner.setAdapter(MidCategoryAdapter)

            SmallCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categorySmallSpinner.setAdapter(SmallCategoryAdapter)
        }
    }

    fun updateData()
    {
        indsLclsNm = binding.categoryBigSpinner.selectedItem.toString()
        indsMclsNm = binding.categoryMidSpinner.selectedItem.toString()
        indsSclsNm = binding.categorySmallSpinner.selectedItem.toString()

        signguNm= binding.areaMidSpinner.selectedItem.toString()
        adongNm= binding.areaSmallSpinner.selectedItem.toString()

        lifecycleScope.launch {
            binding.loadingContainer.visibility = View.VISIBLE
            val storeData = RetrofitClient.getStoreData(indsLclsNm, indsMclsNm, indsSclsNm, "서울특별시", signguNm, adongNm )
            if (storeData != null) {
                // recommendationData 처리
                updateMap(storeData)
            } else {
                Log.e("storeData Error", "")
            }
            binding.loadingContainer.visibility = View.GONE
        }

    }

    fun updateMap(data : List<StoreData?>)
    {

    }

}