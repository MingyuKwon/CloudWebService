package com.example.cloudwebservice5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.cloudwebservice5.Data.RecommendationChargeData
import com.example.cloudwebservice5.Data.StoreData
import com.example.cloudwebservice5.Dialog.ChargePopup
import com.example.cloudwebservice5.Dialog.StatisticsPopup
import com.example.cloudwebservice5.Tools.RetrofitClient
import com.example.cloudwebservice5.databinding.ActivityMainBinding
import com.example.cloudwebservice5.databinding.ActivityMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapFragment
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapBinding

    val BigCategoryArray = listOf<String>("음식","소매", "숙박",  "예술·스포츠", "수리·개인")

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


    val areaMidArray = listOf<String>("강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구",
        "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구",
        "용산구", "은평구", "종로구", "중구", "중랑구")

    var positionHashMap = mapOf<String, LatLng>(
        "강남구" to LatLng(37.517236, 127.047325 ),
        "강동구" to LatLng(37.530125, 127.123762 ),
        "강북구" to LatLng(37.639610, 127.025657 ),
        "광진구" to LatLng(37.639610, 127.025657 ),
        "강서구" to LatLng(37.550979, 126.849538 ),
        "관악구" to LatLng(37.478406, 126.951613 ),
        "노원구" to LatLng(37.538484, 127.082294 ),
        "금천구" to LatLng(37.495403, 126.887369 ),
        "구로구" to LatLng(37.456872, 126.895229 ),
        "도봉구" to LatLng(37.654192, 127.056793 ),
        "동대문구" to LatLng(37.574368, 127.040019 ),
        "서대문구" to LatLng(37.579116, 126.936778 ),
        "영등포구" to LatLng(37.526372, 126.896228 ),
        "중구" to LatLng(37.564090, 126.997940 ),

        "동작구" to LatLng(37.512402, 126.939252 ),
        "마포구" to LatLng(37.566571, 126.901532 ),
        "서초구" to LatLng(37.483712, 127.032411 ),
        "성동구" to LatLng(37.563341, 127.037102 ),
        "성북구" to LatLng(37.589116, 127.018214 ),
        "송파구" to LatLng(37.514544, 127.106597 ),
        "양천구" to LatLng(37.516872, 126.866399 ),
        "용산구" to LatLng(37.538427, 126.965444 ),
        "은평구" to LatLng(37.602696, 126.929112 ),
        "종로구" to LatLng(37.572950, 126.979358 ),
        "중랑구" to LatLng(37.606560, 127.092652 ),
    )

    val areaSmallArray1 = listOf<String>("","도곡1동", "도곡2동", "일원본동", "수서동", "개포2동", "개포3동", "일원1동", "세곡동", "압구정동", "신사동", "논현1동" ,"논현2동" ,"대치1동" ,"대치2동" ,"대치4동" ,"삼성2동" ,"삼성1동" ,"청담동" ,"개포4동" ,"개포1동" ,"역삼1동" ,"역삼2동")
    val areaSmallArray2 = listOf<String>( "","강일동", "천호1동", "천호2동", "천호3동", "성내3동", "성내1동", "성내2동", "암사2동", "암사1동", "암사3동", "둔촌2동", "둔촌1동", "길동", "상일1동", "상일2동", "고덕2동", "고덕1동", "명일1동", "명일2동",)
    val areaSmallArray3 = listOf<String>( "", "우이동", "인수동", "수유3동", "수유1동", "수유2동", "번2동", "번1동", "번3동", "송중동", "미아동", "삼양동", "삼각산동", "송천동")
    val areaSmallArray4 = listOf<String>( "", "공항동", "방화2동", "방화1동", "방화3동", "발산1동", "우장산동", "가양1동", "가양3동", "가양2동", "화곡6동", "화곡본동", "화곡4동", "화곡1동", "화곡3동", "화곡8동", "화곡2동", "등촌1동", "등촌3동", "등촌2동", "염창동")
    val areaSmallArray5 = listOf<String>( "", "남현동", "미성동", "삼성동", "대학동", "서림동", "서원동", "난향동", "난곡동", "신림동", "신원동", "조원동", "신사동", "은천동", "성현동", "낙성대동", "청룡동", "청림동", "행운동", "인헌동", "중앙동", "보라매동")
    val areaSmallArray6 = listOf<String>( "", "군자동", "화양동", "자양4동", "자양1동", "자양3동", "자양2동", "광장동", "구의1동", "구의2동", "구의3동", "능동", "중곡4동", "중곡2동", "중곡3동", "중곡1동")
    val areaSmallArray7 = listOf<String>( "", "행정동명", "항동", "개봉1동", "구로2동", "구로3동", "개봉3동", "가리봉동", "구로5동", "오류2동", "고척2동", "고척1동", "오류1동", "구로4동", "신도림동", "개봉2동", "수궁동", "구로1동")
    val areaSmallArray8 = listOf<String>( "", "시흥5동", "독산3동", "시흥1동", "가산동", "독산2동", "독산1동", "독산4동", "시흥3동", "시흥4동", "시흥2동")
    val areaSmallArray9 = listOf<String>( "", "중계본동", "상계8동", "상계5동", "공릉1동", "공릉2동", "상계2동", "상계10동", "중계2.3동", "중계4동", "상계6.7동", "하계1동", "상계3.4동", "상계9동", "중계1동", "월계3동", "상계1동", "월계2동", "월계1동", "하계2동")
    val areaSmallArray10 = listOf<String>( "", "창2동", "창1동", "도봉1동", "도봉2동", "방학2동", "창5동", "방학1동", "창3동", "쌍문2동", "창4동", "쌍문3동", "쌍문1동", "방학3동", "쌍문4동")
    val areaSmallArray11 = listOf<String>( "", "제기동", "장안1동", "전농1동", "회기동", "이문1동", "용신동", "이문2동", "답십리2동", "장안2동", "청량리동", "답십리1동", "휘경1동", "휘경2동", "전농2동")
    val areaSmallArray12 = listOf<String>( "", "신대방1동", "사당3동", "흑석동", "노량진1동", "대방동", "노량진2동", "사당1동", "상도1동", "상도2동", "상도3동", "상도4동", "사당4동", "신대방2동", "사당5동", "사당2동")
    val areaSmallArray13 = listOf<String>( "", "성산1동", "망원1동", "서교동", "서강동", "대흥동", "도화동", "연남동", "상암동", "아현동", "망원2동", "성산2동", "용강동", "신수동", "합정동", "공덕동", "염리동")
    val areaSmallArray14 = listOf<String>( "", "홍제1동", "남가좌1동", "홍제3동", "홍제2동", "북가좌1동", "충현동", "신촌동", "천연동", "북가좌2동", "연희동", "홍은2동", "남가좌2동", "홍은1동", "북아현동")
    val areaSmallArray15 = listOf<String>( "", "서초3동", "양재2동", "반포4동", "양재1동", "서초4동", "서초2동", "서초1동", "내곡동", "반포1동", "방배4동", "반포3동", "잠원동", "방배2동", "방배1동", "방배본동", "방배3동", "반포본동", "반포2동")
    val areaSmallArray16 = listOf<String>( "", "용답동", "성수2가3동", "성수1가2동", "성수1가1동", "왕십리도선동", "응봉동", "금호4가동", "성수2가1동", "행당2동", "옥수동", "사근동", "금호1가동", "행당1동", "마장동", "왕십리2동", "금호2.3가동", "송정동")
    val areaSmallArray17 = listOf<String>( "", "석관동", "동선동", "성북동", "장위2동", "안암동", "삼선동", "보문동", "장위3동", "돈암2동", "길음1동", "정릉3동", "길음2동", "종암동", "월곡2동", "장위1동", "정릉2동", "월곡1동", "정릉1동", "돈암1동", "정릉4동")
    val areaSmallArray18 = listOf<String>( "", "송파1동", "문정2동", "위례동", "거여2동", "석촌동", "풍납2동", "가락본동", "잠실2동", "방이1동", "방이2동", "오금동", "마천1동", "삼전동", "잠실본동", "문정1동", "잠실6동", "풍납1동", "거여1동", "장지동", "잠실7동", "가락2동", "가락1동", "마천2동", "잠실3동", "송파2동", "오륜동", "잠실4동")
    val areaSmallArray19 = listOf<String>( "", "석관동", "동선동", "성북동", "장위2동", "안암동", "삼선동", "보문동", "장위3동", "돈암2동", "길음1동", "정릉3동", "길음2동", "종암동", "월곡2동", "장위1동", "정릉2동", "월곡1동", "정릉1동", "돈암1동", "정릉4동")
    val areaSmallArray20 = listOf<String>( "", "신월5동", "신월7동", "신정2동", "신정6동", "신정3동", "목2동", "신정4동", "목1동", "목5동", "신월1동", "신정1동", "목3동", "신월6동", "목4동", "신정7동", "신월4동", "신월3동", "신월2동")
    val areaSmallArray21 = listOf<String>( "", "신길1동", "신길3동", "신길7동", "당산1동", "여의동", "당산2동", "영등포동", "문래동", "대림3동", "양평1동", "양평2동", "신길5동", "영등포본동", "신길4동", "신길6동", "대림1동", "도림동", "대림2동")
    val areaSmallArray22 = listOf<String>( "", "원효로2동", "남영동", "한남동", "보광동", "한강로동", "청파동", "이촌1동", "이태원1동", "용산2가동", "이태원2동", "원효로1동", "용문동", "후암동", "효창동", "이촌2동", "서빙고동")
    val areaSmallArray23 = listOf<String>( "", "응암3동", "갈현1동", "진관동", "신사2동", "응암1동", "녹번동", "증산동", "갈현2동", "신사1동", "대조동", "역촌동", "불광2동", "수색동", "불광1동", "구산동", "응암2동")
    val areaSmallArray24 = listOf<String>( "", "광희동", "중림동", "신당동", "소공동", "약수동", "필동", "명동", "황학동", "회현동", "다산동", "을지로동", "신당5동", "동화동", "청구동", "장충동")
    val areaSmallArray25 = listOf<String>( "", "면목본동", "상봉2동", "면목4동", "망우본동", "중화2동", "면목7동", "신내1동", "면목2동", "면목3.8동", "신내2동", "면목5동", "중화1동", "묵1동", "망우3동", "상봉1동", "묵2동")



    var context : Context = this

    var indsLclsNm : String? = ""
    var indsMclsNm : String? = ""
    var indsSclsNm : String? = ""

    var signguNm: String? = ""
    var adongNm: String? = ""

    private val markers = mutableListOf<Marker>()

    fun ClearMarker()
    {
        for (marker in markers) {
            marker.map = null // 마커 제거
        }
        markers.clear()
    }


    lateinit var areaMidAdapter: ArrayAdapter<String>
    lateinit var areaSmallAdapter: ArrayAdapter<String>

    lateinit var BigCategoryAdapter: ArrayAdapter<String>
    lateinit var MidCategoryAdapter: ArrayAdapter<String>
    lateinit var SmallCategoryAdapter: ArrayAdapter<String>

    fun CreateStoreMarker(data: StoreData): Marker {
        val marker = Marker()
        marker.position = LatLng(data.lat!!.toDouble(), data.lon!!.toDouble())
        marker.icon = OverlayImage.fromResource(R.drawable.baseline_flag_circle_24)

        marker.setOnClickListener {
            if(marker.tag != null)
            {
                marker.icon = OverlayImage.fromResource(R.drawable.baseline_flag_circle_24)
                marker.tag = null
            }else
            {
                val inflater = LayoutInflater.from(context)
                val markerView = inflater.inflate(R.layout.custom_marker, null)
                val textView = markerView.findViewById<TextView>(R.id.storeName)
                val imageView = markerView.findViewById<ImageView>(R.id.imageView)
                textView.text = data.bizesNm
                imageView.setImageResource(R.drawable.bigflag)
                marker.icon = OverlayImage.fromView(markerView)
                marker.tag = 1
            }
            true
        }
        return marker
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
        val mapFragment = supportFragmentManager.findFragmentById(binding.map.id) as MapFragment?
        mapFragment?.getMapAsync { mapView ->

            mapView.cameraPosition = CameraPosition(positionHashMap.get(signguNm)!!, 12.0)
            ClearMarker()

            for(store in data)
            {
                val marker = CreateStoreMarker(store!!)
                marker.map = mapView
                markers.add(marker)
            }

            Log.e("count" , markers.size.toString())
        }
    }

    fun showStatistic()
    {
        indsLclsNm = binding.categoryBigSpinner.selectedItem.toString()
        indsMclsNm = binding.categoryMidSpinner.selectedItem.toString()
        indsSclsNm = binding.categorySmallSpinner.selectedItem.toString()

        signguNm= binding.areaMidSpinner.selectedItem.toString()
        adongNm= binding.areaSmallSpinner.selectedItem.toString()
        
        lifecycleScope.launch{
            binding.loadingContainer.visibility = View.VISIBLE
            val big = ArrayList<StoreData>()
            val mid = ArrayList<StoreData>()
            val small = ArrayList<StoreData>()

            var storeData1 : List<StoreData>? = null
            var storeData2 : List<StoreData>? = null
            var storeData3 : List<StoreData>? = null

            storeData1 = RetrofitClient.getStoreData(indsLclsNm, "", "", "서울특별시", signguNm, adongNm )
            if(indsMclsNm != "") storeData2 = RetrofitClient.getStoreData(indsLclsNm, indsMclsNm, "", "서울특별시", signguNm, adongNm )
            if(indsSclsNm != "") storeData3 = RetrofitClient.getStoreData(indsLclsNm, indsMclsNm, indsSclsNm, "서울특별시", signguNm, adongNm )

            val dialogFragment = StatisticsPopup(signguNm, adongNm, indsLclsNm, indsMclsNm, indsSclsNm, storeData1, storeData2, storeData3)
            dialogFragment.show(supportFragmentManager, "ShowStaPopUp")

            binding.loadingContainer.visibility = View.GONE
        }
    }


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

            showStatisButton.setOnClickListener {
                showStatistic()
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
                            areaSmallAdapter.addAll(areaSmallArray10) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "동대문구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray11) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "동작구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray12) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "마포구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray13) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "서대문구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray14) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "서초구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray15) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "성동구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray16) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "성북구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray17) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "송파구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray18) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }

                        "양천구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray19) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "영등포구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray20) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "용산구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray21) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "은평구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray22) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "종로구" -> {
                           areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                           areaSmallAdapter.addAll(areaSmallArray23) // 새 데이터 추가
                           areaSmallAdapter.notifyDataSetChanged()
                        }


                        "중구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray24) // 새 데이터 추가
                            areaSmallAdapter.notifyDataSetChanged()
                        }


                        "중랑구" -> {
                            areaSmallAdapter.clear() // 기존 데이터를 지웁니다
                            areaSmallAdapter.addAll(areaSmallArray25) // 새 데이터 추가
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

}