package com.example.cloudwebservice5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Data.StoreData
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.databinding.ActivityMainBinding
import com.example.cloudwebservice5.databinding.ActivityMapBinding
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding

    val BigCategoryArray = listOf<String>("소매", "숙박", "음식", "예술·스포츠", "수리·개인")

    val Category1tArray = listOf<String>("" ,"자동차 부품 소매", "종합 소매", "음료 소매", "가전·통신 소매")
    val Category2tArray = listOf<String>("" ,"일반 숙박",)
    val Category3tArray = listOf<String>("" ,"한식", "중식", "일식", "서양식", "기타 간이",)
    val Category4tArray = listOf<String>("" ,"스포츠 서비스", "유원지·오락",)
    val Category5tArray = listOf<String>("" ,"이용·미용",)

    val emptyArray = listOf<String>("")


    val Category1tArray1 = listOf<String>("" ,"타이어 소매업","자동차 부품 소매업")
    val Category1tArray2 = listOf<String>("" ,"편의점",)
    val Category1tArray3 = listOf<String>("" ,"얼음 소매업","주류 소매업","생수/음료 소매업", "우유 소매업")
    val Category1tArray4 = listOf<String>("" ,"컴퓨터/소프트웨어 소매업","핸드폰 소매업","가전제품 소매업")

    val Category2tArray1 = listOf<String>("" ,"호텔/리조트","여관/모텔","펜션","캠핑/글램핑",)

    val Category3tArray1 = listOf<String>("" ,"국/탕/찌개류","족발/보쌈","전/부침개","국수/칼국수","횟집")
    val Category3tArray2 = listOf<String>("" ,"중국집","마라탕/훠궈",)
    val Category3tArray3 = listOf<String>("" ,"일식 회/초밥","일식 카레/돈가스/덮밥","기타 일식 음식점",)
    val Category3tArray4 = listOf<String>("" ,"경양식","파스타/스테이크","패밀리레스토랑","기타 서양식 음식점",)
    val Category3tArray5 = listOf<String>("" ,"피자","치킨")

    val Category4tArray1 = listOf<String>("" ,"헬스장","볼링장","당구장")
    val Category4tArray2 = listOf<String>("" ,"PC방","노래방",)

    val Category5tArray1 = listOf<String>("" ,"미용실","피부 관리실","네일숍",)


    val areaMidArray = listOf<String>("" ,"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
        "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구",
        "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구")

    val areaSmallArray1 = listOf<String>("","도곡1동", "도곡2동", "일원본동", "수서동", "개포2동", "개포3동", "일원1동", "세곡동", "압구정동", "신사동", "논현1동" ,"논현2동" ,"대치1동" ,"대치2동" ,"대치4동" ,"삼성2동" ,"삼성1동" ,"청담동" ,"개포4동" ,"개포1동" ,"역삼1동" ,"역삼2동")
    val areaSmallArray2 = listOf<String>( "","강일동", "천호1동", "천호2동", "천호3동", "성내3동", "성내1동", "성내2동", "암사2동", "암사1동", "암사3동", "둔촌2동", "둔촌1동", "길동", "상일1동", "상일2동", "고덕2동", "고덕1동", "명일1동", "명일2동",)
    val areaSmallArray3 = listOf<String>( "", "우이동", "인수동", "수유3동", "수유1동", "수유2동", "번2동", "번1동", "번3동", "송중동", "미아동", "삼양동", "삼각산동", "송천동")
    val areaSmallArray4 = listOf<String>( "", "공항동", "방화2동", "방화1동", "방화3동", "발산1동", "우장산동", "가양1동", "가양3동", "가양2동", "화곡6동", "화곡본동", "화곡4동", "화곡1동", "화곡3동", "화곡8동", "화곡2동", "등촌1동", "등촌3동", "등촌2동", "염창동")
    val areaSmallArray5 = listOf<String>( "", "남현동", "미성동", "삼성동", "대학동", "서림동", "서원동", "난향동", "난곡동", "신림동", "신원동", "조원동", "신사동", "은천동", "성현동", "낙성대동", "청룡동", "청림동", "행운동", "인헌동", "중앙동", "보라매동")
    val areaSmallArray6 = listOf<String>( "", "군자동", "화양동", "자양4동", "자양1동", "자양3동", "자양2동", "광장동", "구의1동", "구의2동", "구의3동", "능동", "중곡4동", "중곡2동", "중곡3동", "중곡1동")
    val areaSmallArray7 = listOf<String>( "", "행정동명", "항동", "개봉1동", "구로2동", "구로3동", "개봉3동", "가리봉동", "구로5동", "오류2동", "고척2동", "고척1동", "오류1동", "구로4동", "신도림동", "개봉2동", "수궁동", "구로1동")
    val areaSmallArray8 = listOf<String>( "", "시흥5동", "독산3동", "시흥1동", "가산동", "독산2동", "독산1동", "독산4동", "시흥3동", "시흥4동", "시흥2동")
    val areaSmallArray9 = listOf<String>( "", "중계본동", "상계8동", "상계5동", "공릉1동", "공릉2동", "상계2동", "상계10동", "중계2.3동", "중계4동", "상계6.7동", "하계1동", "상계3.4동", "상계9동", "중계1동", "월계3동", "상계1동", "월계2동", "월계1동", "하계2동")
    val areaSmallArray10 = listOf<String>( "", "시흥5동", "독산3동", "시흥1동", "가산동", "독산2동", "독산1동", "독산4동", "시흥3동", "시흥4동", "시흥2동")
    val areaSmallArray11 = listOf<String>( "", "시흥5동", "독산3동", "시흥1동", "가산동", "독산2동", "독산1동", "독산4동", "시흥3동", "시흥4동", "시흥2동")






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
            areaMidSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    // 항목이 선택되었을 때의 동작
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    when(selectedItem)
                    {
                        "" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "강남구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray1) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "강동구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray2) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "강북구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray3) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "강서구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray4) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "관악구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray5) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "광진구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray6) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "구로구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray7) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "금천구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray8) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "노원구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray9) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "도봉구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "동대문구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "동작구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "마포구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "서대문구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "서초구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "성동구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "성북구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "송파구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "양천구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "영등포구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "용산구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "은평구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "종로구" -> {
                           areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                           areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                           areaSmallAdapter.notifyDataSetChanged()
                        }


                        "중구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "중랑구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(emptyArray) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

            areaSmallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            areaSmallSpinner.setAdapter(areaSmallAdapter)



            BigCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categoryBigSpinner.setAdapter(BigCategoryAdapter)
            categoryBigSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    // 항목이 선택되었을 때의 동작
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    when(selectedItem)
                    {
                        "" -> {
                            MidCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            MidCategoryAdapter.addAll(emptyArray) // 새 데이터 추가
                            MidCategoryAdapter.notifyDataSetChanged()
                        }

                        "소매" -> {
                            MidCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            MidCategoryAdapter.addAll(Category1tArray) // 새 데이터 추가
                            MidCategoryAdapter.notifyDataSetChanged()
                        }

                        "숙박" -> {
                            MidCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            MidCategoryAdapter.addAll(Category2tArray ) // 새 데이터 추가
                            MidCategoryAdapter.notifyDataSetChanged()
                        }

                        "음식" -> {
                            MidCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            MidCategoryAdapter.addAll(Category3tArray ) // 새 데이터 추가
                            MidCategoryAdapter.notifyDataSetChanged()
                        }
                        "예술·스포츠" -> {
                            MidCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            MidCategoryAdapter.addAll(Category4tArray ) // 새 데이터 추가
                            MidCategoryAdapter.notifyDataSetChanged()
                        }
                        "수리·개인" -> {
                            MidCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            MidCategoryAdapter.addAll(Category5tArray ) // 새 데이터 추가
                            MidCategoryAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }


            MidCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            categoryMidSpinner.setAdapter(MidCategoryAdapter)
            categoryMidSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    // 항목이 선택되었을 때의 동작
                    val selectedItem = parent.getItemAtPosition(position).toString()
                    when(selectedItem)
                    {

                        "" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(emptyArray) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }


                        "자동차 부품 소매" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category1tArray1) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }

                        "종합 소매" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category1tArray2 ) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }

                        "음료 소매" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category1tArray3 ) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }
                        "가전·통신 소매" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category1tArray4 ) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }


                        "일반 숙박" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category2tArray1) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }

                        "한식" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category3tArray1) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }
                        "중식" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category3tArray2) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }
                        "일식" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category3tArray3) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }
                        "서양식" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category3tArray4) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }
                        "기타 간이" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category3tArray5) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }

                        "스포츠 서비스" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category4tArray1) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }
                        "유원지·오락" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category4tArray2) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }


                        "이용·미용" -> {
                            SmallCategoryAdapter.clear() // 기존 데이터를 지웁니다
                            SmallCategoryAdapter.addAll(Category5tArray1) // 새 데이터 추가
                            SmallCategoryAdapter.notifyDataSetChanged()
                        }



                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

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