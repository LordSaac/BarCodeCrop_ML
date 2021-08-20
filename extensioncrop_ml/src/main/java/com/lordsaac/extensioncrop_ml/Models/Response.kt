package com.lordsaac.extensioncrop_ml.Models

import java.util.*

class Response {

    var count: Int = 0
    var dateNow: Date = Date()
    var barcodes: ArrayList<Barcodes> = ArrayList<Barcodes>()
}

class Barcodes{

    var value: String = ""
    var code: String = ""

}