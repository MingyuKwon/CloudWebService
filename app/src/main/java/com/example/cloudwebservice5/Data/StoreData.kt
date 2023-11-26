package com.example.cloudwebservice5.Data

import android.util.Log
import java.util.Date

class StoreData {
        var id : Number? = null
        var bizesNm : String? = null
        var indsLclsNm : String? = null
        var indsMclsNm : String? = null
        var indsSclsNm : String? = null
        var ctprvnNm : String? = null
        var signguNm : String? = null
        var adongNm : String? = null
        var lon : Number? = null
        var lat : Number? = null
        var date : Date? = null

        override fun toString(): String {

            var str = ""
            str += id
            str += "\n"

            str += bizesNm
            str += "\n"

            str += indsLclsNm
            str += "\n"

            str += indsMclsNm
            str += "\n"

            str += indsSclsNm
            str += "\n"

            str += ctprvnNm
            str += "\n"

            str += signguNm
            str += "\n"

            str += adongNm
            str += "\n"

            str += lon
            str += "\n"

            str += lat
            str += "\n"

            Log.e("StoreData" , str)
            return super.toString()
        }


}