package com.lordsaac.extensioncrop_ml.ML_Helpers

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.lordsaac.extensioncrop_ml.Interfaces.ListenerBarcodes
import com.lordsaac.extensioncrop_ml.Models.Barcodes
import com.lordsaac.extensioncrop_ml.Models.Response
import java.util.*


class BarcodeProcess {

    var context: Context

    var options: BarcodeScannerOptions

    constructor(context: Context) {

        this.options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                .build()

        this.context = context

    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun proccessImage(uri: InputImage){

        val scanner = BarcodeScanning.getClient(options)

        val result = scanner.process(uri)
                .addOnSuccessListener { barcodes ->

                    val listenerBarcodes: ListenerBarcodes = this.context as ListenerBarcodes
                    val barcodeProcess = Response()
                    val listBarcodes: ArrayList<Barcodes> = ArrayList<Barcodes>()
                    var index = 1

                    barcodes.forEach {
                        val barcodes = Barcodes()

                        barcodes.code = it.displayValue
                        barcodes.value = "value " + index
                        index++

                        listBarcodes.add(barcodes)
                    }

                    barcodeProcess.barcodes = listBarcodes
                    barcodeProcess.count = listBarcodes.size

                    listenerBarcodes.responseListener(barcodeProcess)


                }
                .addOnFailureListener {

                }
    }
}