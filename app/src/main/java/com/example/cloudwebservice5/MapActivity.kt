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

    val BigCategoryArray = listOf<String>("음식", "서비스", "도소매")
    val MidCategoryArray = listOf<String>("")
    val SmallCategoryArray = listOf<String>("")

    val areaMidArray = listOf<String>("")
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
        areaSmallAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(areaSmallArray))

        BigCategoryAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(BigCategoryArray))
        MidCategoryAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(MidCategoryArray))
        SmallCategoryAdapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ArrayList(SmallCategoryArray))


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