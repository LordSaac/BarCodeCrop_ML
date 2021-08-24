package com.lordsaac.extensioncrop_ml.Models

import java.util.*


enum class Result {
    OK,BAD_SUCCESS, TIME_OUT, NOT_PROCESS
}

class Response {

    var count: Int = 0
    var dateNow: Date = Date()
    var barcodes: ArrayList<Barcodes> = ArrayList<Barcodes>()
    var result: Result = Result.NOT_PROCESS
}


class Barcodes{

    var value: String = ""
    var code: String = ""
    var selected: Boolean = false

}