package com.example.cloudwebservice5.Data

import android.util.Log
import java.util.Date

class RecommendationGrowthData {
    var id : Number? = null
    var yr : String? = null
    var indutyLclasNm : String? = null
    var indutyMlsfcNm : String? = null
    var assetsAmt : Number? = null
    var slsAmt : Number? = null
    var bsnProfitAmt : Number? = null
    var assetsIncRt : Number? = null
    var slsIncRt : Number? = null
    var bsnProfitIncRt : Number? = null
    var date : Date? = null

    override fun toString(): String {

        var str = ""
        str += id
        str += "\n"

        str += yr
        str += "\n"

        str += indutyLclasNm
        str += "\n"

        str += indutyMlsfcNm
        str += "\n"

        str += assetsAmt
        str += "\n"

        str += slsAmt
        str += "\n"

        str += bsnProfitAmt
        str += "\n"

        str += assetsIncRt
        str += "\n"

        str += slsIncRt
        str += "\n"

        str += bsnProfitIncRt
        str += "\n"

        Log.e("RecommendationGrowthData" , str)
        return super.toString()
    }
}
