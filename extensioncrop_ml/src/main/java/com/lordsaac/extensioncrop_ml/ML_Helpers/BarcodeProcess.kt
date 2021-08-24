package com.lordsaac.extensioncrop_ml.ML_Helpers

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.lordsaac.extensioncrop_ml.Interfaces.ListenerBarcodes
import com.lordsaac.extensioncrop_ml.Models.Barcodes
import com.lordsaac.extensioncrop_ml.Models.Response
import com.lordsaac.extensioncrop_ml.Models.Result
import java.util.*


class BarcodeProcess(var context: Context, var optionBarcode: Int) {

    var options: BarcodeScannerOptions = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(optionBarcode)
            .build()


    @RequiresApi(Build.VERSION_CODES.N)
    fun proccessImage(uri: InputImage){

        val scanner = BarcodeScanning.getClient(options)
        var isClose = false
        val listenerBarcodes: ListenerBarcodes = this.context as ListenerBarcodes
        val listBarcodes: ArrayList<Barcodes> = ArrayList<Barcodes>()
        val barcodeProcess = Response()

        val result = scanner.process(uri)
                .addOnSuccessListener { barcodes ->

                    var index = 1

                    isClose = true

                    barcodes.forEach {
                        val barcodes = Barcodes()

                        barcodes.code = it.displayValue
                        barcodes.value = "value $index"
                        index++

                        listBarcodes.add(barcodes)
                    }

                    barcodeProcess.barcodes = listBarcodes
                    barcodeProcess.count = listBarcodes.size
                    barcodeProcess.result = Result.OK

                    listenerBarcodes.responseListener(barcodeProcess)

                    scanner.close()

                }
                .addOnFailureListener {
                    print(it.message)
                    barcodeProcess.result = Result.BAD_SUCCESS
                    listenerBarcodes.responseListener(barcodeProcess)
                }

        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timer = millisUntilFinished / 1000

                if (timer == 0L && !isClose) {
                    scanner.close()
                    barcodeProcess.result = Result.TIME_OUT
                    listenerBarcodes.responseListener(barcodeProcess)
                }
            }

            override fun onFinish() {}
        }.start()
    }
}