package com.example.cloudwebservice5.Data

import android.util.Log
import java.util.Date

class RecommendationChargeData {
    var id : Number? = null
    var yr : String? = null
    var indutyLclasNm : String? = null
    var indutyMlsfcNm : String? = null
    var brandNm : String? = null

    var jngBzmnJngAmt : Number? = null
    var jngBzmnEduAmt : Number? = null
    var jngBzmnAssrncAmt : Number? = null
    var jngBzmnEtcAmt : Number? = null
    var smtnAmt : Number? = null
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

        str += brandNm
        str += "\n"

        str += jngBzmnJngAmt
        str += "\n"

        str += jngBzmnEduAmt
        str += "\n"

        str += jngBzmnAssrncAmt
        str += "\n"

        str += jngBzmnEtcAmt
        str += "\n"

        str += smtnAmt
        str += "\n"

        Log.e("RecommendationChargeData" , str)
        return super.toString()
    }

}
