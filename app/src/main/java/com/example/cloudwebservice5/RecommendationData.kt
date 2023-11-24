package com.example.cloudwebservice5

import android.util.Log
import com.kakao.sdk.common.KakaoSdk.type
import java.util.Date


class RecommendationData {
    var id : Number? = null
    var brandNm : String? = null
    var indutyLclasNm : String? = null
    var indutyMlsfcNm : String? = null
    var yr : String? = null
    var areaNm : String? = null
    var frcsCnt : Number? = null
    var avrgSlsAmt : Number? = null
    var arUnitAvrgSlsAmt : Number? = null
    var date : Date? = null

    override fun toString(): String {

        var str = ""
        str += id
        str += "\n"

        str += brandNm
        str += "\n"

        str += indutyLclasNm
        str += "\n"

        str += indutyMlsfcNm
        str += "\n"

        str += yr
        str += "\n"

        str += areaNm
        str += "\n"

        str += frcsCnt
        str += "\n"

        str += avrgSlsAmt
        str += "\n"

        str += arUnitAvrgSlsAmt
        str += "\n"

        str += date
        str += "\n"

        Log.e("RecommendationData" , str)
        return super.toString()
    }

}
