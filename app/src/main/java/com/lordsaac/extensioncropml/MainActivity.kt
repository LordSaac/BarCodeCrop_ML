package com.lordsaac.extensioncropml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lordsaac.*
import com.lordsaac.extensioncrop_ml.ExtensionCropMLActivity
import com.lordsaac.extensioncrop_ml.Interfaces.ResponseExtenCropML
import com.lordsaac.extensioncrop_ml.Models.ExtensionCropML
import com.lordsaac.extensioncrop_ml.Models.OptionsML
import com.lordsaac.extensioncrop_ml.Models.Response

class MainActivity : AppCompatActivity(), ResponseExtenCropML {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ExtensionCropML.start(this,OptionsML.FORMAT_ALL_FORMATS)

    }

    override fun responseListener(response: Response?) {
        TODO("Not yet implemented")
    }

}