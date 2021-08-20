package com.lordsaac.extensioncrop_ml.Interfaces

import android.content.Context
import com.lordsaac.extensioncrop_ml.ML_Helpers.BarcodeProcess
import com.lordsaac.extensioncrop_ml.Models.Response


interface ListenerBarcodes {

 fun responseListener(response: Response)

}